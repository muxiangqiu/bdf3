package com.bstek.bdf3.jpa.strategy.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.jpa.strategy.GetEntityManagerFactoryStrategy;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月13日
 */
@Component
public class GetEntityManagerFactoryStrategyImpl implements
		GetEntityManagerFactoryStrategy {
	
	@Autowired
	private List<EntityManagerFactory> entityManagerFactories;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	

	@Override
	public EntityManagerFactory getEntityManagerFactory(Class<?> domainClass) {
		if (domainClass == null) {
			return entityManagerFactory;
		}
		
		RuntimeException exception = new RuntimeException("entityManagerFactories can not be empty!");
		for (EntityManagerFactory emf : entityManagerFactories) {
			try {
				emf.getMetamodel().entity(domainClass);
				return emf;
			} catch (IllegalArgumentException e) {
				exception = e;
			}
		}
		throw exception;
	}

}
