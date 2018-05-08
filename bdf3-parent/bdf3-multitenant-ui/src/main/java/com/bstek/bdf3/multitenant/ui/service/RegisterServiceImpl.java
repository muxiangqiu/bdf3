package com.bstek.bdf3.multitenant.ui.service;

import org.malagu.linq.JpaUtil;
import org.malagu.multitenant.MultitenantUtils;
import org.malagu.multitenant.domain.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private org.malagu.multitenant.service.OrganizationService organizationService;	
	@Override
	@Transactional
	public void registerOrganization(User user) {
		Organization organization = user.getOrganization();
		organizationService.register(organization);
		MultitenantUtils.doNonQuery(organization.getId(), () -> {
			userService.save(user);
		});
	}

	@Override
	public void registerUser(User user) {
		Organization organization = user.getOrganization();
		MultitenantUtils.doNonQuery(organization.getId(), () -> {
			userService.save(user);
		});
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
		return MultitenantUtils.doQuery(organizationId, () -> {
			return userService.isExist(username);
		});
	}

	@Override
	public Organization getOrganization(String organizationId) {
		return JpaUtil.getOne(Organization.class, organizationId);
	}
	

}
