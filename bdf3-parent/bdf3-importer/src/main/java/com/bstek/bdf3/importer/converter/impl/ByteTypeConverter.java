package com.bstek.bdf3.importer.converter.impl;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.importer.converter.TypeConverter;

public class ByteTypeConverter implements TypeConverter {

	@Override
	public Object fromText(Class<?> type, String text) {
		if (StringUtils.isBlank(text)) {
			if (Byte.class.isAssignableFrom(type)) {
				return null;
			} else {
				return 0;
			}
		}
		return Byte.parseByte(text);
	}

	@Override
	public boolean support(Class<?> clazz) {
		return Byte.class.isAssignableFrom(clazz)||byte.class.isAssignableFrom(clazz);
	}
}
