package com.bstek.bdf3.autoconfigure.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.bstek.bdf3.autoconfigure.jdbc.DataSources;
import com.bstek.bdf3.autoconfigure.jpa.HibernateJpaAutoConfiguration.HibernateEntityManagerCondition;

@Configuration
@ConditionalOnClass({ LocalContainerEntityManagerFactoryBean.class,
		EnableTransactionManagement.class, EntityManager.class})
@Conditional(HibernateEntityManagerCondition.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(JpaProperties.class)
public class HibernateJpaAutoConfiguration extends org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Autowired(required = false)
	@Qualifier(DataSources.dataSource1)
	private DataSource dataSource1;
	
	@Autowired(required = false)
	@Qualifier(DataSources.dataSource2)
	private DataSource dataSource2;
	
	@Autowired(required = false)
	@Qualifier(DataSources.dataSource3)
	private DataSource dataSource3;
	
	@Autowired(required = false)
	@Qualifier(DataSources.dataSource4)
	private DataSource dataSource4;
	
	@Autowired(required = false)
	@Qualifier(DataSources.dataSource5)
	private DataSource dataSource5;
	
	@Autowired(required = false)
	@Qualifier(DataSources.dataSource6)
	private DataSource dataSource6;
	
	@Autowired
	private JpaProperties jpaProperties;
	
	protected LocalContainerEntityManagerFactoryBean createEntityManagerFactory(
			EntityManagerFactoryBuilder factoryBuilder, DataSource dataSource, String packagesToScan) {
		Map<String, Object> vendorProperties = getVendorProperties();
		customizeVendorProperties(vendorProperties);
		
		String[] psts = null;
		if (StringUtils.hasText(packagesToScan)) {
			psts = packagesToScan.split(",");
		}
		if (psts != null) {
			return factoryBuilder.dataSource(dataSource)
					.properties(vendorProperties).packages(psts).jta(isJta()).build();
		}
		return factoryBuilder.dataSource(dataSource)
				.properties(vendorProperties).jta(isJta()).build();
	}
	
	
	@Bean
	@ConditionalOnBean(name = DataSources.dataSource1)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory1(
			EntityManagerFactoryBuilder factoryBuilder) {
		return createEntityManagerFactory(factoryBuilder, dataSource1, jpaProperties.getPackagesToScan1());
	}
	
	@Bean
	@ConditionalOnBean(name = DataSources.dataSource2)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory2(
			EntityManagerFactoryBuilder factoryBuilder) {
		return createEntityManagerFactory(factoryBuilder, dataSource2, jpaProperties.getPackagesToScan2());

	}
	
	@Bean
	@ConditionalOnBean(name = DataSources.dataSource3)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory3(
			EntityManagerFactoryBuilder factoryBuilder) {
		return createEntityManagerFactory(factoryBuilder, dataSource3, jpaProperties.getPackagesToScan3());
	}
	
	@Bean
	@ConditionalOnBean(name = DataSources.dataSource4)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory4(
			EntityManagerFactoryBuilder factoryBuilder) {
		return createEntityManagerFactory(factoryBuilder, dataSource4, jpaProperties.getPackagesToScan4());
	}
	
	@Bean
	@ConditionalOnBean(name = DataSources.dataSource5)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory5(
			EntityManagerFactoryBuilder factoryBuilder) {
		return createEntityManagerFactory(factoryBuilder, dataSource5, jpaProperties.getPackagesToScan5());
	}
	
	@Bean
	@ConditionalOnBean(name = DataSources.dataSource6)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory6(
			EntityManagerFactoryBuilder factoryBuilder) {
		return createEntityManagerFactory(factoryBuilder, dataSource6, jpaProperties.getPackagesToScan6());
	}
	
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder factoryBuilder) {
		String packagesToScan = jpaProperties.getPackagesToScan();
		if (!StringUtils.hasText(packagesToScan)) {
			packagesToScan = StringUtils.arrayToCommaDelimitedString(getPackagesToScan());
		}
		return createEntityManagerFactory(factoryBuilder, dataSource, packagesToScan);
	}
	
	@Order(Ordered.HIGHEST_PRECEDENCE + 20)
	static class HibernateEntityManagerCondition extends SpringBootCondition {

		private static String[] CLASS_NAMES = {
				"org.hibernate.ejb.HibernateEntityManager",
				"org.hibernate.jpa.HibernateEntityManager" };

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			for (String className : CLASS_NAMES) {
				if (ClassUtils.isPresent(className, context.getClassLoader())) {
					return ConditionOutcome.match("found HibernateEntityManager class");
				}
			}
			return ConditionOutcome.noMatch("did not find HibernateEntityManager class");
		}
	}
}
