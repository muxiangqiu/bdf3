package com.bstek.bdf3.log.context.provider;

import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import com.bstek.bdf3.log.context.ContextHandler;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
public abstract class AbstractContextProvider implements ContextProvider{
	
	protected ContextHandler contextHandler;
	
	protected Object getRealContext(Object obj, String dataPath) {
		if (StringUtils.isEmpty(dataPath)) {
			return obj;
		}
		String[] path = dataPath.split("\\.");
		Object target = obj;
		BeanMap beanMap = BeanMap.create(target);
		for (String property : path) {
			target = beanMap.get(property);
		}
		return target;
	}
	
	public void setContextHandler(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}
}
