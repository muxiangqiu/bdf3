package com.bstek.bdf3.dbconsole.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.dbconsole.DbConstants;
import com.bstek.bdf3.dbconsole.DbType;
import com.bstek.bdf3.dbconsole.jdbc.dialect.IDialect;
import com.bstek.bdf3.dbconsole.model.ColumnInfo;
import com.bstek.bdf3.dbconsole.model.DbInfo;
import com.bstek.bdf3.dbconsole.model.SqlWrapper;
import com.bstek.bdf3.dbconsole.service.impl.DbCommonServiceImpl;
import com.bstek.bdf3.dbconsole.utils.SpringJdbcUtils;
import com.bstek.bdf3.dbconsole.utils.UserConfigUtils;

@Service(DbService.BEAN_ID)
public class DbService extends DbCommonServiceImpl {

	public static final String BEAN_ID = "bdf3.dbconsole.dbService";

	
	@Autowired
	private DataSource dataSource;
	
	/**
	 * 初始化默认数据库配置信息
	 * 
	 * @return 返回DbInfo对象
	 * @throws Exception
	 */
	public DbInfo initDefaultDbInfo() throws Exception {
		DbInfo dbInfo = new DbInfo();
		dbInfo.setId(DbConstants.DEFAULTDATASOURCE);
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			dbInfo.setDbType(metaData.getDatabaseProductName());
			dbInfo.setName("默认连接" + dbInfo.getDbType());
			dbInfo.setUrl(metaData.getURL());
			dbInfo.setUsername(metaData.getUserName());
			dbInfo.setProductName(metaData.getDatabaseProductName());
			dbInfo.setProductVersion(metaData.getDatabaseProductVersion());
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return dbInfo;

	}

	/**
	 * 查询用户的所有数据库连接信息
	 * 
	 * @return 返回DbInfo的集合
	 * @throws Exception
	 */
	public List<DbInfo> findDbInfos() throws Exception {
		List<DbInfo> list = new ArrayList<DbInfo>();
		String userName = UserConfigUtils.getUserName();
		list = this.getConsoleDbInfoManager().findDbInfosByUser(userName);
		list.add(this.initDefaultDbInfo());
		return list;
	}

	/**
	 * 修改表名称
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @param newTableName
	 * @throws Exception
	 */
	public void alertTableName(String dbInfoId, String tableName, String newTableName) throws Exception {
		IDialect dialect = getDBDialectByDbInfoId(dbInfoId);
		String sql = dialect.getTableRenameSql(tableName, newTableName);
		String[] sqls = new String[] { sql };
		this.updateSql(dbInfoId, sqls);

	}

	/**
	 * 删除表
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @throws Exception
	 */
	public void deleteTable(String dbInfoId, String tableName) throws Exception {
		String sql = " drop table " + tableName;
		String[] sqls = new String[] { sql };
		this.updateSql(dbInfoId, sqls);
	}

	/**
	 * 删除表内数据
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @throws Exception
	 */
	public void deleteTableData(String dbInfoId, String tableName) throws Exception {
		String sql = " delete from " + tableName;
		String[] sqls = new String[] { sql };
		this.updateSql(dbInfoId, sqls);
	}

	/**
	 * 删除表列
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @param columnName
	 * @throws Exception
	 */
	public void deleteColumn(String dbInfoId, String tableName, String columnName) throws Exception {
		String sql = "alter table " + tableName + "  drop column " + columnName;
		String[] sqls = new String[] { sql };
		this.updateSql(dbInfoId, sqls);
	}

