package com.bstek.bdf3.saas.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.saas.Constants;
import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.bdf3.saas.domain.DataSourceInfo;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.listener.DataSourceCreateListener;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Service
public class DataSourceServiceImpl implements DataSourceService, InitializingBean {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private DataSourceProperties properties;
	
	@Autowired
	private DataSourceInfoService dataSourceInfoService;
	
	private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>();

	@Autowired(required = false)
	private List<DataSourceCreateListener> listeners;
	
	@Autowired
	private DatabaseNameService databaseNameService;
	
	@Override
	public DataSource getDataSource(Organization organization) {
		return dataSourceMap.get(organization.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataSource createDataSource(Organization organization) {
		return SaasUtils.doQuery(() -> {
			DataSource dataSouce = null;
			DataSourceInfo dataSourceInfo = dataSourceInfoService.get(organization);
			if (StringUtils.isEmpty(dataSourceInfo.getJndiName())) {
				String master = Constants.MASTER;
				if (EmbeddedDatabaseConnection.isEmbedded(dataSourceInfo.getDriverClassName())) {
					master = properties.getName();
				}
				DataSourceBuilder factory = this.properties.initializeDataSourceBuilder();
				factory.url(dataSourceInfo.getUrl().replace(databaseNameService.getDatabaseName(master), databaseNameService.getDatabaseName(organization.getId())))
					.username(dataSourceInfo.getUsername())
					.password(dataSourceInfo.getPassword());
				if (!StringUtils.isEmpty(dataSourceInfo.getDriverClassName())) {
					factory.driverClassName(dataSourceInfo.getDriverClassName());
				}
				if (!StringUtils.isEmpty(dataSourceInfo.getType())) {
					try {
						factory.type((Class<? extends DataSource>) Class.forName(dataSourceInfo.getType()));
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e.getMessage());
					}
				}
				publishEvent(organization, dataSourceInfo, factory);
				dataSouce = factory.build();
			} else {
				JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
				dataSouce = dataSourceLookup.getDataSource(dataSourceInfo.getJndiName());
			}
			dataSourceMap.put(organization.getId(), dataSouce);
			return dataSouce;
		});

	}
	
	
	private void publishEvent(Organization organization, DataSourceInfo dataSourceInfo, DataSourceBuilder dataSourceBuilder) {
		if (listeners != null) {
			for (DataSourceCreateListener dataSourceCreateListener : listeners) {
				dataSourceCreateListener.onCreate(organization, dataSourceInfo, dataSourceBuilder);
			}
		}
		
	}

	@Override
	public SingleConnectionDataSource createSingleConnectionDataSource(Organization organization) {
		DataSourceInfo dataSourceInfo = dataSourceInfoService.get(organization);
		if (!StringUtils.isEmpty(dataSourceInfo.getJndiName())) {
			return null;
		}
		SingleConnectionDataSource dataSource = new SingleConnectionDataSource(
				dataSourceInfo.getUrl(), dataSourceInfo.getUsername(), dataSourceInfo.getPassword(), true);
		dataSource.setAutoCommit(true);
		return dataSource;
	}

	
	@Override
	public DataSource getOrCreateDataSource(String organizationId) {
		Organization organization = new Organization();
		organization.setId(organizationId);
		return getOrCreateDataSource(organization);
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

	@Override
	public void afterPropertiesSet() throws Exception {
		dataSourceMap.put(Constants.MASTER, dataSource);
		if (listeners != null) {
			AnnotationAwareOrderComparator.sort(listeners);
		}

		
	}

	@Override
	public void removeDataSource(Organization organization) {
		dataSourceMap.remove(organization.getId());
		
	}
	
	@Override
	public void clearDataSource() {
		dataSourceMap.clear();
	}
	
	

}
