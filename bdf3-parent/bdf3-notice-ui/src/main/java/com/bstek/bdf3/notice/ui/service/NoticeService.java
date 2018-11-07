package com.bstek.bdf3.notice.ui.service;

import java.util.List;

import com.bstek.bdf3.notice.domain.Group;
import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
public interface NoticeService {
	
	void getNotices(Page<Notice> page, String memberId);

	Long getNoticeCount(String memberId);

	void addNotice(Notice notice);

	List<Notice> getNotices(String memberId);
	
	void markRead(String groupId, String memberId);

	List<Notice> getNotices(String groupId, String memberId);

	void loadUnselectedUsers(Page<User> page, String groupId, String memberId, String usernameOrNickname);

	void loadUsers(Page<User> page, String memberId, String usernameOrNickname);

	List<Notice> loadLastNotices(String groupId, String memberId);

	Group getOrCreatePrivateLetterGroup(String sender, String receiver);

	void send(String sender, String receiver, String message);

	void send(String sender, String receiver, Notice notice);

	void send(Notice notice);

}
