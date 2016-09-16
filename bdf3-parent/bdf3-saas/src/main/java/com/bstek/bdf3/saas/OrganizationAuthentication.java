package com.bstek.bdf3.saas;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月16日
 */
public class OrganizationAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;

	private Object principal;
	
	public OrganizationAuthentication(Object principal) {
		this.principal = principal;
	}
	
	@Override
	public String getName() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {

	}

}
