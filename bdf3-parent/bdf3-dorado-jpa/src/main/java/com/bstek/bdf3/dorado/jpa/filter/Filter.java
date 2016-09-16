package com.bstek.bdf3.dorado.jpa.filter;

import com.bstek.bdf3.dorado.jpa.policy.LinqContext;

/**
 *@author Kevin.yang
 *@since 2015年6月11日
 */
public interface Filter {
	boolean invoke(LinqContext linqContext);
}
