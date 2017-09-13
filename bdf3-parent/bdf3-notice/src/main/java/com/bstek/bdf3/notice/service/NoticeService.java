package com.bstek.bdf3.notice.service;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
public interface NoticeService {

	<T> void registerSocket(String key, T socket);
	
	<T> void removeSocket(T socket);
	
	<T> void receive(T notice);
	
}
