package com.bstek.bdf3.saas.service;

import javax.sql.DataSource;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月4日
 */
public interface ScriptService {

	void runScripts(DataSource dataSource, String locations, String fallback);

	void runScripts(String organizationId, DataSource dataSource, String locations, String fallback);

}
