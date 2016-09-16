package com.bstek.bdf3.saas.strategy;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.jpa.strategy.GetEntityManagerFactoryStrategy;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.EntityManagerFactoryService;
import com.bstek.bdf3.security.domain.OrganizationSupport;

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
	private EntityManagerFactoryService entityManagerFactoryService;

	@Override
	public EntityManagerFactory getEntityManagerFactory(Class<?> domainClass) {
		RuntimeException exception = new RuntimeException("entityManagerFactories can not be empty!");
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof OrganizationSupport) {
			OrganizationSupport support = (OrganizationSupport) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			EntityManagerFactory entityManagerFactory = entityManagerFactoryService.getOrCreateEntityManagerFactory((Organization) support.getOrganization());
			try {
				entityManagerFactory.getMetamodel().entity(domainClass);
				return entityManagerFactory;
			} catch (IllegalArgumentException e) {
				exception = e;
			}
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
