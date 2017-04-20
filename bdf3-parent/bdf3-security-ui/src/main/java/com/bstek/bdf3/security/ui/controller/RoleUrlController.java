package com.bstek.bdf3.security.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.security.orm.Permission;
import com.bstek.bdf3.security.ui.service.RoleUrlService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class RoleUrlController {
	
	@Autowired
	private RoleUrlService roleUrlService;
	
	@DataProvider
	public List<Permission> load(String roleId) {
		return roleUrlService.load(roleId);
	}
	
	@DataResolver
	public void save(List<Permission> permissions) {
		roleUrlService.save(permissions);
	}
	

}
