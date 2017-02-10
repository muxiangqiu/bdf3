package com.bstek.bdf3.dbconsole.service;

import java.util.Map;

import com.bstek.bdf3.dbconsole.model.SqlWrapper;

public interface ISqlWrapperService {
	public static final String BEAN_ID = "bdf3.dbconsole.sqlWrapperService";

	/**
	 * 添加数据浏览器记录sql
	 * 
	 * @param tableName
	 * @param map
	 * @throws Exception
	 */
	public SqlWrapper getInsertTableSql(String tableName, Map<String, Object> map) throws Exception;

	/**
	 * 更新数据浏览器记录sql
	 * 
	 * @param tableName
	 * @param map
	 * @param oldMap
	 * @throws Exception
	 */
	public SqlWrapper getUpdateTableSql(String tableName, Map<String, Object> map, Map<String, Object> oldMap) throws Exception;

	/**
	 * 删除数据浏览器记录sql
	 * 
	 * @param tableName
	 * @param oldMap
	 * @throws Exception
	 */
	public SqlWrapper getDeleteTableSql(String tableName, Map<String, Object> oldMap) throws Exception;
}
