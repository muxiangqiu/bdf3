package com.bstek.bdf3.dbconsole.manager;

import java.util.List;

import com.bstek.bdf3.dbconsole.model.DbInfo;

/**
 * 数据库连接配置信息维护
 * 
 */
public interface IConsoleDbInfoManager {
	public static final String BEAN_ID = "bdf3.consoleDbInfoManager";

	/**
	 * 根据用户名查找数据库连接配置文件
	 * 
	 * @param userName
	 * @return 返回DbInfo的List
	 * @throws Exception
	 */
	public List<DbInfo> findDbInfosByUser(String userName) throws Exception;

	/**
	 * 根据id查找数据库连接配置
	 * 
	 * @param id
	 * @return 返回DbInfo对象
	 * @throws Exception
	 */
	public DbInfo findDbInfosById(String id) throws Exception;

	/**
	 * 添加一个数据库连接
	 * 
	 * @param dbInfo
	 * @throws Exception
	 */
	public void insertDbInfo(DbInfo dbInfo) throws Exception;

	/**
	 * 更新数据库连接
	 * 
	 * @param dbInfo
	 * @throws Exception
	 */
	public void updateDbInfo(DbInfo dbInfo) throws Exception;

	/**
	 * 根据id删除数据库连接
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteDbInfoById(String id) throws Exception;

}
