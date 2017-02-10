package com.bstek.bdf3.dbconsole.controller;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.dbconsole.manager.IConsoleDbInfoManager;
import com.bstek.bdf3.dbconsole.model.ColumnInfo;
import com.bstek.bdf3.dbconsole.model.DataGridWrapper;
import com.bstek.bdf3.dbconsole.model.DbInfo;
import com.bstek.bdf3.dbconsole.model.SqlWrapper;
import com.bstek.bdf3.dbconsole.model.TableInfo;
import com.bstek.bdf3.dbconsole.service.DbService;
import com.bstek.bdf3.dbconsole.service.ISqlWrapperService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityEnhancer;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.entity.FilterType;
import com.bstek.dorado.data.provider.Page;

@Controller
public class DbConsoleController {

	@Autowired
	@Qualifier(DbService.BEAN_ID)
	private DbService dbService;
	
	@Autowired
	@Qualifier(ISqlWrapperService.BEAN_ID)
	private ISqlWrapperService sqlWrapperService;
	
	@Autowired
	@Qualifier(IConsoleDbInfoManager.BEAN_ID)
	private IConsoleDbInfoManager consoleDbInfoManager;

	@DataProvider
	public Collection<DbInfo> loadDbInfos() throws Exception {
		return dbService.findDbInfos();
	}

	@DataProvider
	public Collection<TableInfo> loadTableInfos(String name) throws Exception {
		return dbService.findTableInfos(name);
	}

