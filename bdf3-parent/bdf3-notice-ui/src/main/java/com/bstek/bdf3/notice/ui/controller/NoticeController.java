package com.bstek.bdf3.notice.ui.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.ui.service.NoticeServer;
import com.bstek.bdf3.notice.ui.service.NoticeService;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.view.socket.Socket;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private NoticeServer noticeServer;
	
	@Expose
	public void connectServer(Socket socket, String member) {
		noticeServer.registerSocket(member, socket);
	}
	
	@DataProvider
	public List<Notice> loadLastNotices(String groupId, String memberId) {
		return noticeService.loadLastNotices(groupId, memberId);
	}

	@DataProvider
	public void loadUsers(Page<User> page, String memberId, String usernameOrNickname) {
		noticeService.loadUsers(page, memberId, usernameOrNickname);
	}
	
	@DataProvider
	public void loadUnselectedUsers(Page<User> page, Map<String, String> params) {
		String groupId = params.get("groupId");
		String memberId = params.get("memberId");
		String usernameOrNickname = params.get("usernameOrNickname");
		noticeService.loadUnselectedUsers(page, groupId, memberId, usernameOrNickname);
	}
	
	
	

}
