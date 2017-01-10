package com.bstek.bdf3.importer.parser.impl;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.jexl2.JexlContext;

import com.bstek.bdf3.importer.Constants;
import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.parser.CellPostParser;
import com.bstek.bdf3.importer.policy.Context;
import com.bstek.dorado.core.el.Expression;
import com.bstek.dorado.core.el.ExpressionHandler;

/**
 *@author Kevin.yang
 *@since 2015年8月30日
 */
public class ELCellPostParser implements CellPostParser {

	private ExpressionHandler expressionHandler;
	
	@Override
	public String getName() {
		return "EL表达式后置解析器";
	}

	@Override
	public void parse(Context context) {
		MappingRule mappingRule = context.getCurrentMappingRule();
		Object value = context.getValue();
		String cellPostParserParam = mappingRule.getCellPostParserParam();
		JexlContext jexlContext = expressionHandler.getJexlContext();
		jexlContext.set("context", context);
		jexlContext.set("value", value);
		Expression expression = expressionHandler.compile(cellPostParserParam);
		if (expression == null) {
			value = cellPostParserParam;
		} else {
			value = expression.evaluate();
		}
		context.setValue(value);
		BeanMap beanMap = BeanMap.create(context.getCurrentEntity());
		if (value != Constants.IGNORE_ERROR_FORMAT_DATA) {
			beanMap.put(mappingRule.getPropertyName(), value);
		}
	}

	public void setExpressionHandler(ExpressionHandler expressionHandler) {
		this.expressionHandler = expressionHandler;
	}

}
