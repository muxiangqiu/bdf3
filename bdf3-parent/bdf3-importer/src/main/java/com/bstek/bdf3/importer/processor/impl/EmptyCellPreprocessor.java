package com.bstek.bdf3.importer.processor.impl;

import com.bstek.bdf3.importer.policy.Context;
import com.bstek.bdf3.importer.processor.CellPreprocessor;

/**
 *@author Kevin.yang
 *@since 2015年9月2日
 */
public class EmptyCellPreprocessor implements CellPreprocessor {

	@Override
	public void process(Context context) {

	}

	@Override
	public boolean support(Context context) {
		return false;
	}

}
