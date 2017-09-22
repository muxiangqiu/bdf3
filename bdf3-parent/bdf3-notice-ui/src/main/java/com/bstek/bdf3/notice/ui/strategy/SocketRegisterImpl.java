package com.bstek.bdf3.notice.ui.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.strategy.Pusher;
import com.bstek.bdf3.notice.strategy.SocketRegister;
import com.bstek.bdf3.notice.strategy.SocketSource;
import com.bstek.bdf3.notice.ui.service.NoticeService;
import com.bstek.dorado.view.socket.Socket;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@Component
public class SocketRegisterImpl implements SocketRegister<Socket> {
	
	@Autowired
	private SocketSource<Socket> socketSource;
	
	@Autowired
	private Pusher<Socket> pusher;
	
	@Autowired
	private NoticeService noticeService;

	@Override
	public void register(String key, Socket socket) {
		socketSource.register(key, socket);
		List<Notice> notices = noticeService.getNotices(key);
		for (Notice notice : notices) {
			pusher.push(socket, notice);
		}
	}

}
