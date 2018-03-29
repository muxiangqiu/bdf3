package com.bstek.bdf3.notice.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.notice.domain.Group;
import com.bstek.bdf3.notice.domain.GroupMember;
import com.bstek.bdf3.notice.ui.service.GroupService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
@Controller
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@DataProvider
	public List<Group> loadGroups(String memberId) {
		return groupService.loadGroups(memberId);
	}
	
	@DataProvider
	public List<Group> loadWithoutSystemGroups(Page<Group> page, String memberId, String groupName) {
		return groupService.loadWithoutSystemGroups(page, memberId, groupName);
	}
	
	@DataProvider
	public List<Group> loadSystemGroups(Page<Group> page, String memberId, String groupName) {
		return groupService.loadSystemGroups(page, memberId, groupName);
	}

	
	@DataProvider
	public List<Group> loadActiveGroups(String memberId) {
		return groupService.loadActiveGroups(memberId);
	}
	
	@DataProvider
	public void loadGroupMembers(Page<GroupMember> page, String groupId, String memberIdOrNickname) {
		groupService.loadGroupMembers(page, groupId, memberIdOrNickname);
	}
	
	@DataResolver
	public void save(List<Group> groups) {
		groupService.save(groups);
	}
	
	@DataProvider
	public Group loadActiveGroup(String groupId, String memberId) {
		return groupService.loadActiveGroup(groupId, memberId);
	}
	
	@DataProvider
	public Group loadPrivateLetterGroup(String memberId, String otherId) {
		return groupService.loadPrivateLetterGroup(memberId, otherId);
	}
	
	@Expose
	public void freezeGroup(String groupId, String memberId) {
		groupService.freezeGroup(groupId, memberId);
	}
	
	@Expose
	public void joinGroup(String groupId, String memberId) {
		groupService.joinGroup(memberId, groupId, false);
	}
	
	@Expose
	public void exitGroup(String groupId, String memberId) {
		groupService.exitGroup(memberId, groupId);
	}

}
