package com.bstek.bdf3.log.context;
/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
public interface ContextHandler {
	<T> T compile(String value);
	void set(String key, Object value);
	Object get(String key);
	boolean has(String key);
}
