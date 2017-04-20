package com.bstek.bdf3.security.cola.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.security.cola.ui.service.RoleService;
import com.bstek.bdf3.security.orm.Role;
import com.bstek.bdf3.security.orm.Url;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@RestController("cola.roleController")
@RequestMapping("/api")
@Transactional(readOnly = true)
public class RoleController {
	
	@Autowired
	private RoleService roleService;
		
	@RequestMapping(path = "/role/load", method = RequestMethod.GET)
	public List<Role> load(Pageable pageable, @RequestParam(name = "searchKey", required = false) String searchKey) {
		return roleService.load(pageable, searchKey);
	}
	
	@RequestMapping(path ="/role/load-urls/{roleId}", method = RequestMethod.GET)
	public List<Url> loadUrls(@PathVariable String roleId) {
		return roleService.loadUrls(roleId);
	}
	
	@RequestMapping(path = "/role/remove/{id}", method = RequestMethod.DELETE)
	@Transactional
	public void remove(@PathVariable String id) {
		roleService.remove(id);
	}
	
	@RequestMapping(path = "/role/add", method = RequestMethod.POST)
	@Transactional
	public String add(@RequestBody Role role) {
		return roleService.add(role);
	}

	@RequestMapping(path = "/role/modify", method = RequestMethod.PUT)
	@Transactional
	public void modify(@RequestBody Role role) {
		roleService.modify(role);
	}

}
