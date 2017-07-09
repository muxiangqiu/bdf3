package com.bstek.bdf3.saas.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.DataSourceService;
import com.bstek.bdf3.saas.service.ScriptService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Component
@Order(1500)
public class InitializedDataResourceAllocator implements ResourceAllocator {
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Value("${bdf3.saas.resourceScript:}")
	private String resourceScript;
	
	@Autowired
	private ScriptService scriptService;
	
	

	@Override
	public void allocate(Organization organization) {
		scriptService.runScripts(organization.getId(), dataSourceService.getDataSource(organization), resourceScript, "saas");
	}
	
	
	
}
