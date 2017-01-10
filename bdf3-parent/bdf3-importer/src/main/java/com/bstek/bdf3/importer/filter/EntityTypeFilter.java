package com.bstek.bdf3.importer.filter;

import java.util.List;

import javax.persistence.metamodel.EntityType;

/**
 *@author Kevin.yang
 *@since 2015年8月23日
 */
public interface EntityTypeFilter {

	void filter(List<EntityType<?>> entityTypes);
}
