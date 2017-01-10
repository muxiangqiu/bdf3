package com.bstek.bdf3.importer.filter.impl;

import java.util.Map;

import javax.persistence.EntityManagerFactory;

import com.bstek.bdf3.importer.filter.EntityManagerFactoryFilter;

/**
 *@author Kevin.yang
 *@since 2015年8月23日
 */
public class EmptyEntityManagerFactoryFilter implements EntityManagerFactoryFilter {

	@Override
	public void filter(Map<String, EntityManagerFactory> SessionFactoryMap) {
		
	}

}
