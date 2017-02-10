package com.bstek.bdf3.dbconsole.jdbc.dialect;

import java.sql.Connection;

import org.springframework.stereotype.Component;

@Component
public class DB2Dialect extends AbstractDialect {
	public boolean support(Connection connection) {
		return support(connection, "db2", null);
	}
	
	private String getRowNumber(String sql) {
		StringBuffer rownumber = new StringBuffer(50)
			.append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if ( orderByIndex>0 && !hasDistinct(sql) ) {
			rownumber.append( sql.substring(orderByIndex) );
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}
	
	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct")>=0;
	}

	public  String getPaginationSql(String sql, int pageNo, int pageSize) {
		int startNo = (pageNo - 1) * pageSize;
		int endNo = pageNo * pageSize;
		
		int startOfSelect = sql.toLowerCase().indexOf("select");

		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 )
				.append( sql.substring(0, startOfSelect) )	// add the comment
				.append("select * from ( select ") 			// nest the main query in an outer select
				.append( getRowNumber(sql) ); 				// add the rownnumber bit into the outer query select list

		if ( hasDistinct(sql) ) {
			pagingSelect.append(" row_.* from ( ")			// add another (inner) nested select
					.append( sql.substring(startOfSelect) ) // add the main query
					.append(" ) as row_"); 					// close off the inner nested select
		}
		else {
			pagingSelect.append( sql.substring( startOfSelect + 6 ) ); // add the main query
		}

		pagingSelect.append(" ) as temp_ where rownumber_ ");

		//add the restriction to the outer select
		pagingSelect.append("between "+startNo+" and "+endNo+"");
		return pagingSelect.toString();
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
		StringBuilder sql=new StringBuilder(" alter table "+tableName+" add "+columnName);	
		sql.append(this.generateColumnTypeSql(columnType, columnSize));
		sql.append(this.generateCreateDefinitionSql(isnullAble));
		if(!isnullAble){
			sql.append(";");
			sql.append("alter table "+tableName+" alter column "+columnName+" set not null");;
		}
		return sql.toString();
	}


	public String getUpdateColumnSql(ColumnInfo oldDbColumnInfo,ColumnInfo newDbColumnInfo) {
		return null;
	}
	private String generateCreateDefinitionSql(boolean isnullAble){
		if(isnullAble){
			return " NULL ";
		}
		return " ";
	}
}
