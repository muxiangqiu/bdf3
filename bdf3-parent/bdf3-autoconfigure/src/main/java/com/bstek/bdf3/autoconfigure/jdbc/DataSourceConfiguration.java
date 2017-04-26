/*
 * Copyright 2012-2017 the original author or authors.
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

package com.bstek.bdf3.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;


/**
 * Actual DataSource configurations imported by {@link DataSourceAutoConfiguration}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Stephane Nicoll
 */
abstract class DataSourceConfiguration {

	@SuppressWarnings("unchecked")
	protected <T> T createDataSource(DataSourceProperties properties,
			Class<? extends DataSource> type) {
		return (T) properties.initializeDataSourceBuilder().type(type).build();
	}
	
	protected org.apache.tomcat.jdbc.pool.DataSource createTomcatDataSource(DataSourceProperties properties) {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = createDataSource(
				properties, org.apache.tomcat.jdbc.pool.DataSource.class);
		DatabaseDriver databaseDriver = DatabaseDriver
				.fromJdbcUrl(properties.determineUrl());
		String validationQuery = databaseDriver.getValidationQuery();
		if (validationQuery != null) {
			dataSource.setTestOnBorrow(true);
			dataSource.setValidationQuery(validationQuery);
		}
		return dataSource; 
	}


	/**
	 * Tomcat Pool DataSource configuration.
	 */
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat extends DataSourceConfiguration {

		@Bean
		@Primary
		@ConfigurationProperties(prefix = "spring.datasource.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource(
				DataSourceProperties properties) {
			return createTomcatDataSource(properties);
		}

	}
	
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource1.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat1 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource1")
		public DataSourceProperties dataSource1Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource1.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource1() {
			return createTomcatDataSource(dataSource1Properties());
		}

	}
	
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource2.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat2 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource2")
		public DataSourceProperties dataSource2Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource2.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource2() {
			return createTomcatDataSource(dataSource2Properties());
		}

	}
	
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource3.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat3 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource3")
		public DataSourceProperties dataSource3Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource3.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource3() {
			return createTomcatDataSource(dataSource3Properties());
		}

	}
	
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource4.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat4 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource4")
		public DataSourceProperties dataSource4Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource4.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource4() {
			return createTomcatDataSource(dataSource4Properties());
		}

	}
	
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource5.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat5 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource5")
		public DataSourceProperties dataSource5Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource5.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource5() {
			return createTomcatDataSource(dataSource5Properties());
		}

	}
	
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring.datasource6.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat6 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource6")
		public DataSourceProperties dataSource6Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource6.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource6() {
			return createTomcatDataSource(dataSource6Properties());
		}

	}

	/**
	 * Hikari DataSource configuration.
	 */
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari extends DataSourceConfiguration {

		@Bean
		@Primary
		@ConfigurationProperties(prefix = "spring.datasource.hikari")
		public HikariDataSource dataSource(DataSourceProperties properties) {
			return createDataSource(properties, HikariDataSource.class);
		}

	}
	
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource1.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari1 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource1")
		public DataSourceProperties dataSource1Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource1.hikari")
		public HikariDataSource dataSource1() {
			return createDataSource(dataSource1Properties(), HikariDataSource.class);
		}

	}
	
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource2.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari2 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource2")
		public DataSourceProperties dataSource2Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource2.hikari")
		public HikariDataSource dataSource2() {
			return createDataSource(dataSource2Properties(), HikariDataSource.class);
		}

	}
	
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource3.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari3 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource3")
		public DataSourceProperties dataSource3Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource3.hikari")
		public HikariDataSource dataSource3() {
			return createDataSource(dataSource3Properties(), HikariDataSource.class);
		}

	}
	
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource4.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari4 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource4")
		public DataSourceProperties dataSource4Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource4.hikari")
		public HikariDataSource dataSource4() {
			return createDataSource(dataSource4Properties(), HikariDataSource.class);
		}

	}
	
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource5.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari5 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource5")
		public DataSourceProperties dataSource5Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource5.hikari")
		public HikariDataSource dataSource5() {
			return createDataSource(dataSource5Properties(), HikariDataSource.class);
		}

	}
	
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource6.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari6 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource6")
		public DataSourceProperties dataSource6Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource6.hikari")
		public HikariDataSource dataSource6() {
			return createDataSource(dataSource6Properties(), HikariDataSource.class);
		}

	}
	
	/**
	 * DBCP DataSource configuration.
	 */
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp2 extends DataSourceConfiguration {

		@Bean
		@Primary
		@ConfigurationProperties(prefix = "spring.datasource.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource(
				DataSourceProperties properties) {
			return createDataSource(properties,
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}
	
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource1.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp21 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource1")
		public DataSourceProperties dataSource1Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource1.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource1() {
			return createDataSource(dataSource1Properties(),
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}
	
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource2.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp22 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource2")
		public DataSourceProperties dataSource2Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource2.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource2() {
			return createDataSource(dataSource2Properties(),
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}
	
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource3.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp23 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource3")
		public DataSourceProperties dataSource3Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource3.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource3() {
			return createDataSource(dataSource3Properties(),
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}
	
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource4.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp24 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource4")
		public DataSourceProperties dataSource4Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource4.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource4() {
			return createDataSource(dataSource4Properties(),
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}
	
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource5.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp25 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource5")
		public DataSourceProperties dataSource5Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource5.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource5() {
			return createDataSource(dataSource5Properties(),
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}
	
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring.datasource6.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp26 extends DataSourceConfiguration {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource6")
		public DataSourceProperties dataSource6Properties() {
			return new DataSourceProperties();
		}

		@Bean
		@ConfigurationProperties(prefix = "spring.datasource6.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource6() {
			return createDataSource(dataSource6Properties(),
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}
	
	/**
	 * Generic DataSource configuration.
	 */
	@ConditionalOnMissingBean(name = DataSources.dataSource)
	@ConditionalOnProperty(name = "spring.datasource.type")
	@Primary
	static class Generic {

		@Bean
		@Primary
		public DataSource dataSource(DataSourceProperties properties) {
			return properties.initializeDataSourceBuilder().build();
		}

	}
	
	@ConditionalOnMissingBean(name = DataSources.dataSource1)
	@ConditionalOnProperty(name = "spring.datasource1.type")
	static class Generic1 {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource1")
		public DataSourceProperties dataSource1Properties() {
			return new DataSourceProperties();
		}

		@Bean
		public DataSource dataSource1() {
			return dataSource1Properties().initializeDataSourceBuilder().build();
		}

	}
	
	@ConditionalOnMissingBean(name = DataSources.dataSource2)
	@ConditionalOnProperty(name = "spring.datasource2.type")
	static class Generic2 {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource2")
		public DataSourceProperties dataSource2Properties() {
			return new DataSourceProperties();
		}

		@Bean
		public DataSource dataSource2() {
			return dataSource2Properties().initializeDataSourceBuilder().build();
		}

	}
	
	@ConditionalOnMissingBean(name = DataSources.dataSource3)
	@ConditionalOnProperty(name = "spring.datasource3.type")
	static class Generic3 {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource3")
		public DataSourceProperties dataSource3Properties() {
			return new DataSourceProperties();
		}

		@Bean
		public DataSource dataSource3() {
			return dataSource3Properties().initializeDataSourceBuilder().build();
		}

	}
	
	@ConditionalOnMissingBean(name = DataSources.dataSource4)
	@ConditionalOnProperty(name = "spring.datasource4.type")
	static class Generic4 {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource4")
		public DataSourceProperties dataSource4Properties() {
			return new DataSourceProperties();
		}

		@Bean
		public DataSource dataSource4() {
			return dataSource4Properties().initializeDataSourceBuilder().build();
		}

	}
	
	@ConditionalOnMissingBean(name = DataSources.dataSource5)
	@ConditionalOnProperty(name = "spring.datasource5.type")
	static class Generic5 {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource5")
		public DataSourceProperties dataSource5Properties() {
			return new DataSourceProperties();
		}

		@Bean
		public DataSource dataSource5() {
			return dataSource5Properties().initializeDataSourceBuilder().build();
		}

	}
	
	@ConditionalOnMissingBean(name = DataSources.dataSource6)
	@ConditionalOnProperty(name = "spring.datasource6.type")
	static class Generic6 {
		
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource6")
		public DataSourceProperties dataSource6Properties() {
			return new DataSourceProperties();
		}


		@Bean
		public DataSource dataSource6() {
			return dataSource6Properties().initializeDataSourceBuilder().build();
		}

	}
	
	
	
	
	
	

}
