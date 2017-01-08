package com.bstek.bdf3.autoconfigure.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.bstek.bdf3.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.bstek.bdf3.autoconfigure.jdbc.DataSources;
import com.bstek.bdf3.autoconfigure.jpa.HibernateJpaBaseConfiguration.HibernateEntityManagerCondition;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月8日
 */
@Configuration
@ConditionalOnClass({ LocalContainerEntityManagerFactoryBean.class,
		EnableTransactionManagement.class, EntityManager.class })
@Conditional(HibernateEntityManagerCondition.class)
@ConditionalOnBean(name = DataSources.dataSource6)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class })
@EnableConfigurationProperties(Jpa6Properties.class)
public class HibernateJpa6AutoConfiguration extends HibernateJpaBaseConfiguration {

	@Autowired(required = false)
	@Qualifier(DataSources.dataSource6)
	private DataSource dataSource;
	
	@Autowired
	private Jpa6Properties jpaProperties;
	
	
	@Autowired(required = false)
	@Qualifier(JtaTransactionManagers.jtaTransactionManager6)
	private JtaTransactionManager jtaTransactionManager;
	
	
	@Override
	protected DataSource getDataSource() {
		return dataSource;
	}

	@Override
	protected JpaBaseProperties getJpaProperties() {
		return jpaProperties;
	}
	
	@Override
	protected JtaTransactionManager getJtaTransactionManager() {
		return jtaTransactionManager;
	}
	
	@Bean
	@ConditionalOnMissingBean(name = TransactionManagers.transactionManager6)
	public PlatformTransactionManager transactionManager6() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setPersistenceUnitName(EntityManagerFactories.entityManagerFactory6);
		return jpaTransactionManager;
	}
	
	@Bean
	@ConditionalOnMissingBean(name = EntityManagerFactories.entityManagerFactory6)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory6() {
		EntityManagerFactoryBuilder factoryBuilder = getEntityManagerFactoryBuilder();
		Map<String, Object> vendorProperties = getVendorProperties();
		customizeVendorProperties(vendorProperties);
		return factoryBuilder.dataSource(getDataSource()).packages(getPackagesToScan(jpaProperties.getPackagesToScan()))
				.properties(vendorProperties).jta(isJta()).persistenceUnit(EntityManagerFactories.entityManagerFactory6).build();
	}

}
