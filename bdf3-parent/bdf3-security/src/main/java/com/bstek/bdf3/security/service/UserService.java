package com.bstek.bdf3.security.service;

/**
 * 用户服务接口
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年6月6日
 */
public interface UserService {
	
	/**
	 * 判断当前登录用户是否为管理员
	 * @return 返回ture，则为管理员，否则不是
	 */
	boolean isAdministrator();

	
	/**
	 * 判断是否为管理员
	 * @return 返回ture，则为管理员，否则不是
	 */
	boolean isAdministrator(String username);

}
