package com.bstek.bdf3.security.user;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户元数据服务接口
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月27日
 */
public interface UserMetadateService {

	Class<? extends UserDetails> getSecurityUserType();

	String getUsernameProp();

	String getNicknameProp();

	String getUsernameLabel();

	String getNicknameLabel();

	String getAuthoritiesProp();

	String getPasswordLabel();

	String getPasswordProp();

	String getAccountNonExpiredProp();

	String getAccountNonExpiredLabel();

	String getAccountNonLockedProp();

	String getAccountNonLockedLabel();

	String getCredentialsNonExpiredProp();

	String getCredentialsNonExpiredLabel();

	String getEnabledProp();

	String getEnabledLabel();

	/**
	 * 用户元数据初始化方法
	 */
	void init();

	void setFieldValue(UserDetails userDetails, String prop, Object value);

	String getFieldValue(UserDetails userDetails, String prop);

	String getModifyUserUrl();

}