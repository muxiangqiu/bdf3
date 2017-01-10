package com.bstek.bdf3.importer.converter.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.importer.converter.TypeConverter;

public class BigDecimalTypeConverter implements TypeConverter {

	@Override
	public Object fromText(Class<?> type, String text) {
		if (StringUtils.isBlank(text)) {
			return null;
		}
		return new BigDecimal(text.replace(",", ""));
	}

	@Override
	public boolean support(Class<?> clazz) {
		return BigDecimal.class.isAssignableFrom(clazz);
	}
}
