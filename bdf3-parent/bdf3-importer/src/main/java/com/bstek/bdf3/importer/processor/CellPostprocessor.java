package com.bstek.bdf3.importer.processor;

import com.bstek.bdf3.importer.policy.Context;

/**
 *@author Kevin.yang
 *@since 2015年8月30日
 */
public interface CellPostprocessor {
	

	void process(Context context);
	
	boolean support(Context context);
}
