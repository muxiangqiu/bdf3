package com.bstek.bdf3.security.saas.cola.ui.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.security.saas.cola.ui.service.RegisterService;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@RestController
@RequestMapping("/service")
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping(path = "/register/organization", method = RequestMethod.POST)
	public void registerOrganization(@RequestBody Map<String, Object> info) throws Exception {
		registerService.registerOrganization(info);
	}
	
	@RequestMapping(path = "/register/user", method = RequestMethod.POST)
	public void registerUser(@RequestBody Map<String, Object> info) throws Exception {
		registerService.registerUser(info);
	}
	
	@RequestMapping(path = "/register/exist/user/{organizationId}/{username}", method = RequestMethod.GET)
	public boolean isExistUser(@PathVariable String organizationId, @PathVariable String username) throws Exception {
		return registerService.isExistUser(organizationId, username);
	}
	
	@RequestMapping(path = "/register/exist/organization/{organizationId}", method = RequestMethod.GET)
	public boolean isExistOrganization(@PathVariable String organizationId) throws Exception {
		return registerService.isExistOrganization(organizationId);
	}

}
