package com.bstek.bdf3.dorado.jpa;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class CollectInfo {
	private Class<?> entityClass;
	private String[] properties;
	private List list;
	public Class<?> getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public String[] getProperties() {
		return properties;
	}
	public void setProperties(String[] properties) {
		this.properties = properties;
	}
	
	@SuppressWarnings("unchecked")
	public void add(Object obj) {
		if (list == null) {
			list = new ArrayList();
		}
		list.add(obj);
	}
	
	
	
	
	

}
