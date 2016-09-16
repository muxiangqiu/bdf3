package com.bstek.bdf3.jpa.transform.impl;

import java.util.HashMap;
import java.util.Map;

import com.bstek.bdf3.jpa.transform.ResultTransformer;

/**
 * 别名转Map结果转换器
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月21日
 */
public class AliasToMapResultTransformer implements ResultTransformer {
	
	public static final AliasToMapResultTransformer INSTANCE = new AliasToMapResultTransformer();

	
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String, Object> result = new HashMap<String, Object>(aliases.length);;
		for ( int i = 0; i < aliases.length; i++ ) {
			String alias = aliases[i];
			if (alias != null) {
				result.put(alias, tuple[i]);
			}
			
		}
		return result;
	}

}
