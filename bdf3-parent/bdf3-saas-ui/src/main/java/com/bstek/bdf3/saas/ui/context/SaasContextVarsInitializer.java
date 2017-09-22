package com.bstek.bdf3.saas.ui.context;


import java.util.Map;

import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.dorado.core.el.ContextVarsInitializer;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public class SaasContextVarsInitializer implements ContextVarsInitializer {

	@Override
	public void initializeContext(Map<String, Object> vars) throws Exception {
		vars.put("loginOrgId", SaasUtils.getLoginOrgId());
		vars.put("loginOrg", SaasUtils.getLoginOrg());
	}

}
