package com.bstek.bdf3.dorado.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import com.bstek.dorado.util.proxy.ProxyBeanUtils;

/**
 * 范型工具类。
 * 
 *@author Kevin.yang
 *@since 2015年5月16日
 */
public final class GenricTypeUtils {
	
	public static Class<?> getGenricType(Field field){
		Type type=field.getGenericType();
		if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType = ((ParameterizedType)type);
			Type[] types = parameterizedType.getActualTypeArguments();
			if (types != null && types.length >0 && types[0] instanceof Class)
			return (Class<?>)types[0];
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class<?> getGenricType(Object obj){
		if(obj instanceof Collection) {
			Collection c = (Collection) obj;
			if (c != null && c.size() > 0) {
				Object entity = c.iterator().next();
				if (entity != null) {
					return ProxyBeanUtils.getProxyTargetType(entity);
				}
			}
		} else if (obj != null) {
			return ProxyBeanUtils.getProxyTargetType(obj);
		}
		return null;
	}
}
