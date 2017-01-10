package com.bstek.bdf3.importer.converter.impl;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.importer.converter.TypeConverter;

public class BooleanTypeConverter implements TypeConverter {

	@Override
	public Object fromText(Class<?> type, String text) {
		if (StringUtils.isBlank(text)) {
			return false;
		}
		return Boolean.parseBoolean(text);
	}

	@Override
	public boolean support(Class<?> clazz) {
		return Boolean.class.isAssignableFrom(clazz)||boolean.class.isAssignableFrom(clazz);
	}
	
}
