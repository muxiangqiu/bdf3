package com.bstek.bdf3.jpa.lin;

import javax.persistence.criteria.CriteriaDelete;

/**
 * 语言集成删除
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月16日
 */
public interface Lind extends Lin<Lind, CriteriaDelete<?>>{

	/**
	 * 批量删除
	 * @return 删除条数
	 */
	int delete();

}
