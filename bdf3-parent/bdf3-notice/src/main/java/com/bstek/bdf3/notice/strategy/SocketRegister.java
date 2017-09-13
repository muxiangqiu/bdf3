package com.bstek.bdf3.notice.strategy;
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
public interface SocketRegister<T> {
	
	void register(String key, T socket);
}
