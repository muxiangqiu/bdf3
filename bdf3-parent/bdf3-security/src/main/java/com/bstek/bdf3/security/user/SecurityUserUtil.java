package com.bstek.bdf3.security.user;

import java.util.Collection;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


/**
 * 安全用户元数据工具类
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月26日
 */
@Component
public class SecurityUserUtil implements InitializingBean {
	
	@Autowired
	private UserMetadateService userMetadateService;
	
	private static UserMetadateService metadateService;
	
	public static UserMetadateService getUserMetadateService() {
		return metadateService;
	}
		
	public static String getNickname(UserDetails userDetails) {
		return metadateService.getFieldValue(userDetails, metadateService.getNicknameProp());
	}

	public static Class<? extends UserDetails> getSecurityUserType() {
		return metadateService.getSecurityUserType();
	}

	public static String getUsernameProp() {
		return metadateService.getUsernameProp();
	}

	public static String getNicknameProp() {
		return metadateService.getNicknameProp();
	}

	public static String getUsernameLabel() {
		return metadateService.getUsernameLabel();
	}

	public static String getNicknameLabel() {
		return metadateService.getNicknameLabel();
	}

	public static String getAuthoritiesProp() {
		return metadateService.getAuthoritiesProp();
	}

	public static String getPasswordLabel() {
		return metadateService.getPasswordLabel();
	}

	public static String getPasswordProp() {
		return metadateService.getPasswordProp();
	}
	
	public static String getAccountNonExpiredProp() {
		return metadateService.getAccountNonExpiredProp();
	}

	public static String getAccountNonExpiredLabel() {
		return metadateService.getAccountNonExpiredLabel();
	}

	public static String getAccountNonLockedProp() {
		return metadateService.getAccountNonLockedProp();
	}

	public static String getAccountNonLockedLabel() {
		return metadateService.getAccountNonLockedLabel();
	}

	public static String getCredentialsNonExpiredProp() {
		return metadateService.getCredentialsNonExpiredProp();
	}

	public static String getCredentialsNonExpiredLabel() {
		return metadateService.getCredentialsNonExpiredLabel();
	}

	public static String getEnabledProp() {
		return metadateService.getEnabledProp();
	}

	public static String getEnabledLabel() {
		return metadateService.getEnabledLabel();
	}
	
	public static String getModifyUserUrl() {
		return metadateService.getModifyUserUrl();
	}

	public static void  setAuthorities(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
		metadateService.setFieldValue(userDetails, metadateService.getAuthoritiesProp(), authorities);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		userMetadateService.init();
		metadateService = userMetadateService;
	}
	
	

}
