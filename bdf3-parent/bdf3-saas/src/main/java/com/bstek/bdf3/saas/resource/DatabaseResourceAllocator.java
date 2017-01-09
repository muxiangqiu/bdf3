package com.bstek.bdf3.saas.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.DataSourceService;
import com.bstek.bdf3.saas.service.EntityManagerFactoryService;
import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Component
@Order(1000)
public class DatabaseResourceAllocator implements ResourceAllocator {
	
	@Autowired
	private ConfigurableApplicationContext applicationContext;
	
	@Autowired
	private DataSourceProperties properties;
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Autowired
	private EntityManagerFactoryService entityManagerFactoryService;
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Value("${bdf3.saas.resourceScript:}")
	private String resourceScript;
	
	

	@Override
	public void allocate(Organization organization) {
		EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(emf);
		if (!EmbeddedDatabaseConnection.isEmbedded(properties.getDriverClassName())) {
			em.createNativeQuery("create database " + organization.getId()).executeUpdate();
		}
		EntityManagerFactory entityManagerFactory = entityManagerFactoryService.getOrCreateEntityManagerFactory(organization);
		try {
			entityManagerFactory.getMetamodel().entity(Url.class);
			runDataScripts(dataSourceService.getDataSource(organization));
		} catch (IllegalArgumentException e) {
		}
	}
	
	private void runDataScripts(DataSource dataSource) {
		List<Resource> scripts = getScripts(resourceScript, "saas");
		runScripts(scripts, dataSource);
	}

	private List<Resource> getScripts(String locations, String fallback) {
		if (StringUtils.isEmpty(locations)) {
			String platform = this.properties.getPlatform();
			locations = "classpath*:" + fallback + "-" + platform + ".sql,";
			locations += "classpath*:" + fallback + ".sql";
		}
		return getResources(locations);
	}

	private List<Resource> getResources(String locations) {
		List<Resource> resources = new ArrayList<Resource>();
		for (String location : StringUtils.commaDelimitedListToStringArray(locations)) {
			try {
				for (Resource resource : this.applicationContext.getResources(location)) {
					if (resource.exists()) {
						resources.add(resource);
					}
				}
			}
			catch (IOException ex) {
				throw new IllegalStateException(
						"Unable to load resource from " + location, ex);
			}
		}
		return resources;
	}

	private void runScripts(List<Resource> resources, DataSource dataSource) {
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
		DatabasePopulatorUtils.execute(populator, dataSource);
	}
	
	
}
