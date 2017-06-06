package com.bstek.bdf3.security.orm;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户信息
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月25日
 */
@Entity
@Table(name = "BDF3_USER")
public class User implements UserDetails, OrganizationSupport {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "USERNAME_", length = 64)
	private String username;
	
	@Column(name = "NICKNAME_", length = 64)
	private String nickname;
	
	@Column(name = "PASSWORD_", length = 125)
	private String password;
	
	@Column(name = "ADMINISTRATOR_")
	private boolean administrator;
	
	@Column(name = "ACCOUNT_NON_EXPIRED_")
	private boolean accountNonExpired = true;
	
	@Column(name = "ACCOUNT_NON_LOCKED_")
	private boolean accountNonLocked = true;
	
	@Column(name = "CREDENTIALS_NON_EXPIRED_")
	private boolean credentialsNonExpired = true;
	
	@Column(name = "ENABLED_")
	private boolean enabled = true;
	
	@Transient
	private Object organization;
	
	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * 昵称
	 * @return nickname
	 */
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getOrganization() {
		return (T) organization;
	}

	@Override
	public <T> void setOrganization(T organization) {
		this.organization = organization;
		
	}	
	
	

}
