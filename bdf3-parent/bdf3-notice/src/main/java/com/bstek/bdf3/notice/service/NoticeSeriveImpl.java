package com.bstek.bdf3.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.strategy.NoticeTransform;
import com.bstek.bdf3.notice.strategy.ReceiveStrategy;
import com.bstek.bdf3.notice.strategy.SocketRegister;
import com.bstek.bdf3.notice.strategy.SocketSource;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Service
public class NoticeSeriveImpl implements NoticeService {
	
	@Autowired
	private List<ReceiveStrategy> receiveStrategies;
	
	@Autowired
	private SocketSource socketSource;
	
	@Autowired
	private SocketRegister socketRegister;
	
	@Autowired
	private NoticeTransform noticeTransform;

	@Override
	public <T> void registerSocket(String key, T socket) {
		socketRegister.register(key, socket);
		
	}

	@Override
	public <T> void removeSocket(T socket) {
		socketSource.remove(socket);
		
	}

	@Override
	public <T> void receive(T notice) {
		Notice n = noticeTransform.transform(notice);
		for (ReceiveStrategy receiveStrategy : receiveStrategies) {
			if (receiveStrategy.support(n)) {
				receiveStrategy.apply(n);
				return;
			}
		}
	}

}
