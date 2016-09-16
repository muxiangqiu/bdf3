package com.bstek.bdf3.saas.service;

import javax.sql.DataSource;

import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
public interface DataSourceService {

	DataSource getDataSource(Organization organization);
	
	DataSource createDataSource(Organization organization);
	
	DataSource getOrCreateDataSource(Organization organization);
}
