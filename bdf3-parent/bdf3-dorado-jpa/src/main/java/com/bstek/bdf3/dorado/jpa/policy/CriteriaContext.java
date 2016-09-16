package com.bstek.bdf3.dorado.jpa.policy;

import com.bstek.dorado.data.provider.Criteria;

/**
 *@author Kevin.yang
 *@since 2015年5月23日
 */
public interface CriteriaContext {
	Criteria getCriteria();
	<E> void setCurrent(E e);
	<E> E getCurrent();
}
