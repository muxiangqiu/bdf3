package com.bstek.bdf3.multitenant.ui.service;

import java.util.List;

import org.malagu.multitenant.domain.DataSourceInfo;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月3日
 */
public interface DataSourceInfoService {
	
	boolean isExist(String dataSourceInfoId);

	void save(List<DataSourceInfo> dataSourceInfos);

	List<DataSourceInfo> load();
}
