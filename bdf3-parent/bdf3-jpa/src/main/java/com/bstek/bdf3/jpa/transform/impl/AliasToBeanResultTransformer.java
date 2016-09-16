package com.bstek.bdf3.jpa.transform.impl;

import org.springframework.cglib.beans.BeanMap;

import com.bstek.bdf3.jpa.transform.ResultTransformer;

/**
 * 别名转Bean结果转换器
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月21日
 */
public class AliasToBeanResultTransformer implements ResultTransformer {

	private Class<?> resultClass;
	
	public AliasToBeanResultTransformer(Class<?> resultClass) {
		this.resultClass = resultClass;
	}
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		
		try {
			result = resultClass.newInstance();
			BeanMap beanMap = BeanMap.create(result);

			for ( int i = 0; i < aliases.length; i++ ) {
				String alias = aliases[i];
				if (alias != null) {
					beanMap.put(alias, tuple[i]);
				}
			}
		} catch ( InstantiationException e ) {
			throw new RuntimeException( "Could not instantiate resultclass: " + resultClass.getName() );
		} catch ( IllegalAccessException e ) {
			throw new RuntimeException( "Could not instantiate resultclass: " + resultClass.getName() );
		}
		return result;
	}

}
