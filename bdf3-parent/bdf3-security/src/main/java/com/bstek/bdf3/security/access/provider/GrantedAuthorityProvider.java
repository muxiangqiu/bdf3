package com.bstek.bdf3.security.access.provider;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 授权信息提供者
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
public interface GrantedAuthorityProvider {

	/**
	 * 提供授权权限信息
	 * @param userDetails 用户信息
	 * @return 授权信息
	 */
	Collection<? extends GrantedAuthority> provide(UserDetails userDetails);
}
