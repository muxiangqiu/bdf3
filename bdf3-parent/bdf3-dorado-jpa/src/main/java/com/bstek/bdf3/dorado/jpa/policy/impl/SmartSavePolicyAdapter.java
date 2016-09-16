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
public class SmartSavePolicyAdapter implements SavePolicy {

	@Override
	public void apply(SaveContext context) {
		Object entity = context.getEntity();
		EntityManager entityManager = context.getEntityManager();
		EntityState state = EntityUtils.getState(entity);
		if (EntityState.NEW.equals(state)) {
			beforeInsert(context);
			entityManager.persist(entity);
			afterInsert(context);
		} else if (EntityState.MODIFIED.equals(state) 
				|| EntityState.MOVED.equals(state)) {
			beforeUpdate(context);
			entityManager.merge(entity);
			afterUpdate(context);
		} else if (EntityState.DELETED.equals(state)) {
			beforeDelete(context);
			entityManager.remove(entityManager.merge(entity));
			afterDelete(context);
		}
	}
	
	public void beforeDelete(SaveContext context) {
		
	}
	
	public void afterDelete(SaveContext context) {
		
	}
	
	public void beforeInsert(SaveContext context) {
		
	}
	
	public void afterInsert(SaveContext context) {
		
	}
	
	public void beforeUpdate(SaveContext context) {
		
	}
	
	public void afterUpdate(SaveContext context) {
		
	}
	

}
