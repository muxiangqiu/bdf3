package com.bstek.bdf3.saas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.saas.Constants;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年11月6日
 */
@Service
public class DatabaseNameServiceImpl implements DatabaseNameService {

	@Value("${bdf3.saas.databaseNamePrefix:}")
	private String databaseNamePrefix;
	
	@Value("${bdf3.saas.databaseNameSuffix:}")
	private String databaseNameSuffix;
	
	@Value("${bdf3.saas.masterDatabaseName:}")
	private String masterDatabaseName;
	
	@Override
	public String getDatabaseName(String organizationId) {
		if (Constants.MASTER.equals(organizationId) && !StringUtils.isEmpty(masterDatabaseName)) {
			return masterDatabaseName;
		}
		String databaseName = organizationId;
		if (!StringUtils.isEmpty(databaseNamePrefix)) {
			databaseName = databaseNamePrefix + databaseName;
		}
		if (!StringUtils.isEmpty(databaseNameSuffix)) {
			databaseName += databaseNameSuffix;
		}
		return databaseName;
	}

}
