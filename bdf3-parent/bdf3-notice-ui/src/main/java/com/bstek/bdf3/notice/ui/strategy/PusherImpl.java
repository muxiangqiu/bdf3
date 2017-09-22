package com.bstek.bdf3.notice.ui.strategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.notice.domain.Notice;
import com.bstek.bdf3.notice.strategy.Pusher;
import com.bstek.dorado.view.socket.Message;
import com.bstek.dorado.view.socket.Socket;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@Component
public class PusherImpl implements Pusher<Socket> {
	
    private static final Log logger = LogFactory.getLog(PusherImpl.class);


	@Override
	public void push(Socket socket, Notice notice) {
		Message message = new Message(notice.getType(), notice);
		try {
			socket.send(message);
		} catch (Exception e) {
			logger.error(e, e);
		}	
		
	}

}
