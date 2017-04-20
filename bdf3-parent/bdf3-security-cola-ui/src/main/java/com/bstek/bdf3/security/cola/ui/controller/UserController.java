package com.bstek.bdf3.security.cola.ui.controller;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.security.cola.ui.service.UserService;
import com.bstek.bdf3.security.orm.RoleGrantedAuthority;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@RestController("cola.userController")
@Transactional(readOnly = true)
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/user/load", method = RequestMethod.GET)
	public List<Map<String, Object>> load(Pageable pageable, @RequestParam(name = "searchKey", required = false) String searchKey) {
		return userService.load(pageable, searchKey);
	}
	
	@RequestMapping(path = "/user/remove/{id}", method = RequestMethod.DELETE)
	@Transactional
	public void remove(@PathVariable String id) {
		userService.remove(id);
	}
	
	@RequestMapping(path = "/user/add", method = RequestMethod.POST)
	@Transactional
	public void add(@RequestBody Map<String, Object> user) throws Exception {
		userService.add(user);
	}

	@RequestMapping(path = "/user/modify", method = RequestMethod.PUT)
	@Transactional
	public void modify(@RequestBody Map<String, Object> user) throws Exception {
		userService.modify(user);
	}
	
	@RequestMapping(path = "/user/exist/{username}", method = RequestMethod.GET)
	public boolean validate(@PathVariable("username") String username) {
		return !userService.isExist(username);
	}
	
	@RequestMapping(path = "/authority/add", method = RequestMethod.POST)
	@Transactional
	public String addRoleGrantedAuthority(@RequestBody RoleGrantedAuthority roleGrantedAuthority) {
		return userService.addRoleGrantedAuthority(roleGrantedAuthority);
		
	}
	
	@RequestMapping(path = "/authority/remove/{id}", method = RequestMethod.DELETE)
	@Transactional
	public void removeRoleGrantedAuthority(@PathVariable String id) {
		userService.removeRoleGrantedAuthority(id);
	}

	

}
