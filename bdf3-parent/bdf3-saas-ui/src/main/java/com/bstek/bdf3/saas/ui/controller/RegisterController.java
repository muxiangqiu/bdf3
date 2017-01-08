package com.bstek.bdf3.saas.ui.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.ui.service.RegisterService;
import com.bstek.bdf3.security.domain.User;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@RestController
@RequestMapping("/api")
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping(path = "/register/organization", method = RequestMethod.POST)
	public void registerOrganization(@RequestBody Map<String, Object> info) throws Exception {
		Organization organization = new Organization();
		organization.setId((String)info.get("organizationId"));
		organization.setName((String)info.get("organizationName"));
		User user = new User();
		user.setOrganization(organization);
		user.setUsername((String)info.get("username"));
		user.setNickname((String)info.get("nickname"));
		user.setPassword((String)info.get("password"));
		registerService.registerOrganization(user);
	}
	
	@RequestMapping(path = "/register/user", method = RequestMethod.POST)
	public void registerUser(@RequestBody Map<String, Object> info) throws Exception {
		Organization organization = new Organization();
		organization.setId((String)info.get("organizationId"));
		organization.setName((String)info.get("organizationName"));
		User user = new User();
		user.setOrganization(organization);
		user.setUsername((String)info.get("username"));
		user.setNickname((String)info.get("nickname"));
		user.setPassword((String)info.get("password"));
		registerService.registerUser(user);
	}
	
	@RequestMapping(path = "/register/exist/user/{organizationId}/{username}", method = RequestMethod.GET)
	public boolean isExistUser(@PathVariable String organizationId, @PathVariable String username) throws Exception {
		return registerService.isExistUser(organizationId, username);
	}
	
	@RequestMapping(path = "/register/exist/organization/{organizationId}", method = RequestMethod.GET)
	public boolean isExistOrganization(@PathVariable String organizationId) throws Exception {
		return registerService.isExistOrganization(organizationId);
	}
	
	@RequestMapping(path = "/register/organization/{organizationId}", method = RequestMethod.GET)
	public String getOrganizationName(@PathVariable String organizationId) throws Exception {
		return registerService.getOrganization(organizationId).getName();
	}


}