	@DataProvider
	@Expose
	public Collection<ColumnInfo> loadColumnInfos(String dbInfoId, String tableName) throws Exception {
		return dbService.findColumnInfos(dbInfoId, tableName);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DataProvider
	public void loadQueryTableData(Page page, Map<String, Object> map) throws Exception {
		String dbInfoId = (String) map.get("dbInfoId");
		String tableName = (String) map.get("tableName");
		String sql = (String) map.get("sql");
		DataGridWrapper tableData = dbService.queryTableData(dbInfoId, tableName, sql, page.getPageSize(), page.getPageNo());
		page.setEntities(tableData.getTableData());
		page.setEntityCount(tableData.getTotalCount());
	}

	@SuppressWarnings("unchecked")
	@DataResolver
	public void saveDbInfos(Collection<DbInfo> coll) throws Exception {
		for (Iterator<DbInfo> iter = EntityUtils.getIterator(coll, FilterType.DIRTY); iter.hasNext();) {
			DbInfo dbInfo = iter.next();
			EntityState state = EntityUtils.getState(dbInfo);
			if (state.equals(EntityState.NEW)) {
				consoleDbInfoManager.insertDbInfo(dbInfo);
			}
			if (state.equals(EntityState.MODIFIED)) {
				consoleDbInfoManager.updateDbInfo(dbInfo);

			}
			if (state.equals(EntityState.DELETED)) {
				consoleDbInfoManager.deleteDbInfoById(dbInfo.getId());
			}
		}

	}

	@SuppressWarnings("unchecked")
	@DataResolver
	public void saveTableColumn(Collection<ColumnInfo> coll, Map<String, Object> map) throws Exception {
		String dbInfoId = (String) map.get("dbInfoId");
		String tableName = (String) map.get("tableName");
		for (Iterator<ColumnInfo> iter = EntityUtils.getIterator(coll, FilterType.ALL); iter.hasNext();) {
			ColumnInfo columnInfo = iter.next();
			columnInfo.setTableName(tableName);
			EntityState state = EntityUtils.getState(columnInfo);
			if (state.equals(EntityState.NEW)) {
				dbService.insertColumn(dbInfoId, columnInfo);
			}
			if (state.equals(EntityState.MODIFIED)) {
				EntityEnhancer entityEnhancer = EntityUtils.getEntityEnhancer(columnInfo);
				String oldDefaultValue = null;
				if (entityEnhancer != null) {
					Map<String, Object> oldValues = entityEnhancer.getOldValues();
					if (oldValues.get("defaultValue") != null) {
						oldDefaultValue = (String) oldValues.get("defaultValue");
					}
				}
				ColumnInfo oldColumnInfo = new ColumnInfo();
				BeanUtils.copyProperties(oldColumnInfo, columnInfo);
				oldColumnInfo.setColumnName(EntityUtils.getOldString(columnInfo, "columnName"));
				oldColumnInfo.setIsnullAble(EntityUtils.getOldBoolean(columnInfo, "isnullAble"));
				oldColumnInfo.setIsprimaryKey(EntityUtils.getOldBoolean(columnInfo, "isprimaryKey"));
				oldColumnInfo.setDefaultValue(oldDefaultValue);
				dbService.updateColumn(dbInfoId, oldColumnInfo, columnInfo);

			}
			if (state.equals(EntityState.DELETED)) {
				dbService.deleteColumn(dbInfoId, tableName, columnInfo.getColumnName());
			}
		}

	}

	@SuppressWarnings("unchecked")
	@DataResolver
	public void saveTableData(Collection<Map<String, Object>> coll, Map<String, Object> map) throws Exception {
		String dbInfoId = (String) map.get("dbInfoId");
		String tableName = (String) map.get("tableName");
		List<SqlWrapper> listSqlWrapper = new ArrayList<SqlWrapper>();
		for (Iterator<Map<String, Object>> iter = EntityUtils.getIterator(coll, FilterType.ALL); iter.hasNext();) {
			Map<String, Object> mapValues = iter.next();
			EntityState state = EntityUtils.getState(mapValues);
			if (state.equals(EntityState.NEW)) {
				listSqlWrapper.add(sqlWrapperService.getInsertTableSql(tableName, mapValues));
			}
			if (state.equals(EntityState.MODIFIED)) {
				Map<String, Object> oldMapValues = this.getTableOldMapValues(mapValues);
				listSqlWrapper.add(sqlWrapperService.getUpdateTableSql(tableName, mapValues, oldMapValues));
			}
			if (state.equals(EntityState.DELETED)) {
				Map<String, Object> oldMapValues = this.getTableOldMapValues(mapValues);
				listSqlWrapper.add(sqlWrapperService.getDeleteTableSql(tableName, oldMapValues));
			}
		}
		if (listSqlWrapper.size() > 0) {
			dbService.updateSql(dbInfoId, listSqlWrapper);
		}

	}

	private Map<String, Object> getTableOldMapValues(Map<String, Object> mapValues) {
		EntityEnhancer entityEnhancer = EntityUtils.getEntityEnhancer(mapValues);
		if (entityEnhancer != null) {
			Map<String, Object> oldValues = entityEnhancer.getOldValues();
			if (oldValues != null) {
				return oldValues;
			} else {
				return mapValues;
			}
		}
		return null;
	}

	@DataProvider
	public Collection<DbInfo> loadDbTypes() throws Exception {
		List<String> list = dbService.loadDbTypes();
		Collection<DbInfo> dbInfoList = new ArrayList<DbInfo>();
		DbInfo dbInfo = null;
		for (String s : list) {
			dbInfo = new DbInfo();
			dbInfo.setDbType(s);
			dbInfoList.add(dbInfo);
		}
		return dbInfoList;
	}

	/**
	 * 测试连接是否成功
	 * 
	 * @param map
	 * @return 成功返回null,否则返回出错提示信息
	 */
	@Expose
	public String testConnection(Map<String, Object> map) {
		String url = (String) map.get("url");
		String driverClassName = (String) map.get("driverClassName");
		String username = (String) map.get("username");
		String password = (String) map.get("password");
		return dbService.checkDbConnection(driverClassName, url, username, password);
	}

	@Expose
	public String executeUpdateSql(String dbInfoId, String sql) throws Exception {
		String[] sqls = sql.split(";");
		try {
			@SuppressWarnings("unused")
			int[] ints = dbService.updateSql(dbInfoId, sqls);
		} catch (BatchUpdateException e) {
			return e.getMessage();
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}

	@DataProvider
	public List<ColumnInfo> findColumnType(String dbInfoId) throws Exception {
		if (StringUtils.hasText(dbInfoId)) {
			List<ColumnInfo> list = new ArrayList<ColumnInfo>();
			ColumnInfo info = null;
			List<String> types = dbService.findDefaultColumnType(dbInfoId);
			for (String s : types) {
				info = new ColumnInfo();
				info.setColumnType(s);
				list.add(info);
			}
			return list;
		}
		return null;
	}

	@Expose
	public void deleteTable(String dbInfoId, String tableName) throws Exception {
		dbService.deleteTable(dbInfoId, tableName);
	}

	@Expose
	public void alertTableName(String dbInfoId, String tableName, String newTableName) throws Exception {
		dbService.alertTableName(dbInfoId, tableName, newTableName);
	}

	@Expose
	public void createTable(String dbInfoId, String tableName) throws Exception {
		dbService.createTable(dbInfoId, tableName);
	}

	@Expose
	public void deleteTableData(String dbInfoId, String tableName) throws Exception {
		dbService.deleteTableData(dbInfoId, tableName);
	}

}
