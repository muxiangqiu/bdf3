package com.bstek.bdf3.dorado.jpa.policy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *@author Kevin.yang
 *@since 2015年9月9日
 */
public class LinqContext {
	private Object entity;
	private Map<Object, Object> metadata = new HashMap<Object, Object>();
	
	@SuppressWarnings("unchecked")
	public <T> T getEntity() {
		return (T) entity;
	}
	
	public void setEntity(Object entity) {
		this.entity = entity;
	}
	
	public void put (Object key, Object value) {
		if (key != null) {
			metadata.put(key, value);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Object key) {
		return (T) metadata.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> entityClass, Object id) {
		Map<Object, Object> map = (Map<Object, Object>) metadata.get(entityClass);
		if (map == null) {
			return null;
		}
		return (T) map.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String property, Object id) {
		Map<Object, Object> map = (Map<Object, Object>) metadata.get(property);
		if (map == null) {
			return null;
		}
		return (T) map.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> get(String property) {
		Object value =  metadata.get(property);
		if (value instanceof List) {
			return (List<T>) value;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Set<T> getSet(String property) {
		Object value =  metadata.get(property);
		if (value instanceof List) {
			List<T> list = (List<T>) value;
			return new HashSet<T>(list);
		}
		return null;
	}
	
	public Map<Object, Object> getMetadata() {
		return metadata;
	}
	
	
	
	
}
