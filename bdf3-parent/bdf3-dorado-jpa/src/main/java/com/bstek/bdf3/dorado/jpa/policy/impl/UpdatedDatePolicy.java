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
public class UpdatedDatePolicy implements GeneratorPolicy {

	@Override
	public void apply(Object entity, Field field) {
		Assert.isAssignable(Date.class, field.getType(), "Field type must be java.util.Date!");
		EntityState state = EntityUtils.getState(entity);
		if (EntityState.MODIFIED.equals(state)
				|| EntityState.MOVED.equals(state)
				|| EntityState.NEW.equals(state)) {
			if(field.getType() == Date.class) {
				EntityUtils.setValue(entity, field.getName(), new Date());	
			}
		}

	}

}