	/**
	 * 插入表新列
	 * 
	 * @param dbInfoId
	 * @param columnInfo
	 * @throws Exception
	 */
	public void insertColumn(String dbInfoId, ColumnInfo columnInfo) throws Exception {
		com.bstek.bdf3.dbconsole.jdbc.dialect.ColumnInfo dbColumnInfo = new com.bstek.bdf3.dbconsole.jdbc.dialect.ColumnInfo();
		BeanUtils.copyProperties(dbColumnInfo, columnInfo);
		String tableName = columnInfo.getTableName();
		String columnName = columnInfo.getColumnName();
		boolean isprimaryKey = columnInfo.isIsprimaryKey();
		List<String> primaryKeys = findTablePrimaryKeys(dbInfoId, tableName);
		if (isprimaryKey) {
			primaryKeys.add(columnName);
			dbColumnInfo.setListPrimaryKey(primaryKeys);
			String pkName = this.findSqlServerPKIndex(dbInfoId, tableName);
			log.debug("pkName:" + pkName);
			if (StringUtils.hasText(pkName)) {
				dbColumnInfo.setPkName(pkName);
			}
		}
		IDialect dBDialect = getDBDialectByDbInfoId(dbInfoId);
		String sql = dBDialect.getNewColumnSql(dbColumnInfo);
		String[] sqls = sql.split(";");
		this.updateSql(dbInfoId, sqls);

	}

	/**
	 * 更新表列
	 * 
	 * @param dbInfoId
	 * @param oldColumnInfo
	 * @param newColumnInfo
	 * @throws Exception
	 */
	public void updateColumn(String dbInfoId, ColumnInfo oldColumnInfo, ColumnInfo newColumnInfo) throws Exception {
		com.bstek.bdf3.dbconsole.jdbc.dialect.ColumnInfo oldDbColumnInfo = new com.bstek.bdf3.dbconsole.jdbc.dialect.ColumnInfo();
		BeanUtils.copyProperties(oldDbColumnInfo, oldColumnInfo);
		com.bstek.bdf3.dbconsole.jdbc.dialect.ColumnInfo newDbColumnInfo = new com.bstek.bdf3.dbconsole.jdbc.dialect.ColumnInfo();
		BeanUtils.copyProperties(newDbColumnInfo, newColumnInfo);
		String tableName = oldColumnInfo.getTableName();
		boolean oldPrimaryKey = oldColumnInfo.isIsprimaryKey();
		boolean newPrimaryKey = newColumnInfo.isIsprimaryKey();
		List<String> primaryKeys = null;
		if (oldPrimaryKey != newPrimaryKey) {
			primaryKeys = findTablePrimaryKeys(dbInfoId, tableName);
			if (newPrimaryKey && !oldPrimaryKey) {
				primaryKeys.add(newDbColumnInfo.getColumnName().toUpperCase());
			} else if (!newPrimaryKey && oldPrimaryKey) {
				primaryKeys.remove(newDbColumnInfo.getColumnName().toUpperCase());
			}
			newDbColumnInfo.setListPrimaryKey(primaryKeys);
			String pkName = this.findSqlServerPKIndex(dbInfoId, tableName);
			if (StringUtils.hasText(pkName)) {
				newDbColumnInfo.setPkName(pkName);
			}
		}
		IDialect dBDialect = getDBDialectByDbInfoId(dbInfoId);
		String sql = dBDialect.getUpdateColumnSql(oldDbColumnInfo, newDbColumnInfo);
		String[] sqls = sql.split(";");
		this.updateSql(dbInfoId, sqls);
	}

	/**
	 * 创建表
	 * 
	 * @param dbInfoId
	 * @param tableName
	 * @throws Exception
	 */
	public void createTable(String dbInfoId, String tableName) throws Exception {
		IDialect dBDialect = getDBDialectByDbInfoId(dbInfoId);
		String sql = dBDialect.getCreateDefaultTableSql(tableName);
		String[] sqls = sql.split(";");
		this.updateSql(dbInfoId, sqls);
	}

