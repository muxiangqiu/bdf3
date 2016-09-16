package com.bstek.bdf3.security.ui.service;

import java.util.Collection;
import java.util.List;

import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.ui.builder.ViewComponent;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月12日
 */
public interface RoleComponentService {

	Collection<ViewComponent> loadComponents(String viewName) throws Exception;

	List<Permission> loadPermissions(String roleId, String urlId);

	void save(Permission permission);

}
