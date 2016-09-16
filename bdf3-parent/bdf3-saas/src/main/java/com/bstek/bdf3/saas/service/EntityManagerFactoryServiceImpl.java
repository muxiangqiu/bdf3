package com.bstek.bdf3.saas.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.ClassUtils;

import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Service
public class EntityManagerFactoryServiceImpl implements
		EntityManagerFactoryService, BeanClassLoaderAware, BeanFactoryAware, BeanNameAware, ResourceLoaderAware, LoadTimeWeaverAware {
	
	private Map<String, EntityManagerFactory> emfMap = new ConcurrentHashMap<String, EntityManagerFactory>();
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Autowired(required = false)
	private JtaTransactionManager jtaTransactionManager;
		
	@Autowired
	private EntityManagerFactoryBuilder entityManagerFactoryBuilder;

	private LoadTimeWeaver loadTimeWeaver;

	private ResourceLoader resourceLoader;

	private ClassLoader classLoader;

	private String beanName;
	
	private static final Log logger = LogFactory
			.getLog(HibernateJpaAutoConfiguration.class);

	private static final String JTA_PLATFORM = "hibernate.transaction.jta.platform";

	/**
	 * {@code NoJtaPlatform} implementations for various Hibernate versions.
	 */
	private static final String[] NO_JTA_PLATFORM_CLASSES = {
			"org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform",
			"org.hibernate.service.jta.platform.internal.NoJtaPlatform" };

	/**
	 * {@code WebSphereExtendedJtaPlatform} implementations for various Hibernate
	 * versions.
	 */
	private static final String[] WEBSPHERE_JTA_PLATFORM_CLASSES = {
			"org.hibernate.engine.transaction.jta.platform.internal.WebSphereExtendedJtaPlatform",
			"org.hibernate.service.jta.platform.internal.WebSphereExtendedJtaPlatform", };

	@Autowired
	private JpaProperties properties;

	private BeanFactory beanFactory;
	
	@Value("${bdf3.saas.packagesToScan:com.bstek.bdf3.security.domain}")
	private String packagesToScan;
	


	@Override
	public EntityManagerFactory getEntityManagerFactory(Organization organization) {
		return emfMap.get(organization.getId());
	}

	@Override
	public EntityManagerFactory createEntityManagerFactory(Organization organization) {
		DataSource dataSource = dataSourceService.getOrCreateDataSource(organization);
		Map<String, Object> vendorProperties = getVendorProperties(dataSource);
		customizeVendorProperties(vendorProperties);
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = entityManagerFactoryBuilder.dataSource(dataSource).packages(packagesToScan.split(","))
				.properties(vendorProperties).jta(isJta()).build();
		entityManagerFactoryBean.setBeanClassLoader(classLoader);
		entityManagerFactoryBean.setBeanFactory(beanFactory);
		entityManagerFactoryBean.setBeanName(beanName);
		entityManagerFactoryBean.setLoadTimeWeaver(loadTimeWeaver);
		entityManagerFactoryBean.setResourceLoader(resourceLoader);
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean.getObject();
	}
	
	protected Map<String, Object> getVendorProperties(DataSource dataSource) {
		Map<String, Object> vendorProperties = new LinkedHashMap<String, Object>();
		vendorProperties.putAll(this.properties.getHibernateProperties(dataSource));
		return vendorProperties;
	}

	protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
		if (!vendorProperties.containsKey(JTA_PLATFORM)) {
			configureJtaPlatform(vendorProperties);
		}
	}

	private void configureJtaPlatform(Map<String, Object> vendorProperties)
			throws LinkageError {
		JtaTransactionManager jtaTransactionManager = getJtaTransactionManager();
		if (jtaTransactionManager != null) {
			if (runningOnWebSphere()) {
				configureWebSphereTransactionPlatform(vendorProperties);
			}
			else {
				configureSpringJtaPlatform(vendorProperties, jtaTransactionManager);
			}
		}
		else {
			vendorProperties.put(JTA_PLATFORM, getNoJtaPlatformManager());
		}
	}
	
	private boolean runningOnWebSphere() {
		return ClassUtils.isPresent(
				"com.ibm.websphere.jtaextensions." + "ExtendedJTATransaction",
				getClass().getClassLoader());
	}

	private void configureWebSphereTransactionPlatform(
			Map<String, Object> vendorProperties) {
		vendorProperties.put(JTA_PLATFORM, getWebSphereJtaPlatformManager());
	}

	private Object getWebSphereJtaPlatformManager() {
		return getJtaPlatformManager(WEBSPHERE_JTA_PLATFORM_CLASSES);
	}

	private void configureSpringJtaPlatform(Map<String, Object> vendorProperties,
			JtaTransactionManager jtaTransactionManager) {
		try {
			vendorProperties.put(JTA_PLATFORM,
					new SpringJtaPlatform(jtaTransactionManager));
		}
		catch (LinkageError ex) {		
			if (!isUsingJndi()) {
				throw new IllegalStateException("Unable to set Hibernate JTA "
						+ "platform, are you using the correct "
						+ "version of Hibernate?", ex);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Unable to set Hibernate JTA platform : " + ex.getMessage());
			}
		}
	}

	private boolean isUsingJndi() {
		try {
			return JndiLocatorDelegate.isDefaultJndiEnvironmentAvailable();
		}
		catch (Error ex) {
			return false;
		}
	}

	private Object getNoJtaPlatformManager() {
		return getJtaPlatformManager(NO_JTA_PLATFORM_CLASSES);
	}

	private Object getJtaPlatformManager(String[] candidates) {
		for (String candidate : candidates) {
			try {
				return Class.forName(candidate).newInstance();
			}
			catch (Exception ex) {
				// Continue searching
			}
		}
		throw new IllegalStateException("Could not configure JTA platform");
	}
	
	protected JtaTransactionManager getJtaTransactionManager() {
		return this.jtaTransactionManager;
	}

	protected final boolean isJta() {
		return (this.jtaTransactionManager != null);
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
		
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		
	}
	
	@Override
	public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
		this.loadTimeWeaver = loadTimeWeaver;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		
	}

	@Override
	public EntityManagerFactory getOrCreateEntityManagerFactory(
			Organization organization) {
		EntityManagerFactory emf = getEntityManagerFactory(organization);
		if (emf == null) {
			emf = createEntityManagerFactory(organization);
			emfMap.put(organization.getId(), emf);
		}
		return emf;
	}

}
