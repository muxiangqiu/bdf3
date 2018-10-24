package com.bstek.bdf3.notice.ui.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.notice.domain.Group;
import com.bstek.bdf3.notice.domain.GroupMember;
import com.bstek.bdf3.notice.domain.MemberNotice;
import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.ui.Constants;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.view.socket.Message;

import net.sf.cglib.beans.BeanMap;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
@Service("ui.noticeService")
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private com.bstek.bdf3.notice.service.NoticeService noticeService;

	@Override
	public void getNotices(Page<Notice> page, String memberId) {
		JpaUtil.linq(Notice.class)
			.notEqual("sender", memberId)
			.collect("memberId", GroupMember.class, "sender")
			.or()
				.exists(Group.class)
					.equalProperty("id", "groupId")
					.isTrue("all")
				.end()
				.exists(GroupMember.class)
					.equalProperty("groupId", "groupId")
					.equal("memberId", memberId)
				.end()
			.end()
			.notExists(MemberNotice.class)
				.equalProperty("groupId", "groupId")
				.equalProperty("noticeId", "id")
				.equal("memberId", memberId)
			.end()
			.paging(page);
	}
	
	@Override
	public List<Notice> getNotices(String memberId) {
		return JpaUtil.linq(Notice.class)
				.collect("memberId", GroupMember.class, "sender")
				.notEqual("sender", memberId)
				.or()
					.exists(Group.class)
						.equalProperty("id", "groupId")
						.isTrue("all")
					.end()
					.exists(GroupMember.class)
						.equalProperty("groupId", "groupId")
						.equal("memberId", memberId)
					.end()
				.end()
				.notExists(MemberNotice.class)
					.equalProperty("noticeId", "id")
					.equal("memberId", memberId)
				.end()
				.list();
	}
	
	@Override
	public List<Notice> getNotices(String groupId, String memberId) {
		return JpaUtil.linq(Notice.class)
				.collect("memberId", GroupMember.class, "sender")
				.notEqual("sender", memberId)
				.equal("groupId", groupId)
				.notExists(MemberNotice.class)
					.equalProperty("noticeId", "id")
					.equal("memberId", memberId)
				.end()
				.list();
	}
	
	@Override
	public Long getNoticeCount(String memberId) {
		return JpaUtil.linq(Notice.class)
			.notEqual("sender", memberId)
			.equal("type", Constants.MESSAGE_TYPE)
			.or()
				.exists(Group.class)
					.equalProperty("id", "groupId")
					.isTrue("all")
				.end()
				.exists(GroupMember.class)
					.equalProperty("groupId", "groupId")
					.equal("memberId", memberId)
				.end()
			.end()
			.notExists(MemberNotice.class)
				.equalProperty("groupId", "groupId")
				.equalProperty("noticeId", "id")
				.equal("memberId", memberId)
			.end()
			.count();
	}
	
	@Override
	@Transactional
	public void addNotice(Notice notice) {
		JpaUtil.linu(Group.class)
			.idEqual(notice.getGroupId())
			.set("lastNoticeId", notice.getId())
			.set("lastNoticeSendTime", notice.getSendTime())
			.update();
		org.malagu.linq.JpaUtil.persist(notice);
	}
	
	@Override
	public void loadUsers(Page<User> page, String memberId, String usernameOrNickname) {
		JpaUtil.linq(User.class)
			.notEqual("username", memberId)
			.or()
				.like("username", "%" + usernameOrNickname + "%")
				.like("nickname", "%" + usernameOrNickname + "%")
			.end()
			.list(page);
	}
	
	@Override
	public void loadUnselectedUsers(Page<User> page, String groupId, String memberId, String usernameOrNickname) {
		JpaUtil.linq(User.class)
			.notEqual("username", memberId)
			.addIf(groupId)
				.notExists(GroupMember.class)
					.equal("groupId", groupId)
					.equalProperty("memberId", "username")
					.isFalse("exited")
				.end()
			.endIf()
			.addIf(usernameOrNickname)
				.or()
					.like("username", "%" + usernameOrNickname + "%")
					.like("nickname", "%" + usernameOrNickname + "%")
				.end()
			.endIf()
			.list(page);
	}
	
	@Override
	public List<Notice> loadLastNotices(String groupId, String memberId) {
		List<Notice> notices = JpaUtil.linq(Notice.class)
			.collect("memberId", GroupMember.class, "sender")
			.equal("groupId", groupId)
			.or()
				.exists(MemberNotice.class)
					.equalProperty("noticeId", "id")
					.equal("memberId", memberId)
				.end()
				.equal("sender", memberId)
			.end()
			.desc("sendTime")
			.list(0, 10);
		Collections.<Notice>sort(notices, (o1, o2) -> o1.getSendTime().compareTo(o2.getSendTime()));
		return notices;
	}

	@Override
	@Transactional
	public void markRead(String groupId, String memberId) {
		List<Notice> notices = getNotices(groupId, memberId);
		int count = 0;
		Date now = new Date();
		for (Notice notice : notices) {
			count++;
			MemberNotice memberNotice = new MemberNotice();
			memberNotice.setId(UUID.randomUUID().toString());
			memberNotice.setMemberId(memberId);
			memberNotice.setNoticeId(notice.getId());
			memberNotice.setReadTime(now);
			JpaUtil.persist(memberNotice);
			if (count % 1000 == 0) {
				JpaUtil.flush(MemberNotice.class);
			}

		}
		
		this.send("admin", "user1", "test");

	}
	
	@Override
	@Transactional
	public Group getOrCreatePrivateLetterGroup(String sender, String receiver) {
		Group group = null;
		try {
			group = JpaUtil.linq(Group.class)
					.isTrue("privateLetter")
					.notExists(GroupMember.class)
						.equalProperty("groupId", "id")
						.notIn("memberId", sender, receiver)
					.end()
					.findOne();
		} catch (Exception e) {
			group = new Group();
			group.setPrivateLetter(true);
			group.setCreateTime(new Date());
			group.setCreator(sender);
			group.setId(UUID.randomUUID().toString());
			group.setMemberCount(2);
			
			GroupMember senderMember = new GroupMember();
			senderMember.setActive(true);
			senderMember.setGroupId(group.getId());
			senderMember.setMemberId(sender);
			senderMember.setNickname(JpaUtil.getOne(User.class, sender).getNickname());
			
			GroupMember receiverMember = new GroupMember();
			receiverMember.setActive(true);
			receiverMember.setGroupId(group.getId());
			receiverMember.setMemberId(receiver);
			receiverMember.setNickname(JpaUtil.getOne(User.class, receiver).getNickname());
			
			JpaUtil.persist(group);
			JpaUtil.persist(senderMember);
			JpaUtil.persist(receiverMember);
		}
		
		return group;
	}
	
	@Override
	@Transactional
	public void send(String sender, String receiver, String message) {
		Notice notice = new Notice();
		notice.setType("message");
		notice.setSender(sender);
		notice.setSendTime(new Date());
		notice.setContent(message);
		
		this.send(sender, receiver, notice);
	}
	
	@Override
	@Transactional
	public void send(String sender, String receiver, Notice notice) {
		if (notice.getGroupId() == null) {
			Group group = this.getOrCreatePrivateLetterGroup(sender, receiver);
			notice.setGroupId(group.getId());	
		}
		notice.setSender(sender);
		this.send(notice);
	}
	
	@Override
	@Transactional
	public void send(Notice notice) {
		Message message = new Message(notice.getType(), BeanMap.create(notice));
		noticeService.receive(message);
	}

}
