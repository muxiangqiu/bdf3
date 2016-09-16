package com.bstek.bdf3.security.service;

import java.util.List;

import com.bstek.bdf3.security.domain.Component;


/**
 * 组件权限服务接口
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月30日
 */
public interface ComponentService {
	
	/**
	 * 获取所有的组件（包含权限信息）
	 * @return 所有组件（包含权限信息）
	 */
	List<Component> findAll();

}
