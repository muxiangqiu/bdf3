package com.bstek.bdf3.dbconsole.jdbc.dialect;

import java.sql.Connection;
import java.util.List;

public interface IDialect {
	/**
	 * 判断数据库类型
	 * @param connection
	 * @return 返回是否支持当前连接
	 */
	boolean support(Connection connection);
	/**
	 * 修改表名称sql
	 * @param tableName 
	 * @param newTableName
	 * @return 返回修改表名的SQL
	 */
	String getTableRenameSql(String tableName,String newTableName);
	/**
	 * 默认创建表sql
	 * @param tableName
	 * @return 返回创建表的SQL
	 */
	String getCreateDefaultTableSql(String tableName);
	/**
	 * 添加列sql
	 * @param dbColumnInfo
	 * @return 返回创建新列的SQL
	 */
	String getNewColumnSql(ColumnInfo dbColumnInfo);
	/**
	 * 更新列sql
	 * @param oldDbColumnInfo
	 * @param newDbColumnInfo
	 * @return 返回用于更新列信息的SQL
	 */
	String getUpdateColumnSql(ColumnInfo oldDbColumnInfo,ColumnInfo newDbColumnInfo);
	
	/**
	 * 数据库分页sql
	 * @param sql
	 * @param pageNo
	 * @param pageSize
	 * @return 返回数据库分页SQL
	 */
	String getPaginationSql(String sql, int pageNo, int pageSize);
	
	/**
	 * 根据类型与长度生成一个创建column的SQL
	 * @param columnType
	 * @param columnSize
	 * @return SQL语句
	 */
	String generateColumnTypeSql(String columnType, String columnSize);
	
	/**
	 * 生成可以修改主键的SQL
	 * @param tableName
	 * @param primaryKeys
	 * @return 返回生成的SQL
	 */
	String generateAlertPrimaryKeySql(String tableName,List<String> primaryKeys);
}
