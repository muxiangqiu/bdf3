package com.bstek.bdf3.security.saas.cola.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bstek.bdf3.security.saas.cola.ui.service.SaasFrameworkService;
import com.bstek.bdf3.security.user.SecurityUserUtil;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月15日
 */
@Controller
public class SaasFrameworkController {

	@Autowired
	private SaasFrameworkService saasFrameworkService;
	
	@Value("${bdf3.security.loginPage:/login}")
	protected String loginPath;
	
	@Value("${bdf3.application.title:BDF}")
	private String applicationTitle;
	
	@RequestMapping("/register") 
	public String register(Model model) {
		model.addAttribute(SecurityUserUtil.getUserMetadateService());
		model.addAttribute("loginPath", loginPath.substring(1));
		model.addAttribute("applicationTitle", applicationTitle);
		return saasFrameworkService.getRegisterPage();
	}
}
