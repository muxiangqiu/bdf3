package com.bstek.bdf3.dbconsole.model;


public class TableInfo {
	private DbInfo dbInfo;
	private String dbInfoId;
	
	public String getDbInfoId() {
		return dbInfoId;
	}
	public void setDbInfoId(String dbInfoId) {
		this.dbInfoId = dbInfoId;
	}
	public DbInfo getDbInfo() {
		return dbInfo;
	}
	public void setDbInfo(DbInfo dbInfo) {
		this.dbInfo = dbInfo;
	}
	private String tableName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
