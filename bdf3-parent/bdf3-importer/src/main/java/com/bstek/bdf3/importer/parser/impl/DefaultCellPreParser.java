package com.bstek.bdf3.importer.parser.impl;

import com.bstek.bdf3.importer.parser.CellPreParser;
import com.bstek.bdf3.importer.policy.Context;

/**
 *@author Kevin.yang
 *@since 2015年8月30日
 */
public class DefaultCellPreParser implements CellPreParser {

	@Override
	public String getName() {
		return "默认前置解析器";
	}

	@Override
	public void parse(Context context) {
		context.setValue(context.getCurrentCell().getValue());
	}

}
