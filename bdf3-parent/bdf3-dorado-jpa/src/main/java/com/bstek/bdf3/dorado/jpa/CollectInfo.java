package com.bstek.bdf3.dorado.jpa;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class CollectInfo {
	private Class<?> entityClass;
	private Class<?> relationClass;
	private String relationProperty;
	private String relationOtherProperty;
	private String otherProperty;
	private String[] properties;
	private Set set;
	public Class<?> getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
	public Class<?> getRelationClass() {
		return relationClass;
	}
	public void setRelationClass(Class<?> relationClass) {
		this.relationClass = relationClass;
	}
	public String getRelationProperty() {
		return relationProperty;
	}
	public void setRelationProperty(String relationProperty) {
		this.relationProperty = relationProperty;
	}
	public String getRelationOtherProperty() {
		return relationOtherProperty;
	}
	public void setRelationOtherProperty(String relationOtherProperty) {
		this.relationOtherProperty = relationOtherProperty;
	}
	public String[] getProperties() {
		return properties;
	}
	public void setProperties(String[] properties) {
		this.properties = properties;
	}
	
	@SuppressWarnings("unchecked")
	public void add(Object obj) {
		if (set == null) {
			set = new HashSet();
		}
		set.add(obj);
	}
	public String getOtherProperty() {
		return otherProperty;
	}
	public void setOtherProperty(String otherProperty) {
		this.otherProperty = otherProperty;
	}
	
	
	
	
	

}
