package com.bstek.bdf3.multitenant.ui.context;


import java.util.Map;

import org.malagu.multitenant.MultitenantUtils;

import com.bstek.dorado.core.el.ContextVarsInitializer;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public class MultitenantContextVarsInitializer implements ContextVarsInitializer {

	@Override
	public void initializeContext(Map<String, Object> vars) throws Exception {
		vars.put("loginOrgId", MultitenantUtils.getLoginOrgId());
		vars.put("loginOrg", MultitenantUtils.getLoginOrg());
	}

}
