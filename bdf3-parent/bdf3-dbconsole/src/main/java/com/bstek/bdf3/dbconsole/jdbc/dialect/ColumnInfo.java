package com.bstek.bdf3.dbconsole.jdbc.dialect;

import java.util.List;


public class ColumnInfo {
	private String tableName;
	private String columnName;
	private String columnType;
	private String  columnSize;
	private String defaultValue;
	private boolean isnullAble;
	private boolean isprimaryKey;
	private List<String> listPrimaryKey;
	private boolean isautoincrement;
	private String remarks;
	private String pkName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public boolean isIsnullAble() {
		return isnullAble;
	}
	public void setIsnullAble(boolean isnullAble) {
		this.isnullAble = isnullAble;
	}
	public boolean isIsprimaryKey() {
		return isprimaryKey;
	}
	public void setIsprimaryKey(boolean isprimaryKey) {
		this.isprimaryKey = isprimaryKey;
	}
	public List<String> getListPrimaryKey() {
		return listPrimaryKey;
	}
	public void setListPrimaryKey(List<String> listPrimaryKey) {
		this.listPrimaryKey = listPrimaryKey;
	}
	public boolean isIsautoincrement() {
		return isautoincrement;
	}
	public void setIsautoincrement(boolean isautoincrement) {
		this.isautoincrement = isautoincrement;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

}