	/**
	 * 定义支持的数据库类型，目前支持四种数据库类型
	 * 
	 * @return 返回支持的数据库类型的集合
	 */
	public List<String> loadDbTypes() {
		List<String> dbInfoList = new ArrayList<String>();
		try {
			for (DbType type : DbType.values()) {
				dbInfoList.add(type.name());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return dbInfoList;

	}

	/**
	 * 批处理更新操作
	 * 
	 * @param dbInfoId
	 * @param sqls
	 * @return 返回更新的列的数量
	 * @throws Exception
	 */
	public int[] updateSql(String dbInfoId, String[] sqls) throws Exception {
		final String[] fsqls = this.getFormatArrays(sqls);
		if (log.isDebugEnabled()) {
			for (String s : fsqls) {
				log.debug(s);
			}
		}
		DataSource ds = getDataSourceByDbInfoId(dbInfoId);
		final TransactionTemplate transactionTemplate = SpringJdbcUtils.getTransactionTemplate(ds);
		return transactionTemplate.execute(new TransactionCallback<int[]>() {
			public int[] doInTransaction(TransactionStatus status) {
				JdbcTemplate jdbcTemplate = SpringJdbcUtils.getJdbcTemplate(transactionTemplate);
				int[] i = jdbcTemplate.batchUpdate(fsqls);
				return i;
			}
		});
	}

	/**
	 * 更新表操作
	 * 
	 * @param dbInfoId
	 * @param sql
	 * @param args
	 * @return 返回更新的列的数量
	 * @throws Exception
	 */
	public int updateSql(String dbInfoId, final String sql, final Object[] args) throws Exception {
		log.debug(sql);
		List<SqlWrapper> list = new ArrayList<SqlWrapper>();
		list.add(new SqlWrapper(sql, args));
		int[] ints = updateSql(dbInfoId, list);
		return ints.length > 0 ? ints[0] : 0;
	}

	/**
	 * 对sqlwrapper进行更新
	 * 
	 * @param dbInfoId
	 * @param listSqlWrapper
	 * @return 返回更新的数量
	 * @throws Exception
	 */
	public int[] updateSql(String dbInfoId, final List<SqlWrapper> listSqlWrapper) throws Exception {
		if (log.isDebugEnabled()) {
			for (SqlWrapper sw : listSqlWrapper) {
				log.debug(sw.getSql());
			}
		}
		DataSource ds = getDataSourceByDbInfoId(dbInfoId);
		final TransactionTemplate transactionTemplate = SpringJdbcUtils.getTransactionTemplate(ds);
		return transactionTemplate.execute(new TransactionCallback<int[]>() {
			public int[] doInTransaction(TransactionStatus status) {
				List<Integer> list = new ArrayList<Integer>();
				JdbcTemplate jdbcTemplate = SpringJdbcUtils.getJdbcTemplate(transactionTemplate);
				for (SqlWrapper sw : listSqlWrapper) {
					if (StringUtils.hasText(sw.getSql().trim())) {
						Integer i = jdbcTemplate.update(sw.getSql(), sw.getArgs());
						list.add(i);
					}
				}
				int[] ints = ArrayUtils.toPrimitive((Integer[]) list.toArray(new Integer[list.size()]));
				return ints;
			}
		});
	}

	private String[] getFormatArrays(String[] args) {
		String[] newString = new String[] {};
		List<String> list = new ArrayList<String>();
		for (String s : args) {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(s.trim())) {
				list.add(s);
			}
		}
		newString = list.toArray(new String[list.size()]);
		return newString;
	}

	/**
	 * 查找sqlserver的主键索引
	 * 
	 * @param dbInofId
	 * @param tableName
	 * @return 返回主键索引的值
	 * @throws Exception
	 */
	public String findSqlServerPKIndex(String dbInofId, final String tableName) throws Exception {
		DataSource ds = getDataSourceByDbInfoId(dbInofId);
		JdbcTemplate jdbcTemplate = SpringJdbcUtils.getJdbcTemplate(ds);
		String s = jdbcTemplate.execute(new ConnectionCallback<String>() {
			public String doInConnection(Connection con) throws SQLException, DataAccessException {
				String pkName = null;
				if (con.getMetaData().getURL().toLowerCase().contains("sqlserver")) {
					CallableStatement call = con.prepareCall("{call sp_pkeys(?)}");
					call.setString(1, tableName);
					ResultSet rs = call.executeQuery();
					while (rs.next()) {
						pkName = rs.getString("PK_NAME");
					}
				}
				return pkName;

			}
		});
		return s;
	}

}
