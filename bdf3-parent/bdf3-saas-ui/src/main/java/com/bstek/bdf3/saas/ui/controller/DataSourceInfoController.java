package com.bstek.bdf3.saas.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.saas.domain.DataSourceInfo;
import com.bstek.bdf3.saas.ui.service.DataSourceInfoService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class DataSourceInfoController {
	
	@Autowired
	private DataSourceInfoService dataSourceInfoService;
	
	@DataProvider
	public List<DataSourceInfo> load() {
		return dataSourceInfoService.load();
	}
	
	@DataResolver
	public void save(List<DataSourceInfo> organizations) {
		dataSourceInfoService.save(organizations);
	}
	
	@Expose
	public String isExist(String dataSourceInfoId) {
		if (dataSourceInfoService.isExist(dataSourceInfoId)) {
			return "数据源ID已经存在。";
		}
		return null;
	}
	

}
