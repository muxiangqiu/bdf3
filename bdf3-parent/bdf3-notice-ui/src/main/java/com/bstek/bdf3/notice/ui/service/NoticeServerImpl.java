package com.bstek.bdf3.notice.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstek.dorado.view.socket.Message;
import com.bstek.dorado.view.socket.Socket;
import com.bstek.dorado.view.socket.SocketConnectionListener;
import com.bstek.dorado.view.socket.SocketReceiveListener;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月8日
 */
@Service
public class NoticeServerImpl implements NoticeServer, SocketConnectionListener, SocketReceiveListener{
    
	@Autowired
	private com.bstek.bdf3.notice.service.NoticeService noticeService;

	@Override
	public void registerSocket(String member, Socket socket) {
		socket.addConnectionListener(this);
		socket.addReceiveListener(this);
		noticeService.registerSocket(member, socket);
	}
	
	@Override
	public void onReceive(Socket socket, Message message) {
		noticeService.receive(message);
	}
	
	@Override
	public void onDisconnect(Socket socket) {
		socket.removeConnectionListener(this);
		socket.removeReceiveListener(this);
		noticeService.removeSocket(socket);
		
	}

}
