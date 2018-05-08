package com.bstek.bdf3.security.service;

import org.malagu.linq.JpaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.security.orm.User;

/**
 * Spring Security的{@link org.springframework.security.core.userdetails.UserDetailsService}接口的默认实现
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月27日
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private GrantedAuthorityService grantedAuthorityService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			
			User user = JpaUtil.getOne(User.class, username);
			user.setAuthorities(grantedAuthorityService.getGrantedAuthorities(user));
			return user;
		} catch (Exception e) {
			throw new UsernameNotFoundException("Not Found");
		}
		
		
	}

}
