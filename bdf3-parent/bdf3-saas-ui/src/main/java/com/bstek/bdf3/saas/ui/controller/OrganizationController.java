package com.bstek.bdf3.saas.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.ui.service.OrganizationService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@DataProvider
	public void load(Page<Organization>page, Criteria criteria) {
		organizationService.load(page, criteria);
	}
	
	@DataResolver
	public void save(List<Organization> organizations) {
		organizationService.save(organizations);
	}
	

}
