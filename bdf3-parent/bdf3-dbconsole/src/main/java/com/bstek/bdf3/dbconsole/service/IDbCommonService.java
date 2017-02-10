package com.bstek.bdf3.dbconsole.service;

import java.util.List;

import javax.sql.DataSource;

import com.bstek.bdf3.dbconsole.jdbc.dialect.IDialect;
import com.bstek.bdf3.dbconsole.model.ColumnInfo;
import com.bstek.bdf3.dbconsole.model.DataGridWrapper;
import com.bstek.bdf3.dbconsole.model.TableInfo;


public interface IDbCommonService {

	public static final String BEAN_ID = "bdf3.dbconsole.dbCommonService";

	/**
	 * 查询所有的表信息
	 * 
	 * @param dbInfoId
	 * @return 返回TableInfo的集合
	 * @throws Exception
	 */
	public List<TableInfo> findTableInfos(String dbInfoId) throws Exception;

	/**
	 * 查询表的所有列信息
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @return 返回ColumnInfo的集合
	 * @throws Exception
	 */
	public List<ColumnInfo> findColumnInfos(String dbInfoId, String tableName) throws Exception;

	/**
	 * 返回sql语句查询的数据列信息
	 * 
	 * @param dbInfoId
	 * @param sql
	 * @return 返回ColumnInfo的集合
	 * @throws Exception
	 */
	public List<ColumnInfo> findMultiColumnInfos(String dbInfoId, String sql) throws Exception;;

	/**
	 * 查询表的所有主键
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @return 返回表所有主键的集合
	 * @throws Exception
	 */
	public List<String> findTablePrimaryKeys(String dbInfoId, String tableName) throws Exception;

	/**
	 * 查询某张表的所有列和数据
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @param sql
	 * @param pageSize
	 * @param pageNo
	 * @return 返回表的所有列和数据
	 * @throws Exception
	 */
	public DataGridWrapper queryTableData(String dbInfoId, String tableName, String sql, int pageSize, int pageNo) throws Exception;

	/**
	 * 创建数据库连接池
	 * 
	 * @param url
	 * @param driverClassName
	 * @param username
	 * @param password
	 * @return 返回创建的数据库连接池对象
	 */
	public DataSource createDataSource(String url, String driverClassName, String username, String password);

	/**
	 * 根据数据库id获取对应的datasource
	 * 
	 * @param dbInfoId
	 * @return 返回datasource对象
	 * @throws Exception
	 */
	public DataSource getDataSourceByDbInfoId(String dbInfoId) throws Exception;


	/**
	 * 获取数据库对应的方言
	 * 
	 * @param dbInfoId
	 * @return
	 * @throws Exception
	 */
	public IDialect getDBDialectByDbInfoId(String dbInfoId) throws Exception;

	/**
	 * 测试连接是否成功
	 * 
	 * @param driverClassName
	 * @param url
	 * @param username
	 * @param password
	 * @return 若成功返回null，否则返回相应的错误提示信息
	 */
	public String checkDbConnection(String driverClassName, String url, String username, String password);

	/**
	 * 查找默认配置的数据库类型
	 * 
	 * @param dbInfoId
	 * @return 返回默认配置的数据库类型的集合
	 * @throws Exception
	 */
	public List<String> findDefaultColumnType(String dbInfoId) throws Exception;
}
