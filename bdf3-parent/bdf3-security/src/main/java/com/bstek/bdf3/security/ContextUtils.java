package com.bstek.bdf3.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.bstek.bdf3.security.orm.User;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年8月10日
 */
public abstract class ContextUtils {
	
	public static User getLoginUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
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
