package com.bstek.bdf3.security.saas.cola.ui.service;

import java.util.Map;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月15日
 */
public interface RegisterService {

	void registerUser(Map<String, Object> info) throws Exception;

	void registerOrganization(Map<String, Object> info) throws Exception;

	boolean isExistOrganization(String organizationId);

	boolean isExistUser(String organizationId, String username);
}
