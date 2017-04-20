package com.bstek.bdf3.security.cola.ui.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.security.cola.ui.service.PermissionService;
import com.bstek.bdf3.security.orm.Permission;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@RestController("cola.permissionController")
@RequestMapping("/api")
@Transactional(readOnly = true)
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(path = "/permission/remove/{id}", method = RequestMethod.DELETE)
	@Transactional
	public void remove(@PathVariable String id) {
		permissionService.remove(id);
	}
	
	@RequestMapping(path = "/permission/remove/{roleId}/{resourceId}", method = RequestMethod.DELETE)
	@Transactional
	public void remove(@PathVariable("roleId") String roleId, 
			@PathVariable("resourceId") String resourceId) {
		permissionService.remove(roleId, resourceId);
	}
	
	@RequestMapping(path = "/permission/add", method = RequestMethod.POST)
	@Transactional
	public String add(@RequestBody Permission permission) {
		return permissionService.add(permission);
	}
	
	@RequestMapping(path = "/permission/modify", method = RequestMethod.PUT)
	@Transactional
	public void modify(@RequestBody Permission permission) {
		permissionService.modify(permission);
	}

}
