package com.bstek.bdf3.saas.domain;

import com.bstek.bdf3.security.domain.OrganizationSupport;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月13日
 */
public class TempOrganizationSupport implements OrganizationSupport{

	private Organization organization;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOrganization() {
		return (T) organization;
	}

	@Override
	public <T> void setOrganization(T t) {
		organization = (Organization) t;
		
	}

}
