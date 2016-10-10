package com.bstek.bdf3.security.cola.ui.controller;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstek.bdf3.message.domain.Notify;
import com.bstek.bdf3.security.cola.ui.service.FrameworkService;
import com.bstek.bdf3.security.domain.Url;
import com.bstek.bdf3.security.service.UrlService;
import com.bstek.bdf3.security.user.SecurityUserUtil;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
public class FrameworkController {
	
	@Autowired
	private UrlService urlService;
	
	@Value("${bdf3.application.title:BDF}")
	private String applicationTitle;
	
	@Value("${bdf3.application.name:BDF}")
	private String applicationName;
	
	@Value("${bdf3.security.logoutPath:/logout}")
	private String logoutPath;
	
	@Value("${bdf3.message.disabled:false}")
	private boolean messageDisabled;
	
	@Value("${bdf3.message.longPollingTimeout:0}")
    private int longPollingTimeout;
	
	@Value("${bdf3.message.liveMessage:true}")
    private boolean liveMessage;
    
    @Value("${bdf3.message.longPollingInterval:15000}")
    private int longPollingInterval;
    
    @Value("${bdf3.message.pullPath:./service/message/pull}")
    private String messagePullPath;
    
    @Value("${bdf3.message.totallPullPath:./service/message/total/pull}")
    private String messageTotalPullPath;
    
    @Autowired
    private FrameworkService frameworkService;
	
	@RequestMapping("/") 
	public String home() {
		return frameworkService.getHomePage();
	}
	
	@RequestMapping("/main") 
	public String main(Model model) {
		model.addAttribute("applicationTitle", applicationTitle);
		model.addAttribute("applicationName", applicationName);
		model.addAttribute("logoutPath", logoutPath.substring(1));
		model.addAttribute("messageDisabled", messageDisabled);
		model.addAttribute("longPollingTimeout", longPollingTimeout);
		model.addAttribute("liveMessage", liveMessage);
		model.addAttribute("longPollingInterval", longPollingInterval);
		model.addAttribute("messagePullPath", messagePullPath);
		model.addAttribute("messageTotalPullPath", messageTotalPullPath);
		model.addAttribute(SecurityUserUtil.getUserMetadateService());
		return frameworkService.getMainPage();
	}
	
	@RequestMapping("/login") 
	public String login(HttpSession session, Model model) {
		model.addAttribute(SecurityUserUtil.getUserMetadateService());
		Exception e = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		model.addAttribute("applicationTitle", applicationTitle);
		if (e != null) {
			model.addAttribute("error", e.getLocalizedMessage());
		} else {
			model.addAttribute("error", "");
		}
		return frameworkService.getLoginPage();
	}
	
	@RequestMapping("/user") 
	public String user(Model model) {
		model.addAttribute(SecurityUserUtil.getUserMetadateService());
		return frameworkService.getUserPage();
	}
	
	@RequestMapping("/role") 
	public String role() {
		return frameworkService.getRolePage();
	}
	
	@RequestMapping("/url") 
	public String url() {
		return frameworkService.getUrlPage();
	}
	
	@RequestMapping("/component") 
	public String component() {
		return frameworkService.getComponentPage();
	}
	
	@RequestMapping("/service/menus")
	@ResponseBody
	@Transactional(readOnly = true)
	public List<Url> loadUrlsByCurrentUser(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return urlService.findTreeByUsername(user.getUsername());
	}
	
	@RequestMapping("/service/user/detail")
	@ResponseBody
	@Transactional(readOnly = true)
	public UserDetails getLoginUser(){
		return frameworkService.getLoginUserInfo();
	}
	
	@RequestMapping(path = "/service/message/total/pull", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public Long getMessageTotal(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return frameworkService.getMessageTotal(user.getUsername());
	}
	
	@RequestMapping(path = "/service/message/pull", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public List<Notify> getMessages(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return frameworkService.getMessages(user.getUsername());
	}
	
	
		

}
