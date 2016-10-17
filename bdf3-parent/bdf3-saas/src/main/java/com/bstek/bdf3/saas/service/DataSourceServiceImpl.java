package com.bstek.bdf3.saas.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

	@Autowired
	private DataSourceProperties properties;
	
	private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>();
	
	@Override
	public DataSource getDataSource(Organization organization) {
		return dataSourceMap.get(organization.getId());
	}

	@Override
	public DataSource createDataSource(Organization organization) {
		String master = "master";
		if (EmbeddedDatabaseConnection.isEmbedded(properties.getDriverClassName())) {
			master = properties.getName();
		}
		DataSourceBuilder factory = DataSourceBuilder
				.create(this.properties.getClassLoader())
				.driverClassName(this.properties.getDriverClassName())
				.url(this.properties.getUrl().replace(master, organization.getId())).username(this.properties.getUsername())
				.password(this.properties.getPassword());
		if (this.properties.getType() != null) {
			factory.type(this.properties.getType());
		}
		return factory.build();
	}

	@Override
	public DataSource getOrCreateDataSource(Organization organization) {
		DataSource dataSource = getDataSource(organization);
		if (dataSource == null) {
			dataSource = createDataSource(organization);
			dataSourceMap.put(organization.getId(), dataSource);
		}
		return dataSource;
	}

}
