package com.bstek.bdf3.autoconfigure.saas;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.DataSourceService;
import com.bstek.bdf3.saas.service.EntityManagerFactoryService;
import com.bstek.bdf3.security.orm.OrganizationSupport;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月16日
 */
public class SaasJpaTransactionManager extends JpaTransactionManager {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EntityManagerFactoryService entityManagerFactoryService;
	
	@Autowired
	private DataSourceService dataSourceService;

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof OrganizationSupport) {
			OrganizationSupport support = (OrganizationSupport) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return entityManagerFactoryService.getOrCreateEntityManagerFactory((Organization) support.getOrganization());
		}
		return super.getEntityManagerFactory();
	}

	@Override
	public DataSource getDataSource() {
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof OrganizationSupport) {
			OrganizationSupport support = (OrganizationSupport) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return dataSourceService.getDataSource((Organization) support.getOrganization());
		}
		return super.getDataSource();
	}	
	

}
