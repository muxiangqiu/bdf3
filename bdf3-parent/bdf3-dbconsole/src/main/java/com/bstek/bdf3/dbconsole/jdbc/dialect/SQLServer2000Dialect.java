package com.bstek.bdf3.dbconsole.jdbc.dialect;

import java.sql.Connection;

import org.springframework.stereotype.Component;

@Component
public class SQLServer2000Dialect extends SQLServer2003Dialect {

	@Override
	public boolean support(Connection connection) {
		return support(connection, "sql server", "7");
	}
}
