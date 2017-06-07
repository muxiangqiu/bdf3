package com.bstek.bdf3.security.saas.cola.ui.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.SaasUtils;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.OrganizationService;
import com.bstek.bdf3.security.cola.ui.service.UserService;

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
	public void registerOrganization(Map<String, Object> info) throws Exception {
		Organization organization = new Organization();
		organization.setId((String)info.get("organizationId"));
		organization.setName((String)info.get("organizationName"));
		organizationService.register(organization);
		try {
			SaasUtils.pushSecurityContext(organization);
			userService.add(info);
		} finally {
			SaasUtils.popSecurityContext();
		}
	}

	@Override
	public void registerUser(Map<String, Object> info) throws Exception {
		try {
			SaasUtils.pushSecurityContext((String)info.get("organizationId"));
			userService.add(info);
		} finally {
			SaasUtils.popSecurityContext();
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
			SaasUtils.pushSecurityContext(organizationId);
			return userService.isExist(username);
		} catch(Exception e) {
			return false;
		} finally {
			SaasUtils.popSecurityContext();
		}
	}
	

}
