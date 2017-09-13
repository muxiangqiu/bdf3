package com.bstek.bdf3.saas.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.security.orm.OrganizationSupport;
import com.bstek.bdf3.security.orm.User;
import com.bstek.bdf3.security.service.GrantedAuthorityService;

/**
 * Spring Security的{@link org.springframework.security.core.userdetails.UserDetailsService}接口的默认实现
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月27日
 */
@Service("saas.userDetailsServiceImpl")
@Primary
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired  
	private HttpServletRequest request;
	
	@Autowired
	private GrantedAuthorityService grantedAuthorityService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {			
		try {
			Organization organization = loadOrganization();
			User user = JpaUtil.getOne(User.class, username);
			Assert.isInstanceOf(OrganizationSupport.class, user);
			((OrganizationSupport) user).setOrganization(organization);
			user.setAuthorities(grantedAuthorityService.getGrantedAuthorities(user));
			return user;
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		} finally {
			SaasUtils.popSecurityContext();
		}

	}

	private Organization loadOrganization() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Organization organization = null;
		if (auth == null) {
			String organizationId = request.getParameter("organization");
			organization = organizationService.get(organizationId);
			Assert.notNull(organization, "Organization is not exists.");
		} else {
			Object principal = auth.getPrincipal();
			Assert.isInstanceOf(OrganizationSupport.class, principal);
			organization = ((OrganizationSupport) principal).getOrganization();
		}
		SaasUtils.pushSecurityContext(organization);
		return organization;
	}

}
