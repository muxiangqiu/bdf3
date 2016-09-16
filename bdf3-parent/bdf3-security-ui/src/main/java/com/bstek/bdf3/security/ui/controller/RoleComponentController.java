package com.bstek.bdf3.security.ui.controller;



import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.ui.builder.ViewComponent;
import com.bstek.bdf3.security.ui.service.RoleComponentService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
@Transactional(readOnly = true)
public class RoleComponentController {
	
	
	@Autowired
	private RoleComponentService roleComponentService;
	
	@DataProvider
	public Collection<ViewComponent> loadComponents(String viewName) throws Exception {
		return roleComponentService.loadComponents(viewName);
	}
	
	
	@DataProvider
	public List<Permission> loadPermissions(String roleId, String urlId) {
		return roleComponentService.loadPermissions(roleId, urlId);
	}
	
	@DataResolver
	@Transactional
	public void save(Permission permission) {
		roleComponentService.save(permission);
	}

	
	
	

}
