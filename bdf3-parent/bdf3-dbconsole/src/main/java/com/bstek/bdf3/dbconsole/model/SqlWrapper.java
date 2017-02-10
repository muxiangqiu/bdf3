package com.bstek.bdf3.dbconsole.model;

public class SqlWrapper {
	public SqlWrapper(String sql, Object[] args) {
		super();
		this.sql = sql;
		this.args = args;
	}
	private String sql;
	private Object[] args;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}

}
