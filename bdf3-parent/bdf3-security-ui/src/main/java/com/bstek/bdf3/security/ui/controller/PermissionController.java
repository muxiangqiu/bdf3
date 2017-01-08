package com.bstek.bdf3.security.ui.controller;



import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.ui.builder.ViewComponent;
import com.bstek.bdf3.security.ui.service.PermissionService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class PermissionController {
	
	
	@Autowired
	private PermissionService permissionService;
	
	@DataProvider
	public Collection<ViewComponent> loadComponents(String viewName) throws Exception {
		return permissionService.loadComponents(viewName);
	}
	
	
	@DataProvider
	public List<Permission> loadPermissions(String roleId, String urlId) {
		return permissionService.loadPermissions(roleId, urlId);
	}
	
	@DataResolver
	public void save(Permission permission) {
		permissionService.save(permission);
	}

	
	
	

}
