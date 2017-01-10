package com.bstek.bdf3.importer.converter.impl;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.importer.converter.TypeConverter;
@SuppressWarnings({"unchecked","rawtypes"})
public class EnumTypeConverter implements TypeConverter {

	@Override
	public Object fromText(Class type, String text) {
		if(StringUtils.isEmpty(text)){
			return null;
		}
		return Enum.valueOf(type, text);
	}

	@Override
	public boolean support(Class<?> clazz) {
		if(Enum.class.isAssignableFrom(clazz)){
			return true;
		}
		return false;
	}

}
