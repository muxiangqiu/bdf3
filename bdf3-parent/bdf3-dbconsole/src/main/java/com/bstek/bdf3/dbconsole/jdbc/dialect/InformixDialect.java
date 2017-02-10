package com.bstek.bdf3.dbconsole.jdbc.dialect;

import java.sql.Connection;

import org.springframework.stereotype.Component;

@Component
public class InformixDialect extends AbstractDialect {

	public boolean support(Connection connection) {
		return support(connection, "Informix Dynamic Server", null);
	}

	public String getTableRenameSql(String tableName, String newTableName) {
		return "rename table " + tableName + " to " + newTableName;
	}

	public String getNewColumnSql(ColumnInfo dbColumnInfo) {
		throw new RuntimeException("暂不支持");
	}

	public String getUpdateColumnSql(ColumnInfo oldDbColumnInfo,
			ColumnInfo newDbColumnInfo) {
		throw new RuntimeException("暂不支持");
	}

	public String getPaginationSql(String sql, int pageNo, int pageSize) {
		if (pageNo<1) {
			throw new RuntimeException("page no 不能小于1");			
		}
		int startNo = (pageNo - 1) * pageSize;
		sql = sql.trim().toLowerCase();
		final String select = "select";
		int indexOfSelect = sql.indexOf(select);
		if (indexOfSelect != -1) {
			indexOfSelect += select.length();
			return sql.substring(0, indexOfSelect + 1) + " skip " + startNo
					+ " first " + pageSize + sql.substring(indexOfSelect);
		}
		throw new RuntimeException("未找到select");
	}

	public String getLimitString(String querySelect, int offset, int limit) {
		if (offset > 0) {
			throw new UnsupportedOperationException(
					"query result offset is not supported");
		}
		return new StringBuffer(querySelect.length() + 8)
				.append(querySelect)
				.insert(querySelect.toLowerCase().indexOf("select") + 6,
						" first " + limit).toString();
	}

}
