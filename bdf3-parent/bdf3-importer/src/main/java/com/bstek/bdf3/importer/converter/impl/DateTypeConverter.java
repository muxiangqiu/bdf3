package com.bstek.bdf3.importer.converter.impl;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.util.Assert;

import com.bstek.bdf3.importer.Constants;
import com.bstek.bdf3.importer.converter.TypeConverter;
import com.bstek.dorado.core.Configure;

public class DateTypeConverter implements TypeConverter {
	
	private String[] dataFarmats;

	@Override
	public Object fromText(Class<?> type, String text) {
		if (StringUtils.isBlank(text)) {
			return null;
		}
		try {
			return DateUtils.parseDate(text, getDateFarmats());
		} catch (ParseException e) {
			try {
				return DateUtil.getJavaDate(Double.valueOf(text));
			} catch (Exception e2) {
				throw new RuntimeException("［" + text + "］无法转换为日期类型。");
			}
		}
	}
	
	private String[] getDateFarmats() {
		if (dataFarmats == null) {
			String value =Configure.getString(Constants.DATE_FORMATS_PROP);
			Assert.hasLength(value, Constants.DATE_FORMATS_PROP + "can not be empty.");
			dataFarmats = value.split("\\s?,\\s?");
		}
		return dataFarmats;
	}

	@Override
	public boolean support(Class<?> clazz) {
		return Date.class.isAssignableFrom(clazz);
	}
}
