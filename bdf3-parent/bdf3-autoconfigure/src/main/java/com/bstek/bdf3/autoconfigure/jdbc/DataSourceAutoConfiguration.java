package com.bstek.bdf3.autoconfigure.jdbc;

import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月10日
 */
@Configuration
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@EnableConfigurationProperties(DataSourceProperties.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class)
public class DataSourceAutoConfiguration {
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "spring.datasource1")
	public DataSourceInitializer dataSourceInitializer1(DataSourceProperties properties,
			ApplicationContext applicationContext) {
		return new DataSourceInitializer(properties, applicationContext, 1);
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "spring.datasource2")
	public DataSourceInitializer dataSourceInitializer2(DataSourceProperties properties,
			ApplicationContext applicationContext) {
		return new DataSourceInitializer(properties, applicationContext, 2);
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "spring.datasource3")
	public DataSourceInitializer dataSourceInitializer3(DataSourceProperties properties,
			ApplicationContext applicationContext) {
		return new DataSourceInitializer(properties, applicationContext, 3);
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "spring.datasource4")
	public DataSourceInitializer dataSourceInitializer4(DataSourceProperties properties,
			ApplicationContext applicationContext) {
		return new DataSourceInitializer(properties, applicationContext, 4);
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "spring.datasource5")
	public DataSourceInitializer dataSourceInitializer5(DataSourceProperties properties,
			ApplicationContext applicationContext) {
		return new DataSourceInitializer(properties, applicationContext, 5);
	}
	
	@Bean
	@ConditionalOnMissingBean
	@ConfigurationProperties(prefix = "spring.datasource6")
	public DataSourceInitializer dataSourceInitializer6(DataSourceProperties properties,
			ApplicationContext applicationContext) {
		return new DataSourceInitializer(properties, applicationContext, 6);
	}
	
	
	@Configuration
	@Conditional(PooledDataSourceCondition.class)
	@ConditionalOnMissingBean({ DataSource.class, XADataSource.class })
	@Import({ DataSourceConfiguration.Tomcat.class, DataSourceConfiguration.Hikari.class,
			DataSourceConfiguration.Dbcp2.class, DataSourceConfiguration.Generic.class })
	protected static class PooledDataSourceConfiguration {

	}

	/**
	 * {@link AnyNestedCondition} that checks that either {@code spring.datasource.type}
	 * is set or {@link PooledDataSourceAvailableCondition} applies.
	 */
	static class PooledDataSourceCondition extends AnyNestedCondition {

		PooledDataSourceCondition() {
			super(ConfigurationPhase.PARSE_CONFIGURATION);
		}

		@ConditionalOnProperty(prefix = "spring.datasource", name = "type")
		static class ExplicitType {

		}

		@Conditional(PooledDataSourceAvailableCondition.class)
		static class PooledDataSourceAvailable {

		}

	}

	/**
	 * {@link Condition} to test if a supported connection pool is available.
	 */
	static class PooledDataSourceAvailableCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("PooledDataSource");
			if (getDataSourceClassLoader(context) != null) {
				return ConditionOutcome
						.match(message.foundExactly("supported DataSource"));
			}
			return ConditionOutcome
					.noMatch(message.didNotFind("supported DataSource").atAll());
		}

		/**
		 * Returns the class loader for the {@link DataSource} class. Used to ensure that
		 * the driver class can actually be loaded by the data source.
		 * @param context the condition context
		 * @return the class loader
		 */
		private ClassLoader getDataSourceClassLoader(ConditionContext context) {
			Class<?> dataSourceClass = new DataSourceBuilder(context.getClassLoader())
					.findType();
			return (dataSourceClass == null ? null : dataSourceClass.getClassLoader());
		}

	}

	/**
	 * {@link Condition} to detect when an embedded {@link DataSource} type can be used.
	 * If a pooled {@link DataSource} is available, it will always be preferred to an
	 * {@code EmbeddedDatabase}.
	 */
	static class EmbeddedDatabaseCondition extends SpringBootCondition {

		private final SpringBootCondition pooledCondition = new PooledDataSourceCondition();

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("EmbeddedDataSource");
			if (anyMatches(context, metadata, this.pooledCondition)) {
				return ConditionOutcome
						.noMatch(message.foundExactly("supported pooled data source"));
			}
			EmbeddedDatabaseType type = EmbeddedDatabaseConnection
					.get(context.getClassLoader()).getType();
			if (type == null) {
				return ConditionOutcome
						.noMatch(message.didNotFind("embedded database").atAll());
			}
			return ConditionOutcome.match(message.found("embedded database").items(type));
		}

	}

	/**
	 * {@link Condition} to detect when a {@link DataSource} is available (either because
	 * the user provided one or because one will be auto-configured).
	 */
	@Order(Ordered.LOWEST_PRECEDENCE - 10)
	static class DataSourceAvailableCondition extends SpringBootCondition {

		private final SpringBootCondition pooledCondition = new PooledDataSourceCondition();

		private final SpringBootCondition embeddedCondition = new EmbeddedDatabaseCondition();

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("DataSourceAvailable");
			if (hasBean(context, DataSource.class)
					|| hasBean(context, XADataSource.class)) {
				return ConditionOutcome
						.match(message.foundExactly("existing data source bean"));
			}
			if (anyMatches(context, metadata, this.pooledCondition,
					this.embeddedCondition)) {
				return ConditionOutcome.match(message
						.foundExactly("existing auto-configured data source bean"));
			}
			return ConditionOutcome
					.noMatch(message.didNotFind("any existing data source bean").atAll());
		}

		private boolean hasBean(ConditionContext context, Class<?> type) {
			return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
					context.getBeanFactory(), type, true, false).length > 0;
		}

	}

}
