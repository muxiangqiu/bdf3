package com.bstek.bdf3.importer.parser.impl;

import java.util.UUID;

import org.apache.commons.jexl2.JexlContext;

import com.bstek.bdf3.importer.model.MappingRule;
import com.bstek.bdf3.importer.parser.CellPreParser;
import com.bstek.bdf3.importer.policy.Context;
import com.bstek.dorado.core.el.Expression;
import com.bstek.dorado.core.el.ExpressionHandler;

/**
 *@author Kevin.yang
 *@since 2015年8月30日
 */
public class ELCellPreParser implements CellPreParser {

	private ExpressionHandler expressionHandler;
	
	@Override
	public String getName() {
		return "EL表达式前置解析器";
	}

	@Override
	public void parse(Context context) {
		MappingRule mappingRule = context.getCurrentMappingRule();
		Object value = context.getCurrentCell().getValue();
		String cellPreParserParam = mappingRule.getCellPreParserParam();
		JexlContext jexlContext = expressionHandler.getJexlContext();
		jexlContext.set("context", context);
		jexlContext.set("value", value);
		jexlContext.set("UUID", UUID.randomUUID().toString());
		Expression expression = expressionHandler.compile(cellPreParserParam);
		if (expression == null) {
			value = cellPreParserParam;
		} else {
			value = expression.evaluate();
		}
		context.setValue(value);;
	}

	public void setExpressionHandler(ExpressionHandler expressionHandler) {
		this.expressionHandler = expressionHandler;
	}

}
