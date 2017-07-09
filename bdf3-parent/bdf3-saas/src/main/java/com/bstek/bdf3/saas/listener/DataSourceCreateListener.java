package com.bstek.bdf3.saas.listener;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

import com.bstek.bdf3.saas.domain.DataSourceInfo;
import com.bstek.bdf3.saas.domain.Organization;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月4日
 */
public interface DataSourceCreateListener {

	void onCreate(Organization organization, DataSourceInfo dataSourceInfo, DataSourceBuilder dataSourceBuilder);
}
