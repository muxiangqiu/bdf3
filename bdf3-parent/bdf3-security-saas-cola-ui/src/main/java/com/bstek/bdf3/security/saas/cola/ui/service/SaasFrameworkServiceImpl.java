package com.bstek.bdf3.security.saas.cola.ui.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.security.cola.ui.service.FrameworkServiceImpl;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月15日
 */
@Primary
@Service
public class SaasFrameworkServiceImpl extends FrameworkServiceImpl implements SaasFrameworkService {

	@Override
	public String getLoginPage() {
		return "frame/saas/login";
	}

	@Override
	public String getMainPage() {
		return "frame/saas/main";
	}

	@Override
	public String getRegisterPage() {
		return "frame/saas/register";
	}

	
}
