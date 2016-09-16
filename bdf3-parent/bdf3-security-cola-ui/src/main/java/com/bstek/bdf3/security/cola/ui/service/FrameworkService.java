package com.bstek.bdf3.security.cola.ui.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.bstek.bdf3.message.domain.Notify;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月15日
 */
public interface FrameworkService {

	String getHomePage();
	
	String getLoginPage();
	
	String getMainPage();
	
	String getUserPage();
	
	String getUrlPage();
	
	String getRolePage();
	
	String getComponentPage();
		
	UserDetails getLoginUserInfo();

	Long getMessageTotal(String username);

	List<Notify> getMessages(String username);
}
