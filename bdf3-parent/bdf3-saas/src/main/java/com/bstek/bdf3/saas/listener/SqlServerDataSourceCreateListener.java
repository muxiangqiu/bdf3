package com.bstek.bdf3.saas.listener;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.saas.Constants;
import com.bstek.bdf3.saas.domain.DataSourceInfo;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.listener.DataSourceCreateListener;

@Component
@Order(1000)
public class SqlServerDataSourceCreateListener implements DataSourceCreateListener {

	@Override
	public void onCreate(Organization organization, DataSourceInfo dataSourceInfo,
			DataSourceBuilder dataSourceBuilder) {

		if ("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(dataSourceInfo.getDriverClassName())) {
			String url = dataSourceInfo.getUrl();
			if (!url.contains(Constants.MASTER)) {
				url += "/" + organization.getId();
				dataSourceBuilder.url(url);
			}
		}
	}

}
