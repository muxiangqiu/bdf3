package com.bstek.bdf3.saas.resource;
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月2日
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.saas.domain.DataSourceInfo;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.DataSourceInfoService;

@Component
@Order(500)
public class DataSourceResourceAllocator implements ResourceAllocator {
	
	@Autowired
	private DataSourceInfoService dataSourceInfoService;

	@Override
	public void allocate(Organization organization) {
		DataSourceInfo dataSourceInfo = dataSourceInfoService.allocate(organization);
		organization.setDataSourceInfoId(dataSourceInfo.getId());
		dataSourceInfo.setDepletionIndex(dataSourceInfo.getDepletionIndex() + 1);
	}

}
