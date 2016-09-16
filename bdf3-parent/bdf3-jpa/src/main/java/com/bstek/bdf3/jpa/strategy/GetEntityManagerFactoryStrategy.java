package com.bstek.bdf3.jpa.strategy;

import javax.persistence.EntityManagerFactory;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月13日
 */
public interface GetEntityManagerFactoryStrategy {
	
	EntityManagerFactory getEntityManagerFactory(Class<?> domainClass);

}
