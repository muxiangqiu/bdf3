package com.bstek.bdf3.security.cola.ui.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.bstek.bdf3.security.domain.RoleGrantedAuthority;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月20日
 */
public interface UserService {

	List<Map<String, Object>> load(Pageable pageable, String searchKey);

	void remove(String id);

	void add(Map<String, Object> user) throws Exception;

	void modify(Map<String, Object> user) throws Exception;

	String addRoleGrantedAuthority(RoleGrantedAuthority roleGrantedAuthority);

	void removeRoleGrantedAuthority(String id);

	boolean isExist(String username);
	
	

}
