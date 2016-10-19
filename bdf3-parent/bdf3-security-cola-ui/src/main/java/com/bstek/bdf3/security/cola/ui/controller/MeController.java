package com.bstek.bdf3.security.cola.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.security.cola.ui.service.RoleService;
import com.bstek.bdf3.security.cola.ui.service.UserService;
import com.bstek.bdf3.security.domain.Role;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@RestController
@Transactional(readOnly = true)
@RequestMapping("/api")
public class MeController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/me/load-role", method = RequestMethod.GET)
	public List<Role> load(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return roleService.load(user.getUsername());
	}
	
	@RequestMapping(path = "/me/validate-password/{password}", method = RequestMethod.GET)
	public boolean validatePassword(@PathVariable String password, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return userService.validatePassword(user.getUsername(), password);
	}
	
	@RequestMapping(path = "/me/change-password/{newPassword}", method = RequestMethod.PUT)
	@Transactional
	public void changePassword(@PathVariable String newPassword, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		userService.changePassword(user.getUsername(), newPassword);
	}
	
	@RequestMapping(path = "/me/modify-nickname", method = RequestMethod.PUT)
	@Transactional
	public void modifyNickname(@RequestBody String nickname, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		userService.modifyNickname(user.getUsername(), nickname);
	}



}
