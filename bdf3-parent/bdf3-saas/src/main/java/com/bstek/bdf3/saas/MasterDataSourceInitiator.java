package com.bstek.bdf3.saas;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
	public void afterPropertiesSet(ApplicationContext applicationContext) {
		DataSourceInfo dataSourceInfo = new DataSourceInfo();
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
		EntityManager em = JpaUtil.createEntityManager(DataSourceInfo.class);
		try {
			em.getTransaction().begin();
			em.persist(dataSourceInfo);
			em.getTransaction().commit();
		} catch (Exception e) {
		
		} finally {
			em.close();
		}
		
	}

}
