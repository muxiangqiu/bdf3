package com.bstek.bdf3.autoconfigure.multitenant;

import javax.servlet.http.HttpServletRequest;

import org.malagu.linq.JpaUtil;
import org.malagu.multitenant.MultitenantUtils;
import org.malagu.multitenant.domain.Organization;
import org.malagu.multitenant.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstek.bdf3.security.ContextUtils;
import com.bstek.bdf3.security.orm.User;
import com.bstek.bdf3.security.service.GrantedAuthorityService;

/**
 * Spring Security的{@link org.springframework.security.core.userdetails.UserDetailsService}接口的默认实现
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月27日
 */
@Transactional(readOnly = true)
public class MultitenantUserDetailsService implements UserDetailsService {
	
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
			return MultitenantUtils.doQuery(organization.getId(), () ->  {
				User user = JpaUtil.getOne(User.class, username);
				user.setOrganization(organization);
				user.setAuthorities(grantedAuthorityService.getGrantedAuthorities(user));
				return user;
			});
		} catch (Exception e) {
			throw new UsernameNotFoundException(e.getMessage());
		}

	}

	private Organization loadOrganization() {
		User user = ContextUtils.getLoginUser();
		Organization organization = null;
		if (user == null) {
			String organizationId = request.getParameter("organization");
			organization = organizationService.get(organizationId);
			Assert.notNull(organization, "Organization is not exists.");
		} else {
			organization = user.getOrganization();
		}
		return organization;
	}

}
