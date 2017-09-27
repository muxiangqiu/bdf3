package com.bstek.bdf3.saas.strategy;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.jpa.strategy.GetEntityManagerFactoryStrategy;
import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.EntityManagerFactoryService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月13日
 */
@Component("saas.getEntityManagerFactoryStrategyImpl")
@Primary
public class GetEntityManagerFactoryStrategyImpl implements
		GetEntityManagerFactoryStrategy {
	
	@Autowired
	private List<EntityManagerFactory> entityManagerFactories;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Autowired
	private EntityManagerFactoryService entityManagerFactoryService;

	@Override
	public EntityManagerFactory getEntityManagerFactory(Class<?> domainClass) {
		RuntimeException exception = new RuntimeException("entityManagerFactories can not be empty!");
		Organization organization = SaasUtils.peekOrganization();
		if (organization != null) {
			EntityManagerFactory entityManagerFactory = entityManagerFactoryService.getOrCreateEntityManagerFactory(organization);
			try {
				if (domainClass == null) {
					return entityManagerFactory;
				} else {
					entityManagerFactory.getMetamodel().entity(domainClass);
					return entityManagerFactory;
				}
			} catch (IllegalArgumentException e) {
				exception = e;
			}
		}
		
		if (domainClass == null) {
			return entityManagerFactory;
		}
		
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
