package com.bstek.bdf3.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 主要提供用户的授权信息的收集功能
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年12月6日
 */
public interface GrantedAuthorityService {

	/**
	 * 获取用户的授权信息
	 * @param userDetails 用户信息
	 * @return 权限信息
	 */
	Collection<? extends GrantedAuthority> getGrantedAuthorities(UserDetails userDetails);

}
