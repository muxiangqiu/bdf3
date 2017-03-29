package com.bstek.bdf3.dorado.jpa.policy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

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
	
	public <T> T get(Class<T> entityClass, Object id) {
		List<T> list = getList(entityClass, id);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(Class<T> entityClass, Object value) {
		Map<Object, List<T>> map = (Map<Object, List<T>>) metadata.get(entityClass);
		if (map == null) {
			return null;
		}
		return map.get(value);
	}
	
	public <T> T get(String property, Object id) {
		List<T> list = getList(property, id);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String property, Object value) {
		Map<Object, List<T>> map = (Map<Object, List<T>>) metadata.get(property);
		if (map == null) {
			return null;
		}
		return map.get(value);
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
