package com.bstek.bdf3.importer.processor.impl;

import com.bstek.bdf3.importer.policy.Context;
import com.bstek.bdf3.importer.processor.CellPostprocessor;

/**
 *@author Kevin.yang
 *@since 2015年9月2日
 */
public class EmptyCellPostprocessor implements CellPostprocessor {

	@Override
	public void process(Context context) {

	}

	@Override
	public boolean support(Context context) {
		return false;
	}

}
