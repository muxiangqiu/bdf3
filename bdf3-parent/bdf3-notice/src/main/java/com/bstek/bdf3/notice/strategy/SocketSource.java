package com.bstek.bdf3.notice.strategy;

import java.util.Map;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
public interface SocketSource<T> {
		
	void register(String key, T socket);
	
	void remove(T socket);
	
	T getSocket(String key);
	
	Map<String, T> getCurrentSockets();
	
	Map<String, T> getSockets(String category);
	
	

}
