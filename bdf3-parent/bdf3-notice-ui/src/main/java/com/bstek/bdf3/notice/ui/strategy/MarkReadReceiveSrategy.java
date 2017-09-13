package com.bstek.bdf3.notice.ui.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.strategy.Pusher;
import com.bstek.bdf3.notice.strategy.ReceiveStrategy;
import com.bstek.bdf3.notice.strategy.SocketSource;
import com.bstek.bdf3.notice.ui.service.NoticeService;
import com.bstek.dorado.view.socket.Socket;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@Order
@Component
public class MarkReadReceiveSrategy implements ReceiveStrategy {
	
	public static final String NOTICE_TYPE = "mark-read";
	
	@Autowired
	private SocketSource<Socket> socketSource;
	
	@Autowired
	private Pusher<Socket> pusher;
	
	@Autowired
	private NoticeService noticeService;

	@Override
	public void apply(Notice notice) {
		Socket socket = socketSource.getSocket(notice.getSender());
		if (socket != null) {
			pusher.push(socket, notice);
		}
		noticeService.markRead(notice.getGroupId(), notice.getSender());;
	}

	@Override
	public boolean support(Notice notice) {
		if (NOTICE_TYPE.equals(notice.getType())) {
			return true;
		}
		return false;
	}

}
