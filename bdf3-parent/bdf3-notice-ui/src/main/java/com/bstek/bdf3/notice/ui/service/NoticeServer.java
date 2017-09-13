package com.bstek.bdf3.notice.ui.service;

import com.bstek.dorado.view.socket.Socket;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月8日
 */
public interface NoticeServer {

	void registerSocket(String user, Socket socket);

}
