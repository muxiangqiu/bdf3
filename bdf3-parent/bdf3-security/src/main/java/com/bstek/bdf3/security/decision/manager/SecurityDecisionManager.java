package com.bstek.bdf3.security.decision.manager;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;

import com.bstek.bdf3.security.orm.Resource;

/**
 * 权限决策管理器。<br>
 * 实现{@link org.springframework.security.access.SecurityMetadataSource}来<br>
 * 提供自己定义资源的权限信息，就可以实现权限决策判断
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月7日
 */
public interface SecurityDecisionManager {

	/**
	 * 决策给定的资源，当前登陆用户是否有权限
	 * @param resource 资源
	 * @return 结果。true为有权限，否则没有
	 */
	boolean decide(Resource resource);

	/**
	 * 根据资源获取权限信息
	 * @param resource 资源
	 * @return 权限列表
	 */
	Collection<ConfigAttribute> findConfigAttributes(Resource resource);

	/**
	 * 决策给定的资源，给定用户是否有权限
	 * @param username 用户名
	 * @param resource 资源
	 * @return 结果。true为有权限，否则没有
	 */
	boolean decide(String username, Resource resource);
}
