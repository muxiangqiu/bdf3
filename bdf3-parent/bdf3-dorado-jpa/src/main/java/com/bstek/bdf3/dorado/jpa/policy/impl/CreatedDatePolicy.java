package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.util.Assert;

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
		EntityState state = EntityUtils.getState(entity);
		if (EntityState.NEW.equals(state)) {
			EntityUtils.setValue(entity, field.getName(), new Date());	
		}
	}

}
