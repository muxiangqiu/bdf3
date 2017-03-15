package com.bstek.bdf3.log.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.log.annotation.Log;
import com.bstek.bdf3.log.proxy.LogProxy;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月11日
 */
@Component
@Log(module = "系统模块", category = "系统日志")
public class DataResolverLogProxy implements LogProxy {

	@Value("${bdf3.log.disabled}")
	private boolean disabled;
	
	@Value("${bdf3.log.defaultLog}")
	private boolean defaultLog;

	
	@Override
	public boolean support(Object obj) {
		return !disabled && defaultLog;
	}

}
