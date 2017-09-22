package com.bstek.bdf3.saas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.jpa.initiator.JpaUtilAble;
import com.bstek.bdf3.saas.domain.DataSourceInfo;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月3日
 */
@Component
public class MasterDataSourceInitiator implements JpaUtilAble {

	@Autowired
	private DataSourceProperties properties;
	
	@Override
	@Transactional
	public void afterPropertiesSet(ApplicationContext applicationContext) {
		boolean isExist = true;
		DataSourceInfo dataSourceInfo = JpaUtil.getOne(DataSourceInfo.class, Constants.MASTER);
		if (dataSourceInfo == null) {
			dataSourceInfo = new DataSourceInfo();
			isExist = false;
		}
		dataSourceInfo.setId(Constants.MASTER);
		dataSourceInfo.setDriverClassName(properties.determineDriverClassName());
		dataSourceInfo.setEnabled(true);
		dataSourceInfo.setJndiName(properties.getJndiName());
		dataSourceInfo.setName("主公司数据源");
		dataSourceInfo.setUrl(properties.determineUrl());
		dataSourceInfo.setUsername(properties.determineUsername());
		dataSourceInfo.setPassword(properties.determinePassword());
		dataSourceInfo.setShared(true);
		dataSourceInfo.setDepletionIndex(1);
		dataSourceInfo.setType(properties.getType() != null ? properties.getType().getName() : null);
		if (isExist) {
			JpaUtil.merge(dataSourceInfo);
		} else {
			JpaUtil.persist(dataSourceInfo);
		}
		
	}

}
