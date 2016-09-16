package com.bstek.bdf3.autoconfigure.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月10日
 */
@ConfigurationProperties(prefix = DataSource3Properties.PREFIX)
public class DataSource3Properties extends DataSourceProperties {
	
	public static final String PREFIX = "spring.datasource3";


}
