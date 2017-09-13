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

	void loadGroupMembers(Page<GroupMember> page, String groupId);

	void save(List<Group> groups);

	Group loadActiveGroup(String groupId, String memberId);

	Group loadPrivateLetterGroup(String memberId, String otherId);
	
}
