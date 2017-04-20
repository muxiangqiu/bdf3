package com.bstek.bdf3.security.cola.ui.service;

import com.bstek.bdf3.security.orm.Permission;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月6日
 */
public interface PermissionService {

	void remove(String id);
	
	void remove(String roleId, String resourceId);

	String add(Permission permission);

	void modify(Permission permission);
}
