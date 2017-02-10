package com.bstek.bdf3.dbconsole.jdbc.dialect;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SQLServer2005Dialect extends AbstractDialect{
	private String DISTINCT="distinct";
	private String SELECT="select";
	private String FROM="from";

	public boolean support(Connection connection) {
		return support(connection, "sql server", "9");
	}
	
	private void replaceDistinctWithGroupBy(StringBuilder sql) {
		int distinctIndex = sql.indexOf(DISTINCT);
		if (distinctIndex > 0) {
			sql.delete(distinctIndex, distinctIndex + DISTINCT.length() + 1);
			sql.append(" group by").append(getSelectFieldsWithoutAliases(sql));
		}
	}
	
	private CharSequence getSelectFieldsWithoutAliases(StringBuilder sql) {
		String select = sql.substring(sql.indexOf(SELECT) + SELECT.length(), sql.indexOf(FROM));

		// Strip the as clauses
		return stripAliases(select);
	}
	
	private String stripAliases(String str) {
		return str.replaceAll("\\sas[^,]+(,?)", "$1");
	}
	
	private void insertRowNumberFunction(StringBuilder sql, CharSequence orderby) {
		// Find the end of the select statement
		//int selectEndIndex = sql.indexOf(SELECT) + SELECT.length();
		// Insert after the select statement the row_number() function:
		//sql.insert(selectEndIndex, " ROW_NUMBER() OVER (" + orderby + ") as __hibernate_row_nr__,");
		int fromStartIndex = sql.indexOf(FROM);
		sql.insert(fromStartIndex, " ,ROW_NUMBER() OVER (" + orderby + ") as __hibernate_row_nr__ ");
	}
	
	public String getPaginationSql(String sql, int pageNo, int pageSize) {
		int startNo = (pageNo - 1) * pageSize+1;
		int endNo = pageNo * pageSize;
		StringBuilder sb = new StringBuilder(sql.trim().toLowerCase());

		int orderByIndex = sb.indexOf("order by");
		CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length())
				: "ORDER BY CURRENT_TIMESTAMP";

		// Delete the order by clause at the end of the query
		if (orderByIndex > 0) {
			sb.delete(orderByIndex, orderByIndex + orderby.length());
		}

		replaceDistinctWithGroupBy(sb);

		insertRowNumberFunction(sb, orderby);

		// Wrap the query within a with statement:
		sb.insert(0, "WITH query AS (").append(") SELECT * FROM query ");
		sb.append("WHERE __hibernate_row_nr__ BETWEEN "+startNo+" AND "+endNo+"");
		return sb.toString();
	}


	public String getTableRenameSql(String tableName, String newTableName) {
		return " EXEC sp_rename '"+tableName+"', '"+newTableName+"'";

	}

	public String getNewColumnSql(ColumnInfo dbColumnInfo) {
		String tableName=dbColumnInfo.getTableName();
		String columnName=dbColumnInfo.getColumnName();
		String columnType=dbColumnInfo.getColumnType();
		String columnSize=dbColumnInfo.getColumnSize();
		boolean isnullAble=dbColumnInfo.isIsnullAble();
		boolean isprimaryKey=dbColumnInfo.isIsprimaryKey();
		String pkName=dbColumnInfo.getPkName();
		List<String> primaryKeys=dbColumnInfo.getListPrimaryKey();
		StringBuilder sql=new StringBuilder(" alter table "+tableName+" add "+columnName);
		String columnTypeSql=this.generateColumnTypeSql(columnType, columnSize);
		sql.append(columnTypeSql);
		if(!isnullAble){
			sql.append(";");
			sql.append("ALTER TABLE  "+tableName+" ALTER COLUMN "+columnName+columnTypeSql+ " NOT NULL ");;
		}
		if(isprimaryKey){
			if(primaryKeys.size()==1){
				sql.append(";");
	    		sql.append(this.generateAlertPrimaryKeySql(tableName, primaryKeys));
	    	}else {
	    		sql.append(";");
	    		sql.append(this.generateDropPrimaryKeySql(tableName,pkName));
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
		boolean oldNullAble=oldDbColumnInfo.isIsnullAble();
		boolean isprimaryKey=newDbColumnInfo.isIsprimaryKey();
		boolean oldPrimaryKey=oldDbColumnInfo.isIsprimaryKey();
		List<String> primaryKeys=newDbColumnInfo.getListPrimaryKey();
		String pkName=newDbColumnInfo.getPkName();
		String cType=this.generateColumnTypeSql(columnType, columnSize);
		StringBuilder sql=new StringBuilder();
		 if(!oldColumnName.equals(newColumnName)){
		    	sql.append(" sp_rename '"+tableName+"."+oldColumnName+"','"+newColumnName+"','column'");
		 }
		 sql.append(";");
		 if(isnullAble&&oldNullAble==false&&isprimaryKey!=true){
				sql.append("ALTER TABLE  "+tableName+" ALTER COLUMN "+newColumnName+cType+ " NOT NULL ");
		 }else{
				sql.append("ALTER TABLE  "+tableName+" ALTER COLUMN "+newColumnName+cType+this.generateCreateDefinitionSql(isnullAble));
		 }
		if (isprimaryKey != oldPrimaryKey) {
			if (primaryKeys.size() == 1 && isprimaryKey == true) {
				sql.append(";");
	    		sql.append(this.generateAlertPrimaryKeySql(tableName, primaryKeys));
			} else {
				sql.append(";");
	    		sql.append(this.generateDropPrimaryKeySql(tableName,pkName));
	    		sql.append(";");
	    		sql.append(this.generateAlertPrimaryKeySql(tableName, primaryKeys));
			}

		}
		if(isnullAble&&oldNullAble==false){
			sql.append(";");
			sql.append("ALTER TABLE  "+tableName+" ALTER COLUMN "+newColumnName+cType+ "  NULL ");
	    }else if(!isnullAble&&oldNullAble==true&&isprimaryKey==false){
	    	sql.append(";");
	    	sql.append("ALTER TABLE  "+tableName+" ALTER COLUMN "+newColumnName+cType+ " NOT NULL ");
	    }
		return sql.toString();
	}
	private String generateCreateDefinitionSql(boolean isnullAble){
		StringBuilder sql=new StringBuilder(" ");
		if (isnullAble) {
			sql.append(" NULL ");
		} else {
			sql.append(" NOT NULL ");
		}
		return sql.toString();
	}
	private String generateDropPrimaryKeySql(String tableName,String pkName){
		return "ALTER TABLE "+tableName+" DROP CONSTRAINT "+ pkName ;


	}
}
