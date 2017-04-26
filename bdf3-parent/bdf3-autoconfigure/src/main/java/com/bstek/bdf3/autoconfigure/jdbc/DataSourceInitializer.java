
package com.bstek.bdf3.autoconfigure.jdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceInitializedEvent;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;


class DataSourceInitializer implements ApplicationListener<DataSourceInitializedEvent> {

	private static final Log logger = LogFactory.getLog(DataSourceInitializer.class);

	private final DataSourceProperties properties;

	private final ApplicationContext applicationContext;

	private DataSource dataSource;

	private boolean initialized = false;
	
	private final int index;
	
	private final String[] dataSourceIds = { DataSources.dataSource, DataSources.dataSource1, DataSources.dataSource2,
			DataSources.dataSource3, DataSources.dataSource4, DataSources.dataSource5, DataSources.dataSource6 };

	DataSourceInitializer(DataSourceProperties properties,
			ApplicationContext applicationContext, int index) {
		this.properties = properties;
		this.applicationContext = applicationContext;
		this.index = index;
	}

	@PostConstruct
	public void init() {
		if (!this.properties.isInitialize()) {
			logger.debug("Initialization disabled (not running DDL scripts)");
			return;
		}
		if (this.applicationContext.containsBean(dataSourceIds[index])) {
			this.dataSource = (DataSource) this.applicationContext.getBean(dataSourceIds[index]);
		}
		if (this.dataSource == null) {
			logger.debug("No DataSource found so not initializing");
			return;
		}
		runSchemaScripts();
	}

	private void runSchemaScripts() {
		String indexStr = getIndexStr();
		List<Resource> scripts = getScripts("spring.datasource" + indexStr + ".schema",
				this.properties.getSchema(), "schema" + indexStr);
		if (!scripts.isEmpty()) {
			String username = this.properties.getSchemaUsername();
			String password = this.properties.getSchemaPassword();
			runScripts(scripts, username, password);
			try {
				this.applicationContext
						.publishEvent(new DataSourceInitializedEvent(this.dataSource));
				// The listener might not be registered yet, so don't rely on it.
				if (!this.initialized) {
					runDataScripts();
					this.initialized = true;
				}
			}
			catch (IllegalStateException ex) {
				logger.warn("Could not send event to complete DataSource initialization ("
						+ ex.getMessage() + ")");
			}
		}
	}

	private String getIndexStr() {
		return String.valueOf(index == 0 ? "" : index);
	}

	@Override
	public void onApplicationEvent(DataSourceInitializedEvent event) {
		if (!this.properties.isInitialize()) {
			logger.debug("Initialization disabled (not running data scripts)");
			return;
		}
		// NOTE the event can happen more than once and
		// the event datasource is not used here
		if (!this.initialized) {
			runDataScripts();
			this.initialized = true;
		}
	}

	private void runDataScripts() {
		String indexStr = getIndexStr();
		List<Resource> scripts = getScripts("spring.datasource" + indexStr + ".data",
				this.properties.getData(), "data" + indexStr);
		String username = this.properties.getDataUsername();
		String password = this.properties.getDataPassword();
		runScripts(scripts, username, password);
	}

	private List<Resource> getScripts(String propertyName, List<String> resources,
			String fallback) {
		if (resources != null) {
			return getResources(propertyName, resources, true);
		}
		String platform = this.properties.getPlatform();
		List<String> fallbackResources = new ArrayList<String>();
		fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
		fallbackResources.add("classpath*:" + fallback + ".sql");
		return getResources(propertyName, fallbackResources, false);
	}

	private List<Resource> getResources(String propertyName, List<String> locations,
			boolean validate) {
		List<Resource> resources = new ArrayList<Resource>();
		for (String location : locations) {
			for (Resource resource : doGetResources(location)) {
				if (resource.exists()) {
					resources.add(resource);
				}
				else if (validate) {
					throw new ResourceNotFoundException(propertyName, resource);
				}
			}
		}
		return resources;
	}

	private Resource[] doGetResources(String location) {
		try {
			SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(
					this.applicationContext, Collections.singletonList(location));
			factory.afterPropertiesSet();
			return factory.getObject();
		}
		catch (Exception ex) {
			throw new IllegalStateException("Unable to load resources from " + location,
					ex);
		}
	}

	private void runScripts(List<Resource> resources, String username, String password) {
		if (resources.isEmpty()) {
			return;
		}
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.setContinueOnError(this.properties.isContinueOnError());
		populator.setSeparator(this.properties.getSeparator());
		if (this.properties.getSqlScriptEncoding() != null) {
			populator.setSqlScriptEncoding(this.properties.getSqlScriptEncoding().name());
		}
		for (Resource resource : resources) {
			populator.addScript(resource);
		}
		DataSource dataSource = this.dataSource;
		if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
			dataSource = DataSourceBuilder.create(this.properties.getClassLoader())
					.driverClassName(this.properties.determineDriverClassName())
					.url(this.properties.determineUrl()).username(username)
					.password(password).build();
		}
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

}
