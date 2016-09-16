package com.bstek.bdf3.security.service;

import java.util.List;

import com.bstek.bdf3.security.domain.Url;


/**
 * 菜单服务接口
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月30日
 */
public interface UrlService {
	
	/**
	 * 获取所有菜单（包含权限信息）
	 * @return 所有菜单（包含权限信息）
	 */
	List<Url> findAll();

	/**
	 * 获取用户有权访问的菜单，已树形结构返回
	 * @param username 用户名
	 * @return 菜单列表
	 */
	List<Url> findTreeByUsername(String username);

}
