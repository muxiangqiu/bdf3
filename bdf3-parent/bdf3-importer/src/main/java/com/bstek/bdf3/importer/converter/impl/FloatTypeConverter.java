package com.bstek.bdf3.importer.converter.impl;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.importer.converter.TypeConverter;

public class FloatTypeConverter implements TypeConverter {

	@Override
	public Object fromText(Class<?> type, String text) {
		if (StringUtils.isBlank(text)) {
			if (Float.class.isAssignableFrom(type)) {
				return null;
			} else {
				return 0f;
			}
		}
		return Float.parseFloat(text);
	}

	@Override
	public boolean support(Class<?> clazz) {
		return Float.class.isAssignableFrom(clazz)||float.class.isAssignableFrom(clazz);
	}
}
