package com.bstek.bdf3.saas.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.saas.Constants;
import com.bstek.bdf3.saas.domain.DataSourceInfo;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.DatabaseNameService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月4日
 */
@Component
@Order(1000)
public class MysqlDataSourceCreateListener implements DataSourceCreateListener {

	@Autowired
	private DatabaseNameService databaseNameService;
	
	@Override
	public void onCreate(Organization organization, DataSourceInfo dataSourceInfo,
			DataSourceBuilder dataSourceBuilder) {
		if ("com.mysql.jdbc.Driver".equals(dataSourceInfo.getDriverClassName())) {
			String url = dataSourceInfo.getUrl();
			if (!url.contains(databaseNameService.getDatabaseName(Constants.MASTER))) {
				String databaseName = databaseNameService.getDatabaseName(Constants.MASTER);
				url += "/" + databaseName;
				dataSourceBuilder.url(url);
			}
		}
		
	}

	

	

}
