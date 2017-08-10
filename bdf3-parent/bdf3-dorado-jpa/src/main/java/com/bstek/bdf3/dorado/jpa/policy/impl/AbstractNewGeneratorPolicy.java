package com.bstek.bdf3.dorado.jpa.policy.impl;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.util.ReflectionUtils;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public abstract class AbstractNewGeneratorPolicy implements GeneratorPolicy {

	@Override
	public void apply(Object entity, Field field) {
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
	
	protected abstract Object getValue(Object entity, Field field);

}
