package com.bstek.bdf3.autoconfigure.jpa;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月8日
 */
@ConfigurationProperties(prefix = Jpa1Properties.PREFIX)
public class Jpa1Properties extends JpaBaseProperties {
	public static final String PREFIX = "spring.jpa1";
	
	
}
