package com.bstek.bdf3.importer.parser.impl;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Lob;

import net.sf.cglib.beans.BeanMap;

import org.springframework.util.ReflectionUtils;

import com.bstek.bdf3.importer.Constants;
import com.bstek.bdf3.importer.exception.DataLengthException;
import com.bstek.bdf3.importer.exception.DataNullableException;
import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.parser.CellPostParser;
import com.bstek.bdf3.importer.policy.Context;

/**
 *@author Kevin.yang
 *@since 2015年8月30日
 */
public abstract class AbstractCellPostParser implements CellPostParser {


	@Override
	public void parse(Context context) {
		if (context.getValue() != null) {
			MappingRule mappingRule =context.getCurrentMappingRule();
			BeanMap beanMap = BeanMap.create(context.getCurrentEntity());
			if (context.getValue() != Constants.IGNORE_ERROR_FORMAT_DATA) {
				Field field = ReflectionUtils.findField(context.getEntityClass(), mappingRule.getPropertyName());
				Column column = field.getAnnotation(Column.class);
				if (column.nullable()) {
					if (context.getValue() == null) {
						throw new DataNullableException(context.getCurrentCell().getRow(), context.getCurrentCell().getCol());
					}
				}
				
				if (field.getType() == String.class && !field.isAnnotationPresent(Lob.class)) {
					String value = (String) context.getValue();
					if (value.getBytes().length > column.length()) {
						throw new DataLengthException(context.getCurrentCell().getRow(), context.getCurrentCell().getCol(), value, column.length());
					}
				}
				beanMap.put(mappingRule.getPropertyName(), context.getValue());
			}
		}
	}

}
