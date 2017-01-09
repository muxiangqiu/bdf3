package com.bstek.bdf3.saas.service;

import javax.persistence.EntityManagerFactory;

import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
public interface EntityManagerFactoryService {

	EntityManagerFactory getEntityManagerFactory(Organization organization);
	
	EntityManagerFactory createEntityManagerFactory(Organization organization);

	EntityManagerFactory getOrCreateEntityManagerFactory(Organization organization);
	
	void removeEntityManagerFactory(Organization organization);

}
