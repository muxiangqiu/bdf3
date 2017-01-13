package com.bstek.bdf3.sample.saas;

import org.springframework.stereotype.Component;

import com.bstek.bdf3.log.annotation.Log;
import com.bstek.bdf3.log.proxy.LogProxy;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月11日
 */
@Component
@Log(module = "BDF模块", category = "系统日志")
public class BDF3ControllerLogProxy implements LogProxy {

	@Override
	public boolean support(Object obj) {
		return true;
	}

}
