package com.bstek.bdf3.multitenant.ui.policy;

import java.lang.reflect.Field;

import org.malagu.multitenant.MultitenantUtils;

import com.bstek.bdf3.dorado.jpa.policy.impl.AbstractNewGeneratorPolicy;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public class OrgIdPolicy extends AbstractNewGeneratorPolicy {

	@Override
	protected Object getValue(Object entity, Field field) {
		return MultitenantUtils.getLoginOrgId();
	}

}
