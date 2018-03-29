package com.bstek.bdf3.notice.ui.service;

import java.util.List;

import com.bstek.bdf3.notice.domain.Group;
import com.bstek.bdf3.notice.domain.GroupMember;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
public interface GroupService {
	
	List<String> getMemberIds(String groupId);
	
	List<Group> loadActiveGroups(String memberId);
	
	List<Group> loadGroups(String memberId);

	void save(List<Group> groups);

	Group loadActiveGroup(String groupId, String memberId);

	Group loadPrivateLetterGroup(String memberId, String otherId);

	void loadGroupMembers(Page<GroupMember> page, String groupId, String memberIdOrNickname);

	void freezeGroup(String groupId, String memberId);

	List<Group> loadWithoutSystemGroups(Page<Group> page, String memberId, String groupName);

	void exitGroup(String memberId, String groupId);

	List<Group> loadSystemGroups(Page<Group> page, String memberId, String groupName);

	void joinGroup(String memberId, String groupId, boolean administrator);
	
}
