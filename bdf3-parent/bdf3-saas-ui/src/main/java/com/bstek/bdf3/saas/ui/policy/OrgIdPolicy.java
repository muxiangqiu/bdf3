package com.bstek.bdf3.saas.ui.policy;

import java.lang.reflect.Field;

import com.bstek.bdf3.dorado.jpa.policy.impl.AbstractNewGeneratorPolicy;
import com.bstek.bdf3.saas.SaasUtils;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public class OrgIdPolicy extends AbstractNewGeneratorPolicy {

	@Override
	protected Object getValue(Object entity, Field field) {
		return SaasUtils.getLoginOrgId();
	}

}
