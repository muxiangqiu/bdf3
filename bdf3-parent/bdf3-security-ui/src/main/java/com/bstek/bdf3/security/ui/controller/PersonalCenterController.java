package com.bstek.bdf3.security.ui.controller;



import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.security.orm.User;
import com.bstek.bdf3.security.ui.service.PersonalCenterService;
import com.bstek.dorado.annotation.DataProvider;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class PersonalCenterController {
	
	@Autowired
	protected PersonalCenterService personalCenterService;
	
	@DataProvider
	public List<User> loadUser(String username) {
		return Arrays.asList(personalCenterService.getUser(username));
	}

}
