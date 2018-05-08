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
import com.bstek.bdf3.notice.domain.GroupTemplate;
import com.bstek.bdf3.notice.domain.MemberNotice;
import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.domain.Template;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.data.entity.EntityUtils;
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
				.isFalse("exited")
				.list();
	}

	@Override
	public List<Group> loadActiveGroups(String memberId) {
		List<Group> groups = JpaUtil.linq(Group.class)
				.toEntity()
				.collect(GroupTemplate.class, Template.class)
				.collect(Notice.class, "lastNoticeId")
				.exists(GroupMember.class)
					.equalProperty("groupId", "id")
					.equal("memberId", memberId)
					.isTrue("active")
					.isFalse("exited")
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
				.toEntity()
				.collect(Notice.class, "lastNoticeId")
				.collect(GroupTemplate.class, Template.class)
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
					.isFalse("exited")
					.list();
			Map<String, GroupMember> gmMap = JpaUtil.index(groupMembers, "groupId");
			Map<String, GroupMember>  ogmMap = null;
			if (!privateLetterGroupId.isEmpty()) {
				List<GroupMember> otherGroupMembers = JpaUtil.linq(GroupMember.class)
					.in("groupId", privateLetterGroupId)
					.notEqual("memberId", memberId)
					.isFalse("exited")
					.list();
				ogmMap = JpaUtil.index(otherGroupMembers, "groupId");
			}
			
			for (Group group : groups) {
				group.setCurrent(gmMap.get(group.getId()));
				if (ogmMap != null && !ogmMap.isEmpty()) {
					group.setOther(ogmMap.get(group.getId()));
				}
			}
			fillTemplate(groups);
		}
		
	}

	@Override
	public List<Group> loadGroups(String memberId) {
		List<Group> groups =  JpaUtil.linq(Group.class)
				.toEntity()
				.collect(GroupTemplate.class, Template.class)
				.isFalse("privateLetter")
				.or()
					.and()
						.isTrue("all")
						.exists(GroupMember.class)
							.equalProperty("groupId", "id")
							.equal("memberId", memberId)
							.isTrue("administrator")
							.isFalse("exited")
						.end()
					.end()
					.and()
						.isFalse("all")
						.exists(GroupMember.class)
							.equalProperty("groupId", "id")
							.equal("memberId", memberId)
							.isFalse("exited")
						.end()
					.end()
				.end()
				.asc("name")
				.list();
		parseGroups(groups, memberId);
		return groups;
	}
	
	@Override
	public List<Group> loadSystemGroups(Page<Group> page, String memberId, String groupName) {
		List<Group> groups =  JpaUtil.linq(Group.class)
				.isFalse("privateLetter")
				.isTrue("system")
				.addIf(groupName)
					.like("name", "%" + groupName + "%")
				.endIf()
				.exists(GroupMember.class)
					.equalProperty("groupId", "id")
					.equal("memberId", memberId)
					.isFalse("exited")
				.end()
				.asc("name")
				.list(page);
		return groups;
	}
	
	@Override
	public List<Group> loadWithoutSystemGroups(Page<Group> page, String memberId, String groupName) {
		List<Group> groups =  JpaUtil.linq(Group.class)
				.isFalse("privateLetter")
				.isTrue("system")
				.addIf(groupName)
					.like("name", "%" + groupName + "%")
				.endIf()
				.notExists(GroupMember.class)
					.equalProperty("groupId", "id")
					.equal("memberId", memberId)
					.isFalse("exited")
				.end()
				.asc("name")
				.list(page);
		return groups;
	}
	
	

	private void parseGroups(List<Group> groups, String memberId) {
		if (!groups.isEmpty()) {
			Set<String> groupIds =JpaUtil.collectId(groups);
			List<GroupMember> groupMembers = JpaUtil.linq(GroupMember.class)
					.in("groupId", groupIds)
					.equal("memberId", memberId)
					.isFalse("exited")
					.list();
			Map<String, GroupMember> map = JpaUtil.index(groupMembers, "groupId");
			for (Group group : groups) {
				group.setCurrent(map.get(group.getId()));
			}
			fillTemplate(groups);

		}
		
	}

	private void fillTemplate(List<Group> groups) {
		Set<String> templateIds =JpaUtil.collect(groups, "templateId");
		if (!templateIds.isEmpty()) {
			List<Template> templates = JpaUtil.linq(Template.class).in("id", templateIds).list();
			Map<String, Template> templateMap = JpaUtil.index(templates);
			for (Group group : groups) {
				if (group.getTemplateId() != null) {
					EntityUtils.setValue(group, "template", templateMap.get(group.getTemplateId()));

				}
			}
		}
	}
	
	@Transactional
	@Override
	public void joinGroup(String memberId, String groupId, boolean administrator) {
		User user = JpaUtil.getOne(User.class, memberId);
		Long count = JpaUtil.linq(GroupMember.class).equal("memberId", memberId).equal("groupId", groupId).count();
		Group group = JpaUtil.getOne(Group.class, groupId);
		if (count > 0) {
			if (JpaUtil.linu(GroupMember.class).equal("memberId", memberId).equal("groupId", groupId).set("administrator", administrator).set("nickname", user.getNickname()).set("exited", false).update() >0) {
				group.setMemberCount(group.getMemberCount() + 1);
			}
		} else {
			GroupMember groupMember = new GroupMember();
			groupMember.setActive(true);
			groupMember.setExited(false);
			groupMember.setGroupId(groupId);
			groupMember.setMemberId(memberId);
			groupMember.setAdministrator(administrator);
			groupMember.setNickname(user.getNickname());
			JpaUtil.persist(groupMember);
			group.setMemberCount(group.getMemberCount() + 1);

		}
		
	}
	
	@Transactional
	@Override
	public void exitGroup(String memberId, String groupId) {
		if (JpaUtil.linu(GroupMember.class).equal("memberId", memberId).equal("groupId", groupId).set("exited", true).update() > 0) {
			Group group = JpaUtil.getOne(Group.class, groupId);
			group.setMemberCount(group.getMemberCount() - 1);
		}
	}
	
	@Override
	public void loadGroupMembers(Page<GroupMember> page, String groupId, String memberIdOrNickname) {
		JpaUtil.linq(GroupMember.class)
			.equal("groupId", groupId)
			.isFalse("exited")
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

				} else if (entity instanceof GroupMember) {
					GroupMember groupMember = (GroupMember) entity;
					exitGroup(groupMember.getMemberId(), groupMember.getGroupId());
					return false;
				} else if (entity instanceof Template) {
					Group group = context.getParent();
					Template template = (Template) entity;
					JpaUtil.lind(GroupTemplate.class).equal("groupId", group.getId()).equal("templateId", template.getId()).delete();
					return false;
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
					GroupMember groupMember = (GroupMember) entity;
					joinGroup(groupMember.getMemberId(), group.getId(), groupMember.isAdministrator());
					return false;
				} else if (entity instanceof Template) {
					Group group = context.getParent();
					Template template = (Template) entity;
					GroupTemplate groupTemplate = new GroupTemplate();
					groupTemplate.setGroupId(group.getId());
					groupTemplate.setTemplateId(template.getId());
					JpaUtil.persist(groupTemplate);
					return false;
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
