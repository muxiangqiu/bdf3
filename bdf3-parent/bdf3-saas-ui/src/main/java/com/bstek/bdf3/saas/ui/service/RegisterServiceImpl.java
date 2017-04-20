package com.bstek.bdf3.saas.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.OrganizationService;
import com.bstek.bdf3.security.orm.User;
import com.bstek.bdf3.security.ui.service.UserService;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月15日
 */
@Service
@Transactional(readOnly = true)
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Override
	@Transactional
	public void registerOrganization(User user) {
		Organization organization = user.getOrganization();
		organizationService.register(organization);
		try {
			SaasUtils.setSecurityContext(organization.getId());
			userService.save(user);
		} finally {
			SaasUtils.clearSecurityContext();
		}
	}

	@Override
	public void registerUser(User user) {
		Organization organization = user.getOrganization();
		try {
			SaasUtils.setSecurityContext(organization.getId());
			userService.save(user);
		} finally {
			SaasUtils.clearSecurityContext();
		}
	}
	
	@Override
	public boolean isExistOrganization(String organizationId) {
		return JpaUtil.linq(Organization.class).equal("id", organizationId).exists();
	}
	
	@Override
	public boolean isExistUser(String organizationId, String username) {
		if (!isExistOrganization(organizationId)) {
			return false;
		}
		try {
			SaasUtils.setSecurityContext(organizationId);
			return userService.isExist(username);
		} catch(Exception e) {
			return false;
		} finally {
			SaasUtils.clearSecurityContext();
		}
	}

	@Override
	public Organization getOrganization(String organizationId) {
		return JpaUtil.getOne(Organization.class, organizationId);
	}
	

}
