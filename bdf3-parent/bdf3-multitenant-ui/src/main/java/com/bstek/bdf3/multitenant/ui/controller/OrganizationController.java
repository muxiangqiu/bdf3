package com.bstek.bdf3.multitenant.ui.controller;



import java.util.List;

import org.malagu.multitenant.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.multitenant.ui.service.OrganizationService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
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
	
	@Expose
	public String isExist(String organizationId) {
		if (organizationService.isExist(organizationId)) {
			return "公司ID已经存在。";
		}
		return null;
	}
	

}
