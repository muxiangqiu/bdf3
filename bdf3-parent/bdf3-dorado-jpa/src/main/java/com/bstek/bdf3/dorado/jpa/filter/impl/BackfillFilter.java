package com.bstek.bdf3.dorado.jpa.filter.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.bstek.bdf3.dorado.jpa.CollectInfo;
import com.bstek.bdf3.dorado.jpa.filter.Filter;
import com.bstek.bdf3.dorado.jpa.policy.LinqContext;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.util.proxy.ProxyBeanUtils;

import net.sf.cglib.beans.BeanMap;

public class BackfillFilter implements Filter {

	private List<CollectInfo> collectInfos;
	
	private Map<Class<?>, List<PropertyDescriptor>> propertyMap = new HashMap<Class<?>, List<PropertyDescriptor>>();

	private Filter filter;

	public BackfillFilter(Filter filter, List<CollectInfo> collectInfos) {
		this.collectInfos = collectInfos;
		this.filter = filter;
	}
	
	@Override
	public boolean invoke(LinqContext linqContext) {
		Object entity = linqContext.getEntity();
		for (CollectInfo collectInfo : collectInfos) {
			Class<?> cls = collectInfo.getEntityClass();
			
			if (cls != null) {
				List<PropertyDescriptor> pds = getPropertyMap(entity).get(cls);
				String[] properties = collectInfo.getProperties();
				if (CollectionUtils.isEmpty(pds)) {
					Object value = linqContext.get(cls, BeanMap.create(entity).get(properties[0]));
					if (value != null) {
						EntityUtils.setValue(entity, Introspector.decapitalize(cls.getSimpleName()), value);
					}
				} else if (pds.size() == 1) {
					doFill(linqContext, entity, cls, properties[0], pds.get(0));
				} else {
					for (String property : properties) {
						for (PropertyDescriptor pd : pds) {
							if (StringUtils.contains(property, pd.getName()) || StringUtils.contains(pd.getName(), property)) {
								doFill(linqContext, entity, cls, property, pd);
							}
						}
					}
				}
				
				
			}
			
		}
		
		return filter == null ? true : filter.invoke(linqContext);
	}

	private void doFill(LinqContext linqContext, Object entity, Class<?> cls,
			String property, PropertyDescriptor pd) {
		Object value;
		if (Collection.class.isAssignableFrom(pd.getPropertyType())) {
			value = linqContext.getList(cls, BeanMap.create(entity).get(property));
		} else {
			value = linqContext.get(cls, BeanMap.create(entity).get(property));
		}
		if (value != null) {
			try {
				pd.getWriteMethod().invoke(entity, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Map<Class<?>,List<PropertyDescriptor>> getPropertyMap(Object entity) {
		if (propertyMap.isEmpty()) {
			Class<?> entityClass = ProxyBeanUtils.getProxyTargetType(entity);
			PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(entityClass);
			if (ArrayUtils.isNotEmpty(pds)) {
				for (PropertyDescriptor pd : pds) {
					for (CollectInfo collectInfo : collectInfos) {
						Class<?> cls = collectInfo.getEntityClass();
						if (cls == null) {
							continue;
						}
						if (pd.getPropertyType().isAssignableFrom(cls)) {
							addPd2PropertyMap(cls, pd);
						} else if (Collection.class.isAssignableFrom(pd.getPropertyType())) {
							Type[] pts = ((ParameterizedType)pd.getReadMethod().getGenericReturnType()).getActualTypeArguments();
							if (pts != null && pts.length > 0) {
								Type pt = pts[0];
								if (pt instanceof Class && ((Class<?>) pt).isAssignableFrom(cls)) {
									addPd2PropertyMap(cls, pd);
								}
							}
						}
					}
				}
			}
		}
		
		return propertyMap;
	}

	private void addPd2PropertyMap(Class<?> cls, PropertyDescriptor pd) {
		List<PropertyDescriptor> list = propertyMap.get(cls);
		if (list == null) {
			list = new ArrayList<PropertyDescriptor>(4);
			propertyMap.put(cls, list);
		}
		list.add(pd);
	}

}
