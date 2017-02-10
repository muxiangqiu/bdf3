package com.bstek.bdf3.dbconsole.model;

public class ColumnInfo {
	private String tableName;
	private String oldColumnName;
	private String columnName;
	private String columnType;
	private String columnSize;
	private String defaultValue;
	private boolean isnullAble;
	private boolean isprimaryKey;
	private boolean isautoincrement;

	public String getOldColumnName() {
		return oldColumnName;
	}

	public void setOldColumnName(String oldColumnName) {
		this.oldColumnName = oldColumnName;
	}

	public boolean isIsautoincrement() {
		return isautoincrement;
	}

	public void setIsautoincrement(boolean isautoincrement) {
		this.isautoincrement = isautoincrement;
	}

	public boolean isIsprimaryKey() {
		return isprimaryKey;
	}

	public void setIsprimaryKey(boolean isprimaryKey) {
		this.isprimaryKey = isprimaryKey;
	}

	public boolean isIsnullAble() {
		return isnullAble;
	}

	public void setIsnullAble(boolean isnullAble) {
		this.isnullAble = isnullAble;
	}

	private String remarks;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}

	public String getColumnSize() {
		return columnSize;
	}

}
