package com.bstek.bdf3.security.cola.ui.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.RoleGrantedAuthority;
import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月20日
 */
@Service("cola.roleService")
public class RoleServiceImpl implements RoleService {

	@Override
	public List<Role> load(Pageable pageable, String searchKey) {
		return JpaUtil
				.linq(Role.class)
				.addIf(searchKey)
					.or()
						.like("name", "%" + searchKey + "%")
						.like("description", "%" + searchKey + "%")
				.endIf()
				.list(pageable);
	}
	
	@Override
	public List<Role> load(String username) {
		return JpaUtil
				.linq(Role.class)
				.exists(RoleGrantedAuthority.class)
					.equalProperty("roleId", "id")
					.equal("actorId", username)
				.list();
	}
	
	@Override
	public List<Url> loadUrls(String roleId) {
		return JpaUtil
				.linq(Url.class)
				.exists(Permission.class)
					.equalProperty("resourceId", "id")
					.equal("roleId", roleId)
				.list();
	}
	
	@Override
	@SecurityCacheEvict		
	public void remove(String id) {
		Role role = JpaUtil.getOne(Role.class, id);
		JpaUtil.remove(role);
		JpaUtil
			.lind(Permission.class)
			.equal("roleId", role.getId())
			.delete();
	
		JpaUtil
			.lind(RoleGrantedAuthority.class)
			.equal("roleId", role.getId())
			.delete();
	}
	
	@Override
	@SecurityCacheEvict
	public String add(Role role) {
		role.setId(UUID.randomUUID().toString());
		JpaUtil.persist(role);
		return role.getId();
	}

	@Override
	@SecurityCacheEvict
	public void modify(Role role) {
		JpaUtil.merge(role);
	}
	

}
