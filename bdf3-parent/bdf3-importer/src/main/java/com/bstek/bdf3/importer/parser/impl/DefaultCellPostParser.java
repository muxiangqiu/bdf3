package com.bstek.bdf3.importer.parser.impl;

import net.sf.cglib.beans.BeanMap;

import com.bstek.bdf3.importer.Constants;
import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.parser.CellPostParser;
import com.bstek.bdf3.importer.policy.Context;

/**
 *@author Kevin.yang
 *@since 2015年8月30日
 */
public class DefaultCellPostParser implements CellPostParser {

	@Override
	public String getName() {
		return "默认后置解析器";
	}

	@Override
	public void parse(Context context) {
		if (context.getValue() != null) {
			MappingRule mappingRule =context.getCurrentMappingRule();
			BeanMap beanMap = BeanMap.create(context.getCurrentEntity());
			if (context.getValue() != Constants.IGNORE_ERROR_FORMAT_DATA) {
				beanMap.put(mappingRule.getPropertyName(), context.getValue());
			}
		}
	}

}
