package com.bstek.bdf3.autoconfigure.security;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月14日
 */
public class StaticKeyGenerator implements KeyGenerator {
	
	@Override
	public Object generate(Object target, Method method, Object... params) {
		return "BDF3_SECURITY";
	}

}
