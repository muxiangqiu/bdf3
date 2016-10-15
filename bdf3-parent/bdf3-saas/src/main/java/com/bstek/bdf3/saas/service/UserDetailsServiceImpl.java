package com.bstek.bdf3.saas.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.security.domain.OrganizationSupport;
import com.bstek.bdf3.security.service.AbstractUserDetailsService;
import com.bstek.bdf3.security.user.SecurityUserUtil;

/**
 * Spring Security的{@link org.springframework.security.core.userdetails.UserDetailsService}接口的默认实现
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月27日
 */
@Service("saas.userDetailsServiceImpl")
@Primary
@Transactional(readOnly = true)
public class UserDetailsServiceImpl extends AbstractUserDetailsService {
	
	@Autowired  
	private HttpServletRequest request;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {			
		try {
			Organization organization = loadOrganization();
			UserDetails userDetails = JpaUtil.getOne(SecurityUserUtil.getSecurityUserType(), username);
			Assert.isInstanceOf(OrganizationSupport.class, userDetails);
			((OrganizationSupport) userDetails).setOrganization(organization);
			SecurityUserUtil.setAuthorities(userDetails, getGrantedAuthorities(userDetails));
			return userDetails;
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		} finally {
			SaasUtils.clearSecurityContext();
		}

	}

	private Organization loadOrganization() {
		String organizationId = request.getParameter("organization");
		Organization organization = organizationService.get(organizationId);
		Assert.notNull(organization);
		SaasUtils.setSecurityContext(organizationId);
		return organization;
	}

}
