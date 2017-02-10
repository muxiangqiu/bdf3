
package com.bstek.bdf3.dbconsole.jdbc.dialect;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SqlLiteDialect extends AbstractDialect{


	public boolean support(Connection connection) {
		return support(connection, "SQLite", null);
	}

	public String getPaginationSql(String sql, int pageNo, int pageSize) {
		int startNo = (pageNo - 1) * pageSize;
		return sql + " limit " + startNo + "," + pageSize;
	}


	public String getTableRenameSql(String tableName, String newTableName) {
		return "rename table "+tableName+" to "+newTableName;
	}

	public String getNewColumnSql(ColumnInfo dbColumnInfo) {
		String tableName=dbColumnInfo.getTableName();
		String columnName=dbColumnInfo.getColumnName();
		String columnType=dbColumnInfo.getColumnType();
		String columnSize=dbColumnInfo.getColumnSize();
		boolean isnullAble=dbColumnInfo.isIsnullAble();
		boolean isprimaryKey=dbColumnInfo.isIsprimaryKey();
		List<String> primaryKeys=dbColumnInfo.getListPrimaryKey();
		StringBuilder sql=new StringBuilder(" alter table "+tableName+" add "+columnName);	
		sql.append(this.generateColumnTypeSql(columnType, columnSize));
		sql.append(this.generateCreateDefinitionSql(isnullAble));
		if(isprimaryKey){
			if(primaryKeys.size()==1){
				sql.append(";");
	    		sql.append(this.generateAlertPrimaryKeySql(tableName, primaryKeys));
	    	}else {
	    		sql.append(";");
	    		sql.append(this.generateDropPrimaryKeySql(tableName));
	    		sql.append(";");
	    		sql.append(this.generateAlertPrimaryKeySql(tableName, primaryKeys));
	    	}
		}
		return sql.toString();
	}

	public String getUpdateColumnSql(ColumnInfo oldDbColumnInfo,ColumnInfo newDbColumnInfo) {
		String tableName=newDbColumnInfo.getTableName();
		String newColumnName=newDbColumnInfo.getColumnName();
		String oldColumnName=oldDbColumnInfo.getColumnName();
		String columnType=newDbColumnInfo.getColumnType();
		String columnSize=newDbColumnInfo.getColumnSize();
		boolean isnullAble=newDbColumnInfo.isIsnullAble();
		boolean isprimaryKey=newDbColumnInfo.isIsprimaryKey();
		boolean oldPrimaryKey=oldDbColumnInfo.isIsprimaryKey();
		List<String> primaryKeys=newDbColumnInfo.getListPrimaryKey();
		String cType=this.generateColumnTypeSql(columnType, columnSize);
		String definition=this.generateCreateDefinitionSql(isnullAble);
		StringBuilder sql=new StringBuilder();
	    if(!oldColumnName.equals(newColumnName)){
	    	sql.append("ALTER TABLE "+tableName+" CHANGE "+oldColumnName+" "+newColumnName+cType+definition);
	    }else {
	    	sql.append("ALTER TABLE  "+tableName+" MODIFY  "+newColumnName+cType+definition);
	    }
	    if(isprimaryKey!=oldPrimaryKey){
	    	if(primaryKeys.size()==1&&isprimaryKey==true){
	    		sql.append(";");
	    		sql.append(this.generateAlertPrimaryKeySql(tableName, primaryKeys));
	    	}else {
	    		sql.append(";");
	    		sql.append(this.generateDropPrimaryKeySql(tableName));
	    		sql.append(";");
	    		sql.append(this.generateAlertPrimaryKeySql(tableName, primaryKeys));
	    	}
	    	if(isnullAble){
	    		sql.append(";");
	    		sql.append("ALTER TABLE  "+tableName+" MODIFY  "+newColumnName+cType+definition);
	    	}
		}
		return sql.toString();
	}
	private String generateCreateDefinitionSql(boolean isnullAble){
		StringBuilder sql =new StringBuilder(" ");
		if (isnullAble) {
			sql.append(" DEFAULT NULL ");
		} else {
			sql.append(" NOT NULL ");
		}
		return sql.toString();
	}
	private String generateDropPrimaryKeySql(String tableName){	
		return " ALTER TABLE "+tableName+" DROP PRIMARY KEY";
	}
}
