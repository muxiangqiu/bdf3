package com.bstek.bdf3.security.ui.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bstek.bdf3.security.domain.Url;
import com.bstek.bdf3.security.service.UrlService;
import com.bstek.dorado.annotation.DataProvider;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class FrameworkController {
	
	@Autowired
	private UrlService urlService;
	
	@Value("${bdf3.security.loginSuccessPage:bdf3.security.ui.view.Main.d}")
	private String loginSuccessPage;
	
	@RequestMapping("/") 
	public String home() {
		return "redirect:" + loginSuccessPage;
	}
	
	@DataProvider
	@Transactional(readOnly = true)
	public List<Url> loadUrlsByCurrentUser() {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return urlService.findTreeByUsername(user.getUsername());
	}

	
		

}
