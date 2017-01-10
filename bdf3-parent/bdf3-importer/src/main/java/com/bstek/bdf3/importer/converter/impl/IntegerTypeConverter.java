package com.bstek.bdf3.importer.converter.impl;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.importer.converter.TypeConverter;


public class IntegerTypeConverter implements TypeConverter {

	@Override
	public Object fromText(Class<?> type, String text) {
		if (StringUtils.isBlank(text)) {
			if (Integer.class.isAssignableFrom(type)) {
				return null;
			} else {
				return 0;
			}
		}
		return Integer.parseInt(text);
	}

	@Override
	public boolean support(Class<?> clazz) {
		return int.class.isAssignableFrom(clazz)||Integer.class.isAssignableFrom(clazz);
	}
	
}
