package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

/**
 *@author Kevin.yang
 *@since 2015年5月18日
 */
public class CreatedDatePolicy implements GeneratorPolicy {

	@Override
	public void apply(Object entity, Field field) {
		Assert.isAssignable(Date.class, field.getType(), "Field type must be java.util.Date!");
		if (EntityUtils.isEntity(entity)) {
			EntityState state = EntityUtils.getState(entity);
			if (EntityState.NEW.equals(state)) {
				EntityUtils.setValue(entity, field.getName(), new Date());	
			}
		} else {
			field.setAccessible(true);
			ReflectionUtils.setField(field, entity, new Date());
		}
	}

}
