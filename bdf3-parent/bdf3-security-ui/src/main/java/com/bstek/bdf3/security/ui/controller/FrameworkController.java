package com.bstek.bdf3.security.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bstek.bdf3.security.orm.Url;
import com.bstek.bdf3.security.ui.service.FrameworkService;
import com.bstek.dorado.annotation.DataProvider;



/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class FrameworkController {
	
	@Autowired
	private FrameworkService frameworkService;
	
	@Value("${bdf3.loginSuccessPage}")
	private String loginSuccessPage;
	
	@RequestMapping("/") 
	public String home() {
		return "redirect:" + loginSuccessPage;
	}
	
	@DataProvider
	public List<Url> loadUrlForLoginUser() {
		return frameworkService.loadUrlForLoginUser();
	}

	
		

}
