package com.bstek.bdf3.notice.ui.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.notice.domain.Group;
import com.bstek.bdf3.notice.domain.GroupMember;
import com.bstek.bdf3.notice.domain.MemberNotice;
import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService{

	@Override
	public List<String> getMemberIds(String groupId) {
		return JpaUtil.linq(GroupMember.class, String.class)
				.select("memberId")
				.equal("groupId", groupId)
				.list();
	}

	@Override
	public List<Group> loadActiveGroups(String memberId) {
		List<Group> groups = JpaUtil.linq(Group.class)
				.collect(Notice.class, "lastNoticeId")
				.exists(GroupMember.class)
					.equalProperty("groupId", "id")
					.equal("memberId", memberId)
					.isTrue("active")
				.end()
				.desc("lastNoticeSendTime")
				.list();
		parseActiveGroups(groups, memberId);
		return groups;
	}
	
	@Override
	@Transactional
	public Group loadActiveGroup(String groupId, String memberId) {
		List<Group> groups = JpaUtil.linq(Group.class)
				.collect(Notice.class, "lastNoticeId")
				.idEqual(groupId)
				.list();
		Group group = groups.get(0);
		if (!group.isAll() && !group.isSystem()) {
			JpaUtil.linu(GroupMember.class)
				.equal("groupId", groups.get(0).getId())
				.equal("memberId", memberId)
				.set("active", true)
				.update();
		}
		parseActiveGroups(groups, memberId);
		return groups.get(0);
	}

	private void parseActiveGroups(List<Group> groups, String memberId) {
		if (!groups.isEmpty()) {
			Set<String> groupIds = new HashSet<>();
			Set<String> privateLetterGroupId = new HashSet<>();
			for (Group group : groups) {
				groupIds.add(group.getId());
				if (group.isPrivateLetter()) {
					privateLetterGroupId.add(group.getId());
				}
			}
			List<GroupMember> groupMembers = JpaUtil.linq(GroupMember.class)
					.in("groupId", groupIds)
					.equal("memberId", memberId)
					.list();
			Map<String, GroupMember> gmMap = JpaUtil.index(groupMembers, "groupId");
			Map<String, GroupMember>  ogmMap = null;
			if (!privateLetterGroupId.isEmpty()) {
				List<GroupMember> otherGroupMembers = JpaUtil.linq(GroupMember.class)
					.in("groupId", privateLetterGroupId)
					.notEqual("memberId", memberId)
					.list();
				ogmMap = JpaUtil.index(otherGroupMembers, "groupId");
			}
			
			for (Group group : groups) {
				group.setCurrent(gmMap.get(group.getId()));
				if (ogmMap != null && !ogmMap.isEmpty()) {
					group.setOther(ogmMap.get(group.getId()));
				}
			}
		}
		
	}

	@Override
	public List<Group> loadGroups(String memberId) {
		List<Group> groups =  JpaUtil.linq(Group.class)
				.isFalse("temporary")
				.isFalse("privateLetter")
				.or()
					.and()
						.isTrue("system")
						.exists(GroupMember.class)
							.equalProperty("groupId", "id")
							.equal("memberId", memberId)
							.isTrue("administrator")
						.end()
					.end()
					.and()
						.isFalse("system")
						.exists(GroupMember.class)
							.equalProperty("groupId", "id")
							.equal("memberId", memberId)
						.end()
					.end()
				.end()
				.asc("name")
				.list();
		parseGroups(groups, memberId);
		return groups;
	}

	private void parseGroups(List<Group> groups, String memberId) {
		if (!groups.isEmpty()) {
			Set<String> groupIds =JpaUtil.collectId(groups);
			List<GroupMember> groupMembers = JpaUtil.linq(GroupMember.class)
					.in("groupId", groupIds)
					.equal("memberId", memberId)
					.list();
			Map<String, GroupMember> map = JpaUtil.index(groupMembers, "groupId");
			for (Group group : groups) {
				group.setCurrent(map.get(group.getId()));
			}
		}
		
	}
	
	@Override
	public void loadGroupMembers(Page<GroupMember> page, String groupId, String memberIdOrNickname) {
		JpaUtil.linq(GroupMember.class)
			.equal("groupId", groupId)
			.addIf(memberIdOrNickname)
				.or()
					.like("memberId", "%" + memberIdOrNickname + "%")
					.like("nickname", "%" + memberIdOrNickname + "%")
				.end()
			.endIf()
			.paging(page);
	}
	
	@Override
	@Transactional
	public void save(List<Group> groups) {
		JpaUtil.save(groups, new SmartSavePolicyAdapter() {

			@Override
			public boolean beforeDelete(SaveContext context) {
				Object entity = context.getEntity();
				if (entity instanceof Group) {
					JpaUtil.lind(GroupMember.class).equal("groupId", ((Group) entity).getId()).delete();
					JpaUtil.lind(MemberNotice.class)
						.exists(Notice.class)
							.equal("groupId", ((Group) entity).getId())
						.end()
						.delete();
					JpaUtil.lind(Notice.class).equal("groupId", ((Group) entity).getId()).delete();
				}
				return true;
			}

			@Override
			public boolean beforeInsert(SaveContext context) {
				Object entity = context.getEntity();
				if (entity instanceof Group) {
					((Group) entity).setCreateTime(new Date());
				} else if (entity instanceof GroupMember) {
					Group group = context.getParent();
					((GroupMember) entity).setGroupId(group.getId());
				}
				return true;
			}
			
		});
	}

	@Override
	@Transactional
	public Group loadPrivateLetterGroup(String memberId, String otherId) {
		List<Group> groups = JpaUtil.linq(Group.class)
			.isTrue("privateLetter")
			.notExists(GroupMember.class)
				.equalProperty("groupId", "id")
				.notIn("memberId",memberId, otherId)
			.end()
			.list();
		if (!groups.isEmpty()) {
			JpaUtil.linu(GroupMember.class)
				.equal("groupId", groups.get(0).getId())
				.equal("memberId", memberId)
				.set("active", true)
				.update();
			parseGroups(groups, memberId);
			return groups.get(0);
		}
		
		return null;
	}

	@Override
	@Transactional
	public void freezeGroup(String groupId, String memberId) {
		JpaUtil.linu(GroupMember.class)
			.equal("groupId", groupId)
			.equal("memberId", memberId)
			.set("active", false)
			.update();
	}
}
