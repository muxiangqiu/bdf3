package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.UUID;

import org.springframework.util.ReflectionUtils;

/**
 *@author Kevin.yang
 *@since 2015年5月18日
 */
public class UUIDPolicy extends AbstractNewGeneratorPolicy {

	@Override
	protected Object getValue(Object entity, Field field) {
		Object value = ReflectionUtils.getField(field, entity);
		if (value == null) {
			return UUID.randomUUID().toString();
		}
		return value;
	}

}
