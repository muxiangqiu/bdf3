package com.bstek.bdf3.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import com.bstek.bdf3.security.access.provider.GrantedAuthorityProvider;

/**
 * 提供给开发人员扩展实现Spring Security的{@link org.springframework.security.core.userdetails.UserDetailsService}接口<br>
 * 用以替换系统默认的<br>
 * 主要提供登陆用户的授权信息的收集功能
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
public abstract class AbstractUserDetailsService implements UserDetailsService, InitializingBean {

	@Autowired
	private List<GrantedAuthorityProvider> providers;
	
	/**
	 * 获取用户的授权信息
	 * @param userDetails 用户信息
	 * @return 权限信息
	 */
	protected Collection<? extends GrantedAuthority> getGrantedAuthorities(UserDetails userDetails) {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (GrantedAuthorityProvider provider : providers) {
			Collection<? extends GrantedAuthority> list =  provider.provide(userDetails);
			if (CollectionUtils.isNotEmpty(list)) {
				grantedAuthorities.addAll(list);
			}
		}
		return grantedAuthorities;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(providers, "providers can not be empty");
		AnnotationAwareOrderComparator.sort(providers);
	}
	
	
	
	

}
