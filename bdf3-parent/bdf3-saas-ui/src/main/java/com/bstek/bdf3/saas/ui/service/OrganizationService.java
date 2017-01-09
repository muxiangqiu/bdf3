package com.bstek.bdf3.saas.ui.service;

import java.util.List;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月9日
 */
public interface OrganizationService {
	

	void load(Page<Organization> page, Criteria criteria);
	
	void save(List<Organization> organizations);
	
	boolean isExist(String organizationId);
}
