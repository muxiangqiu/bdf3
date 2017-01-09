package com.bstek.bdf3.saas.service;

import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
public interface OrganizationService {

	Organization get(String id);
	
	void register(Organization organization);
	
	void remove(Organization organization);
}
