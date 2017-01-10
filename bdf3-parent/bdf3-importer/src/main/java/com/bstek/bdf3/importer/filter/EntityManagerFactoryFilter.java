package com.bstek.bdf3.importer.filter;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

/**
 *@author Kevin.yang
 *@since 2015年8月23日
 */
public interface EntityManagerFactoryFilter {

	void filter(Map<String, EntityManagerFactory> entityManagerFactory);
}
