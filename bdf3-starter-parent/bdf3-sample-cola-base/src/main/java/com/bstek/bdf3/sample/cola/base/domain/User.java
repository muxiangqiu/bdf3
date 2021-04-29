package com.bstek.bdf3.sample.cola.base.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 用户信息
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月25日
 */
@Entity
@Table(name = "BASE_USER")
public class User {

	
	@Id
	@Column(name = "USERNAME_", length = 64)
	private String username;
	
	@Column(name = "NICKNAME_", length = 64)
	private String nickname;
	
	@Column(name = "PASSWORD_", length = 125)
	private String password;
	
	@Column(name = "ACCOUNT_NON_EXPIRED_")
	private boolean accountNonExpired = true;
	
	@Column(name = "ACCOUNT_NON_LOCKED_")
	private boolean accountNonLocked = true;
	
	@Column(name = "CREDENTIALS_NON_EXPIRED_")
	private boolean credentialsNonExpired = true;
	
	@Column(name = "ENABLED_")
	private boolean enabled = true;


	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	
	

}
