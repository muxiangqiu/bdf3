package com.bstek.bdf3.saas.resource;

import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
public interface ResourceAllocator {

	void allocate(Organization organization);
}
