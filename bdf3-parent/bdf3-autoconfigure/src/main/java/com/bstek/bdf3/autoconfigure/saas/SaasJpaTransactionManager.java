package com.bstek.bdf3.autoconfigure.saas;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.DataSourceService;
import com.bstek.bdf3.saas.service.EntityManagerFactoryService;

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
		Organization organization = SaasUtils.peekOrganization();
		if (organization != null) {
			return entityManagerFactoryService.getEntityManagerFactory(organization);
		}
		return super.getEntityManagerFactory();
		
	}

	@Override
	public DataSource getDataSource() {
		Organization organization = SaasUtils.peekOrganization();
		if (organization != null) {
			return dataSourceService.getDataSource(organization);
		}
		return super.getDataSource();
	}	
	

}
