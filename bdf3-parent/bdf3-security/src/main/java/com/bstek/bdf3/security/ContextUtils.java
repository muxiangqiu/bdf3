package com.bstek.bdf3.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bstek.bdf3.security.orm.User;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public abstract class ContextUtils {
	
	public static User getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof User) {
			return (User) authentication.getPrincipal();
			
		}
		return null;
	}
	
	public static String getLoginUsername() {
		User user = getLoginUser();
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}
}
