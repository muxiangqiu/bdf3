package com.bstek.bdf3.security.cola.ui.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月20日
 */
public interface RoleService {

	List<Role> load(Pageable pageable, String searchKey);

	List<Url> loadUrls(String roleId);

	void remove(String id);

	String add(Role role);

	void modify(Role role);

	List<Role> load(String username);

}
