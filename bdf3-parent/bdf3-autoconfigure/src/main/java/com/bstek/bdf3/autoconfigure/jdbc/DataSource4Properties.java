package com.bstek.bdf3.autoconfigure.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月10日
 */
@ConfigurationProperties(prefix = DataSource4Properties.PREFIX)
public class DataSource4Properties extends DataSourceProperties {
	
	public static final String PREFIX = "spring.datasource4";


}
