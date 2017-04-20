package com.bstek.bdf3.security.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.security.orm.User;
import com.bstek.bdf3.security.ui.service.UserService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class UserController {
	
	@Autowired
	protected UserService userService;
	
	@DataProvider
	public void load(Page<User>page, Criteria criteria) {
		userService.load(page, criteria);
	}
	
	@Expose
	public String validateOldPassword(String oldPassword) {
		return userService.validateOldPassword(oldPassword);
	}
	
	@DataResolver
	@Transactional
	public void save(List<User> users) {
		userService.save(users);
	}
	
	@Expose
	@Transactional
	public void changePassword(String username, String newPassword) {
		userService.changePassword(username, newPassword);
	}
	

}
