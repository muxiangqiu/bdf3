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
			try {
				entityManager.persist(EntityUtils.toPureData(entity));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (EntityState.MODIFIED.equals(state) 
				|| EntityState.MOVED.equals(state)) {
			try {
				entityManager.merge(EntityUtils.toPureData(entity));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (EntityState.DELETED.equals(state)) {
			try {
				entityManager.remove(entityManager.merge(EntityUtils.toPureData(entity)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
