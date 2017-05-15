package com.bstek.bdf3.saas.resource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.EntityManagerFactoryService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Component
@Order(1000)
public class DatabaseResourceReleaser implements ResourceReleaser {
	
	@Autowired
	private DataSourceProperties properties;
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Autowired
	private EntityManagerFactoryService entityManagerFactoryService;

	@Override
	public void release(Organization organization) {
		entityManagerFactoryService.removeEntityManagerFactory(organization);
		EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(emf);
		if (!EmbeddedDatabaseConnection.isEmbedded(properties.determineDriverClassName())) {
			em.createNativeQuery("drop database " + organization.getId()).executeUpdate();
		}
	}
}
