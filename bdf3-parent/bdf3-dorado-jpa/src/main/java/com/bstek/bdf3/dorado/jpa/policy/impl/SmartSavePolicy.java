package com.bstek.bdf3.dorado.jpa.policy.impl;

import javax.persistence.EntityManager;

import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;


/**
 *@author Kevin.yang
 *@since 2015年5月17日
 */
public class SmartSavePolicy implements SavePolicy {

	@Override
	public void apply(SaveContext context) {
		Object entity = context.getEntity();
		EntityManager entityManager = context.getEntityManager();
		EntityState state = EntityUtils.getState(entity);
		if (EntityState.NEW.equals(state)) {
			entityManager.persist(entity);
		} else if (EntityState.MODIFIED.equals(state) 
				|| EntityState.MOVED.equals(state)) {
			entityManager.merge(entity);
		} else if (EntityState.DELETED.equals(state)) {
			entityManager.remove(entityManager.merge(entity));
		}
	}

}
