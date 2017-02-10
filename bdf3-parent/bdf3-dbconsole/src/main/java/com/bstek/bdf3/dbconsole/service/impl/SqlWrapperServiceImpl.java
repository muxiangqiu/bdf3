package com.bstek.bdf3.dbconsole.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.bdf3.dbconsole.model.SqlWrapper;
import com.bstek.bdf3.dbconsole.service.ISqlWrapperService;

@Component(ISqlWrapperService.BEAN_ID)
public class SqlWrapperServiceImpl implements ISqlWrapperService {

	public SqlWrapper getInsertTableSql(String tableName, Map<String, Object> map) throws Exception {
		StringBuilder columnName = new StringBuilder();
		StringBuilder values = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		int i = 1;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			list.add(value);
			if (i == map.size()) {
				columnName.append(key);
				values.append("?");
			} else {
				columnName.append(key + ",");
				values.append("?,");
			}
			i++;
		}
		String sql = "insert into " + tableName + "(" + columnName + ")values(" + values + ")";
		return new SqlWrapper(sql, list.toArray());
	}

	public SqlWrapper getUpdateTableSql(String tableName, Map<String, Object> map, Map<String, Object> oldMap) throws Exception {
		StringBuilder newParameter = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		int i = 1;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			list.add(value);
			if (i == map.size()) {
				newParameter.append(key + "=?");
			} else {
				newParameter.append(key + "=?,");
			}
			i++;
		}
		String whereParameter = this.generateWhereSql(list, oldMap);
		String sql = "update " + tableName + " set " + newParameter + " where " + whereParameter;
		return new SqlWrapper(sql, list.toArray());

	}

	public SqlWrapper getDeleteTableSql(String tableName, Map<String, Object> oldMap) throws Exception {
		List<Object> list = new ArrayList<Object>();
		String whereParameter = this.generateWhereSql(list, oldMap);
		String sql = "delete from  " + tableName + " where " + whereParameter;
		return new SqlWrapper(sql, list.toArray());

	}

	private String generateWhereSql(List<Object> list, Map<String, Object> map) {
		StringBuilder sql = new StringBuilder(" 1=1 ");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value != null) {
				list.add(value);
				sql.append(" and " + key + "=? ");
			}
		}
		return sql.toString();
	}

}
