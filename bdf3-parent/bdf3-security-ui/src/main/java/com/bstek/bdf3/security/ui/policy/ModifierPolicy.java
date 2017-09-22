package com.bstek.bdf3.security.ui.policy;

import java.lang.reflect.Field;

import com.bstek.bdf3.dorado.jpa.policy.impl.AbstractModifiedGeneratorPolicy;
import com.bstek.bdf3.security.ContextUtils;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public class ModifierPolicy extends AbstractModifiedGeneratorPolicy {

	@Override
	protected Object getValue(Object entity, Field field) {
		return ContextUtils.getLoginUsername();
	}

}
