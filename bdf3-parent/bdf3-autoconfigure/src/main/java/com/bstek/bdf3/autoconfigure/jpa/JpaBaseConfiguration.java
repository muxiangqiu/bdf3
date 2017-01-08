/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bstek.bdf3.autoconfigure.jpa;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月8日
 */
public abstract class JpaBaseConfiguration implements BeanFactoryAware {

	private static final String[] NO_PACKAGES = new String[0];

	private ConfigurableListableBeanFactory beanFactory;
	
	@Autowired(required = false)
	private PersistenceUnitManager persistenceUnitManager;

	public JpaVendorAdapter getJpaVendorAdapter() {
		Object jpaProperties = getJpaProperties();
		AbstractJpaVendorAdapter adapter = createJpaVendorAdapter();
		if (jpaProperties instanceof JpaBaseProperties) {
			JpaBaseProperties properties = (JpaBaseProperties) jpaProperties;
			adapter.setShowSql(properties.isShowSql());
			adapter.setDatabase(properties.getDatabase());
			adapter.setDatabasePlatform(properties.getDatabasePlatform());
			adapter.setGenerateDdl(properties.isGenerateDdl());
		} else {
			org.springframework.boot.autoconfigure.orm.jpa.JpaProperties properties = (org.springframework.boot.autoconfigure.orm.jpa.JpaProperties) jpaProperties;
			adapter.setShowSql(properties.isShowSql());
			adapter.setDatabase(properties.getDatabase());
			adapter.setDatabasePlatform(properties.getDatabasePlatform());
			adapter.setGenerateDdl(properties.isGenerateDdl());
		}
		return adapter;
	}

	public EntityManagerFactoryBuilder getEntityManagerFactoryBuilder() {
		Object jpaProperties = getJpaProperties();
		JpaVendorAdapter jpaVendorAdapter = getJpaVendorAdapter();
		Map<String, String> properties = null;
		if (jpaProperties instanceof JpaBaseProperties) {
			properties = ((JpaBaseProperties) jpaProperties).getProperties();
		} else {
			properties = ((org.springframework.boot.autoconfigure.orm.jpa.JpaProperties) jpaProperties).getProperties();

		}
		if (properties != null && !properties.containsKey("hibernate.ejb.interceptor") && ClassUtils.isPresent("com.bstek.bdf3.dorado.jpa.UnByteCodeProxyInterceptor", this.getClass().getClassLoader())) {
			properties.put("hibernate.ejb.interceptor", "com.bstek.bdf3.dorado.jpa.UnByteCodeProxyInterceptor");
		}
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(
				jpaVendorAdapter, properties,
				this.persistenceUnitManager);
		builder.setCallback(getVendorCallback());
		return builder;
	}

	protected abstract AbstractJpaVendorAdapter createJpaVendorAdapter();

	protected abstract Map<String, Object> getVendorProperties();
	
	protected abstract DataSource getDataSource();
	
	protected abstract Object getJpaProperties();

	/**
	 * Return the JTA transaction manager.
	 * @return the transaction manager or {@code null}
	 */
	protected abstract JtaTransactionManager getJtaTransactionManager() ;


	/**
	 * Customize vendor properties before they are used. Allows for post processing (for
	 * example to configure JTA specific settings).
	 * @param vendorProperties the vendor properties to customize
	 */
	protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
	}

	protected EntityManagerFactoryBuilder.EntityManagerFactoryBeanCallback getVendorCallback() {
		return null;
	}

	protected String[] getPackagesToScan(String packagesToScan) {
		if (!StringUtils.isEmpty(packagesToScan)) {
			return packagesToScan.split(",");
		}
		return NO_PACKAGES;
	}
	
	protected String[] getPackagesToScan() {
		if (AutoConfigurationPackages.has(this.beanFactory)) {
			List<String> basePackages = AutoConfigurationPackages.get(this.beanFactory);
			return basePackages.toArray(new String[basePackages.size()]);
		}
		return NO_PACKAGES;
	}

	/**
	 * Returns if a JTA {@link PlatformTransactionManager} is being used.
	 * @return if a JTA transaction manager is being used
	 */
	protected final boolean isJta() {
		return (getJtaTransactionManager() != null);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	@Configuration
	@ConditionalOnWebApplication
	@ConditionalOnClass(WebMvcConfigurerAdapter.class)
	@ConditionalOnMissingBean({ OpenEntityManagerInViewInterceptor.class,
			OpenEntityManagerInViewFilter.class })
	@ConditionalOnProperty(prefix = "spring.jpa", name = "open-in-view", havingValue = "true", matchIfMissing = true)
	protected static class JpaWebConfiguration {

		// Defined as a nested config to ensure WebMvcConfigurerAdapter is not read when
		// not on the classpath
		@Configuration
		protected static class JpaWebMvcConfiguration extends WebMvcConfigurerAdapter {

			@Bean
			public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
				return new OpenEntityManagerInViewInterceptor();
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor());
			}

		}

	}

}
