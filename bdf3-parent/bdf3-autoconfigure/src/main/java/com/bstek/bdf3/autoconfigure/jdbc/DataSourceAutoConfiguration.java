package com.bstek.bdf3.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月10日
 */
@Configuration
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@EnableConfigurationProperties({
	org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.class,
	DataSource1Properties.class,
	DataSource2Properties.class,
	DataSource3Properties.class,
	DataSource4Properties.class,
	DataSource5Properties.class,
	DataSource6Properties.class
})
@AutoConfigureAfter(org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class)
public class DataSourceAutoConfiguration {
	
	@Conditional(NonEmbeddedDataSourceCondition.class)
	protected static class NonEmbeddedConfiguration {

		@Autowired
		private org.springframework.boot.autoconfigure.jdbc.DataSourceProperties properties;

		@Bean
		@Primary
		@ConfigurationProperties(prefix = org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.PREFIX)
		public DataSource dataSource() {
			DataSourceBuilder factory = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName())
					.url(this.properties.getUrl()).username(this.properties.getUsername())
					.password(this.properties.getPassword());
			if (this.properties.getType() != null) {
				factory.type(this.properties.getType());
			}
			return factory.build();
		}
	}

	@Conditional(NonEmbeddedDataSourceCondition.class)
	@ConditionalOnProperty(prefix = DataSource1Properties.PREFIX, name = "url")
	protected static class NonEmbedded1Configuration {

		@Autowired
		private DataSource1Properties properties;

		@Bean
		@ConfigurationProperties(prefix = DataSource1Properties.PREFIX)
		public DataSource dataSource1() {
			DataSourceBuilder factory = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName())
					.url(this.properties.getUrl()).username(this.properties.getUsername())
					.password(this.properties.getPassword());
			if (this.properties.getType() != null) {
				factory.type(this.properties.getType());
			}
			return factory.build();
		}
	}
	
	@Conditional(NonEmbeddedDataSourceCondition.class)
	@ConditionalOnProperty(prefix = DataSource2Properties.PREFIX, name = "url")
	protected static class NonEmbedded2Configuration {

		@Autowired
		private DataSource2Properties properties;

		@Bean
		@ConfigurationProperties(prefix = DataSource2Properties.PREFIX)
		public DataSource dataSource2() {
			DataSourceBuilder factory = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName())
					.url(this.properties.getUrl()).username(this.properties.getUsername())
					.password(this.properties.getPassword());
			if (this.properties.getType() != null) {
				factory.type(this.properties.getType());
			}
			return factory.build();
		}
	}
	
	@Conditional(NonEmbeddedDataSourceCondition.class)
	@ConditionalOnProperty(prefix = DataSource3Properties.PREFIX, name = "url")
	protected static class NonEmbedded3Configuration {

		@Autowired
		private DataSource3Properties properties;

		@Bean
		@ConfigurationProperties(prefix = DataSource3Properties.PREFIX)
		public DataSource dataSource3() {
			DataSourceBuilder factory = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName())
					.url(this.properties.getUrl()).username(this.properties.getUsername())
					.password(this.properties.getPassword());
			if (this.properties.getType() != null) {
				factory.type(this.properties.getType());
			}
			return factory.build();
		}
	}
	
	@Conditional(NonEmbeddedDataSourceCondition.class)
	@ConditionalOnProperty(prefix = DataSource4Properties.PREFIX, name = "url")
	protected static class NonEmbedded4Configuration {

		@Autowired
		private DataSource4Properties properties;

		@Bean
		@ConfigurationProperties(prefix = DataSource4Properties.PREFIX)
		public DataSource dataSource4() {
			DataSourceBuilder factory = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName())
					.url(this.properties.getUrl()).username(this.properties.getUsername())
					.password(this.properties.getPassword());
			if (this.properties.getType() != null) {
				factory.type(this.properties.getType());
			}
			return factory.build();
		}
	}
	
	@Conditional(NonEmbeddedDataSourceCondition.class)
	@ConditionalOnProperty(prefix = DataSource5Properties.PREFIX, name = "url")
	protected static class NonEmbedded5Configuration {

		@Autowired
		private DataSource5Properties properties;

		@Bean
		@ConfigurationProperties(prefix = DataSource5Properties.PREFIX)
		public DataSource dataSource5() {
			DataSourceBuilder factory = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName())
					.url(this.properties.getUrl()).username(this.properties.getUsername())
					.password(this.properties.getPassword());
			if (this.properties.getType() != null) {
				factory.type(this.properties.getType());
			}
			return factory.build();
		}
	}
	
	@Conditional(NonEmbeddedDataSourceCondition.class)
	@ConditionalOnProperty(prefix = DataSource6Properties.PREFIX, name = "url")
	protected static class NonEmbedded6Configuration {

		@Autowired
		private DataSource6Properties properties;

		@Bean
		@ConfigurationProperties(prefix = DataSource6Properties.PREFIX)
		public DataSource dataSource6() {
			DataSourceBuilder factory = DataSourceBuilder
					.create(this.properties.getClassLoader())
					.driverClassName(this.properties.getDriverClassName())
					.url(this.properties.getUrl()).username(this.properties.getUsername())
					.password(this.properties.getPassword());
			if (this.properties.getType() != null) {
				factory.type(this.properties.getType());
			}
			return factory.build();
		}
	}
	
	
	
	/**
	 * {@link Condition} to test is a supported non-embedded {@link DataSource} type is
	 * available.
	 */
	static class NonEmbeddedDataSourceCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			if (getDataSourceClassLoader(context) != null) {
				return ConditionOutcome.match("supported DataSource class found");
			}
			return ConditionOutcome.noMatch("missing supported DataSource");
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
	 */
	static class EmbeddedDataSourceCondition extends SpringBootCondition {

		private final SpringBootCondition nonEmbedded = new NonEmbeddedDataSourceCondition();

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			if (anyMatches(context, metadata, this.nonEmbedded)) {
				return ConditionOutcome
						.noMatch("existing non-embedded database detected");
			}
			EmbeddedDatabaseType type = EmbeddedDatabaseConnection
					.get(context.getClassLoader()).getType();
			if (type == null) {
				return ConditionOutcome.noMatch("no embedded database detected");
			}
			return ConditionOutcome.match("embedded database " + type + " detected");
		}

	}
}
