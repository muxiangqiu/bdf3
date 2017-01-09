package com.bstek.bdf3.security.ui.service;

import java.util.List;

import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.Url;
import com.bstek.bdf3.security.domain.User;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月9日
 */
public interface PersonalCenterService {

	User getUser(String username);

	List<Role> getRoles(String username);

	List<Url> getUrls(String username);

	List<Permission> getPermissions(String username);

}
