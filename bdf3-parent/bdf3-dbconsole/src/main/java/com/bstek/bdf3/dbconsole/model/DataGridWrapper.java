package com.bstek.bdf3.dbconsole.model;

import java.util.List;
import java.util.Map;

public class DataGridWrapper {
	private List<ColumnInfo> columnNames;
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	private int totalCount;

	public List<ColumnInfo> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<ColumnInfo> columnNames) {
		this.columnNames = columnNames;
	}

	private List<Map<String, Object>> tableData;

	public List<Map<String, Object>> getTableData() {
		return tableData;
	}

	public void setTableData(List<Map<String, Object>> tableData) {
		this.tableData = tableData;
	}

}
