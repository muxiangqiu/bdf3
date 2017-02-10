package com.bstek.bdf3.dbconsole.utils;

import java.util.HashMap;
import java.util.Map;

import com.bstek.bdf3.dbconsole.model.ColumnInfo;

public class ColumnTypeUtils {
	/**
	 * 数据库类型和dorado类型映射，
	 */
	private static final Map<String, String> columnTypeMap;
	static {
		columnTypeMap = new HashMap<String, String>();
		columnTypeMap.put("DATETIME", "Date");
		columnTypeMap.put("TIMESTAMP", "Date");
		columnTypeMap.put("DATE", "Date");
		columnTypeMap.put("TIME", "Date");

		columnTypeMap.put("BOOLEAN", "Boolean");
		columnTypeMap.put("BIT", "Boolean");
		columnTypeMap.put("DECIMAL", "BigDecimal");
		columnTypeMap.put("DOUBLE", "Double");
		columnTypeMap.put("FLOAT", "Float");

		columnTypeMap.put("TINYINT", "Integer");
		columnTypeMap.put("SMALLINT", "Integer");
		columnTypeMap.put("INT", "Integer");
		columnTypeMap.put("INTEGER", "Integer");
		columnTypeMap.put("MEDIUMINT", "Integer");
		columnTypeMap.put("BIGINT", "Integer");
		columnTypeMap.put("NUMBER", "Integer");
		

	}
	public static String getDroadoType(ColumnInfo info) {
		if (info != null) {
			//mysql
			if(info.getColumnType().toLowerCase().indexOf("tinyint")!=-1&&info.getColumnSize().equals("1")){
				return "Boolean";
			}
			if(info.getColumnType().toLowerCase().indexOf("unsigned")!=-1){
				return "Integer";
			}
			for (Map.Entry<String, String> entry : columnTypeMap.entrySet()) {
				if (info.getColumnType().toLowerCase().endsWith(entry.getKey().toLowerCase())) {
					return entry.getValue();
				}
			}
		}
		return null;
	}

}
