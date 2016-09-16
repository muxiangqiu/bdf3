package com.bstek.bdf3.autoconfigure.dorado.jpa;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import com.bstek.bdf3.autoconfigure.jpa.JpaAutoConfiguration;
import com.bstek.bdf3.autoconfigure.dorado.jpa.HibernateJpaAutoConfiguration.HibernateEntityManagerCondition;
import com.bstek.bdf3.dorado.jpa.configure.JpaConfiguration;

@Configuration
@ConditionalOnClass({ LocalContainerEntityManagerFactoryBean.class,
		EnableTransactionManagement.class, EntityManager.class, JpaConfiguration.class })
@Conditional(HibernateEntityManagerCondition.class)
@AutoConfigureAfter(JpaAutoConfiguration.class)
@EnableConfigurationProperties(JpaProperties.class)
public class HibernateJpaAutoConfiguration {
	
	@Autowired
	private JpaProperties jpaProperties;
	
	@Autowired(required = false)
	private PersistenceUnitManager persistenceUnitManager;
	
	@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
			JpaVendorAdapter jpaVendorAdapter) {
		if (!this.jpaProperties.getProperties().containsKey("hibernate.ejb.interceptor")) {
			this.jpaProperties.getProperties().put("hibernate.ejb.interceptor", "com.bstek.bdf3.dorado.jpa.UnByteCodeProxyInterceptor");
		}
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(
				jpaVendorAdapter, this.jpaProperties.getProperties(),
				this.persistenceUnitManager);
		return builder;
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
