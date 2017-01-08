package com.bstek.bdf3.saas.ui.service;

import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.security.domain.User;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月15日
 */
public interface RegisterService {

	boolean isExistOrganization(String organizationId);

	boolean isExistUser(String organizationId, String username);

	void registerOrganization(User user);

	void registerUser(User user);

	Organization getOrganization(String organizationId);
}
