package com.bstek.bdf3.importer.processor.impl;


import java.util.Collection;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.bdf3.importer.Constants;
import com.bstek.bdf3.importer.converter.TypeConverter;
import com.bstek.bdf3.importer.exception.DataFormatException;
import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.parser.CellPostParser;
import com.bstek.bdf3.importer.parser.CellPreParser;
import com.bstek.bdf3.importer.policy.Context;
import com.bstek.bdf3.importer.processor.CellProcessor;

/**
 *@author Kevin.yang
 *@since 2015年8月30日
 */
public class DefaultCellProcessor implements CellProcessor, ApplicationContextAware {

	private Collection<TypeConverter> typeConverters;
	
	private ApplicationContext applicationContext;
	
	
	private Log log = LogFactory.getLog(DefaultCellProcessor.class);
	
	@Override
	public void process(Context context) {
		cellPreParse(context);
		cellParse(context);
		cellPostParse(context);
	}
	
	protected void cellPreParse(Context context) {
		MappingRule mappingRule = context.getCurrentMappingRule();
		String cellPreParserBean = mappingRule.getCellPreParserBean();
		CellPreParser cellPreParser = (CellPreParser) applicationContext.getBean(cellPreParserBean);
		cellPreParser.parse(context);
	}
	
	protected void cellParse(Context context) {
		MappingRule mappingRule = context.getCurrentMappingRule();
		BeanMap beanMap = BeanMap.create(context.getCurrentEntity());
		String propertyName = mappingRule.getPropertyName();
		Class<?> type = beanMap.getPropertyType(propertyName);
		Object value = context.getValue();
		value = value == null ? null : value.toString();
		for (TypeConverter typeConverter : typeConverters) {
			if (typeConverter.support(type)) {
				try {
					value = typeConverter.fromText(type, mappingRule.getMappingValueIfNeed((String) value));
				} catch (Exception e) {
					if (mappingRule.isIgnoreErrorFormatData()) {
						log.debug(e.getMessage());
						value = Constants.IGNORE_ERROR_FORMAT_DATA;
					} else {
						throw new DataFormatException(context.getCurrentCell().getRow(), context.getCurrentCell().getCol(), (String) value);
					}
				}
				break;
			}
		}
		context.setValue(value);
	}
	
	protected void cellPostParse(Context context) {
		MappingRule mappingRule = context.getCurrentMappingRule();
		String cellPostParserBean = mappingRule.getCellPostParserBean();
		CellPostParser cellPostParser = (CellPostParser) applicationContext.getBean(cellPostParserBean);
		cellPostParser.parse(context);
	}

	@Override
	public boolean support(Context context) {
		return true;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		typeConverters = applicationContext.getBeansOfType(TypeConverter.class).values();
	}

}
