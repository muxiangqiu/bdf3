package com.bstek.bdf3.security.user;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.security.orm.User;
import com.bstek.bdf3.security.user.annotation.AccountNonExpired;
import com.bstek.bdf3.security.user.annotation.AccountNonLocked;
import com.bstek.bdf3.security.user.annotation.Authorities;
import com.bstek.bdf3.security.user.annotation.CredentialsNonExpired;
import com.bstek.bdf3.security.user.annotation.Enabled;
import com.bstek.bdf3.security.user.annotation.Nickname;
import com.bstek.bdf3.security.user.annotation.Password;
import com.bstek.bdf3.security.user.annotation.SecurityUser;
import com.bstek.bdf3.security.user.annotation.Username;

/**
 * 默认用户元数据服务类
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月26日
 */
@Service
public class UserMetadataServiceImpl implements UserMetadateService {

	private Class<? extends UserDetails> securityUserType = User.class;
	private String usernameProp = "username";
	private String nicknameProp = "nickname";
	private String usernameLabel = "用户名";
	private String nicknameLabel = "昵称";
	private String authoritiesProp = "authorities";
	private String passwordLabel = "密码";
	private String passwordProp = "password";
	private String accountNonExpiredProp = "accountNonExpired";
	private String accountNonExpiredLabel = "账户未过期";
	private String accountNonLockedProp = "accountNonLocked";
	private String accountNonLockedLabel = "账户未锁定";
	private String credentialsNonExpiredProp = "credentialsNonExpired";
	private String credentialsNonExpiredLabel = "证书未过期";
	private String enabledProp = "enabled";
	private String enabledLabel = "可用";
	private String modifyUserUrl = null;

	
	@Autowired(required = false)
	private UserDetails userDetails;
	
	@Override
	public Class<? extends UserDetails> getSecurityUserType() {
		return securityUserType;
	}

	@Override
	public String getUsernameProp() {
		return usernameProp;
	}

	@Override
	public String getNicknameProp() {
		return nicknameProp;
	}

	@Override
	public String getUsernameLabel() {
		return usernameLabel;
	}

	@Override
	public String getNicknameLabel() {
		return nicknameLabel;
	}

	@Override
	public String getAuthoritiesProp() {
		return authoritiesProp;
	}

	@Override
	public String getPasswordLabel() {
		return passwordLabel;
	}

	@Override
	public String getPasswordProp() {
		return passwordProp;
	}
	
	@Override
	public String getAccountNonExpiredProp() {
		return accountNonExpiredProp;
	}

	@Override
	public String getAccountNonExpiredLabel() {
		return accountNonExpiredLabel;
	}

	@Override
	public String getAccountNonLockedProp() {
		return accountNonLockedProp;
	}

	@Override
	public String getAccountNonLockedLabel() {
		return accountNonLockedLabel;
	}

	@Override
	public String getCredentialsNonExpiredProp() {
		return credentialsNonExpiredProp;
	}

	@Override
	public String getCredentialsNonExpiredLabel() {
		return credentialsNonExpiredLabel;
	}

	@Override
	public String getEnabledProp() {
		return enabledProp;
	}

	@Override
	public String getEnabledLabel() {
		return enabledLabel;
	}
	
	@Override
	public String getModifyUserUrl() {
		return modifyUserUrl;
	}
	
	@Override
	public void setFieldValue(UserDetails userDetails, String prop, Object value) {
		if (userDetails !=null) {
			Field field = ReflectionUtils.findField(securityUserType, prop);
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field , userDetails, value);
		}
	}
	
	@Override
	public String getFieldValue(UserDetails userDetails, String prop) {
		if (userDetails !=null) {
			Field field = ReflectionUtils.findField(securityUserType, prop);
			ReflectionUtils.makeAccessible(field);
			try {
				return (String) field.get(userDetails);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void init() {
		if (userDetails != null) {
			passwordLabel = null;
			passwordProp = null;
			accountNonExpiredProp = null;
			accountNonExpiredLabel = null;
			accountNonLockedProp = null;
			accountNonLockedLabel = null;
			credentialsNonExpiredProp = null;
			credentialsNonExpiredLabel = null;
			enabledProp = null;
			enabledLabel = null;

			securityUserType = userDetails.getClass();
			SecurityUser securityUser = AnnotationUtils.getAnnotation(securityUserType, SecurityUser.class);
			if (securityUser != null) {
				modifyUserUrl = StringUtils.isEmpty(securityUser.modifyUserUrl()) ? null : securityUser.modifyUserUrl();
			}
			ReflectionUtils.doWithFields(securityUserType, new FieldCallback() {
				
				@Override
				public void doWith(Field field) throws IllegalArgumentException,
						IllegalAccessException {
					Username username = AnnotationUtils.getAnnotation(field, Username.class);
					if (username != null) {
						usernameProp = field.getName();
						usernameLabel = username.label();
					}
					
					Nickname nickname = AnnotationUtils.getAnnotation(field, Nickname.class);
					if (nickname != null) {
						nicknameProp = field.getName();
						nicknameLabel = nickname.label();
					}
					
					Password password = AnnotationUtils.getAnnotation(field, Password.class);
					if (password != null) {
						passwordProp = field.getName();
						passwordLabel = password.label();
					}
					
					AccountNonExpired accountNonExpired = AnnotationUtils.getAnnotation(field, AccountNonExpired.class);
					if (accountNonExpired != null) {
						accountNonExpiredProp = field.getName();
						accountNonExpiredLabel = password.label();
					}
					
					AccountNonLocked accountNonLocked = AnnotationUtils.getAnnotation(field, AccountNonLocked.class);
					if (accountNonLocked != null) {
						accountNonLockedProp = field.getName();
						accountNonLockedLabel = password.label();
					}
					
					CredentialsNonExpired credentialsNonExpired = AnnotationUtils.getAnnotation(field, CredentialsNonExpired.class);
					if (credentialsNonExpired != null) {
						accountNonExpiredProp = field.getName();
						accountNonExpiredLabel = credentialsNonExpired.label();
					}
					
					Enabled enabled = AnnotationUtils.getAnnotation(field, Enabled.class);
					if (enabled != null) {
						enabledProp = field.getName();
						enabledLabel = enabled.label();
					}
					
					Authorities authorities = AnnotationUtils.getAnnotation(field, Authorities.class);
					if (authorities != null) {
						authoritiesProp = field.getName();
					}
				}
			});
			
		}
		
	}

}
