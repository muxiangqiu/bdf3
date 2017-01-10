package com.bstek.bdf3.importer.policy;

import com.bstek.bdf3.importer.model.ImporterSolution;

/**
 *@author Kevin.yang
 *@since 2015年9月3日
 */
public interface AutoCreateMappingRulePolicy {
	void apply(ImporterSolution importerSolution);
}
