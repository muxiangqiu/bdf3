package com.bstek.bdf3.sample.replaceuser.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bstek.bdf3.security.user.annotation.Authorities;
import com.bstek.bdf3.security.user.annotation.Nickname;
import com.bstek.bdf3.security.user.annotation.Password;
import com.bstek.bdf3.security.user.annotation.SecurityUser;
import com.bstek.bdf3.security.user.annotation.Username;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月26日
 */
@Entity
//必须加
@SecurityUser(
		modifyUserUrl = "modify-user"//可以不定义，除非你要定制用户的修改界面,其中用户主键可以通过url参数username取得
)
public class TestUser implements UserDetails/*必须实现 UserDetails接口*/{


	private static final long serialVersionUID = 1L;
	
	@Id
	@Username(label = "用户名")//必须加
	private String id;
	
	@Nickname(label = "昵称")//必须加
	private String name;
	
	@Password(label = "密码")//可以不加
	private String password;
	
	private String email;
	
	@Transient
	@Authorities//必须加，授权信息
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
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	

}
