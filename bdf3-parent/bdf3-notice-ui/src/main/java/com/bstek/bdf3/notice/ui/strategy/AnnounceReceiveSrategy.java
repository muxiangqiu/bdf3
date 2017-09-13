package com.bstek.bdf3.notice.ui.strategy;

import java.util.Map;

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
@Order(Integer.MAX_VALUE - 100)
@Component
public class AnnounceReceiveSrategy implements ReceiveStrategy {
	
	public static final String NOTICE_TYPE = "message";
	
	@Autowired
	private SocketSource<Socket> socketSource;
	
	@Autowired
	private Pusher<Socket> pusher;
	
	@Autowired
	private NoticeService noticeService;

	@Override
	public void apply(Notice notice) {
		Map<String, Socket> socketMap = socketSource.getCurrentSockets();
		if (socketMap != null) {
			for (Socket socket : socketMap.values()) {
				pusher.push(socket, notice);
			}
		}
		noticeService.addNotice(notice);
	}

	@Override
	public boolean support(Notice notice) {
		if (NOTICE_TYPE.equals(notice.getType()) && notice.isAll()) {
			return true;
		}
		return false;
	}

}
