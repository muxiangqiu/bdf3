package com.bstek.bdf3.security.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.security.orm.Role;
import com.bstek.bdf3.security.orm.User;
import com.bstek.bdf3.security.ui.service.RoleAssignmentService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class RoleAssignmentController {
	
	@Autowired
	private RoleAssignmentService roleAssignmentService;
	
	@DataProvider
	public void loadUsersWithout(Page<User> page, Criteria criteria, String roleId) {
		roleAssignmentService.loadUsersWithout(page, criteria, roleId);
	}
	
	@DataProvider
	public void loadUsersWithin(Page<User> page, Criteria criteria, String roleId) {
		roleAssignmentService.loadUsersWithin(page, criteria, roleId);
	}
	
	@DataResolver
	public void save(List<Role> roles) {		
		roleAssignmentService.save(roles);
	}
	

}
