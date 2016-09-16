package com.bstek.bdf3.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.user.SecurityUserUtil;

/**
 * Spring Security的{@link org.springframework.security.core.userdetails.UserDetailsService}接口的默认实现
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月27日
 */
@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl extends AbstractUserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			
			UserDetails userDetails = JpaUtil.getOne(SecurityUserUtil.getSecurityUserType(), username);
			SecurityUserUtil.setAuthorities(userDetails, getGrantedAuthorities(userDetails));
			return userDetails;
		} catch (Exception e) {
			throw new UsernameNotFoundException("Not Found");
		}
		
		
	}

}
