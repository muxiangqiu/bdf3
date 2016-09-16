package com.bstek.bdf3.dorado.jpa;

import org.hibernate.Query;

/**
 *@author Kevin.yang
 *@since 2015年6月12日
 */
public interface QueryCallback<T extends Query> {
	void initQuery(T query);
}
