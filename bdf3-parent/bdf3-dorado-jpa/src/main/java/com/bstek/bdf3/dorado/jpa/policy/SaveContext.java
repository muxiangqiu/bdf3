package com.bstek.bdf3.dorado.jpa.policy;

import javax.persistence.EntityManager;

/**
 *@author Kevin.yang
 *@since 2015年9月9日
 */
public class SaveContext {
	private Object entity;
	private EntityManager entityManager;
	private Object parent;
	
	@SuppressWarnings("unchecked")
	public <T> T getEntity() {
		return (T) entity;
	}
	public void setEntity(Object entity) {
		this.entity = entity;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getParent() {
		return (T) parent;
	}
	public void setParent(Object parent) {
		this.parent = parent;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	
}
