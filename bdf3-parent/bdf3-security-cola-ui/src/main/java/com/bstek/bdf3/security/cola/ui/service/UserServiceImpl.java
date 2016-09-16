package com.bstek.bdf3.security.cola.ui.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.RoleGrantedAuthority;
import com.bstek.bdf3.security.user.SecurityUserUtil;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月20日
 */
@Service("cola.userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Override
	public List<Map<String, Object>> load(Pageable pageable, String searchKey) {
		List<Map<String, Object>> users = JpaUtil
				.linq(SecurityUserUtil.getSecurityUserType(), Map.class)
				.addIf(searchKey)
					.or()
						.like(SecurityUserUtil.getUsernameProp(), "%" + searchKey + "%")
						.like(SecurityUserUtil.getNicknameProp(), "%" + searchKey + "%")
						.exists(RoleGrantedAuthority.class)
							.equalProperty("actorId", SecurityUserUtil.getUsernameProp())
							.exists(Role.class)
								.equalProperty("id", "roleId")
								.like("name", "%" + searchKey + "%")
							.end()
						.end()
					.end()
				.endIf()
				.list(pageable);

		Set<String> usernames = JpaUtil.collect(users, SecurityUserUtil.getUsernameProp());
		if (!usernames.isEmpty()) {
			List<RoleGrantedAuthority> rgas = JpaUtil.linq(RoleGrantedAuthority.class).in("actorId", usernames).findAll();
			Set<Role> roleIds = JpaUtil.collect(rgas, "roleId");
			if (!roleIds.isEmpty()) {
				List<Map<String, Object>> roles = JpaUtil.linq(Role.class, Map.class).in("id", roleIds).findAll();
				Map<Object, List<RoleGrantedAuthority>> rgaMap = JpaUtil.classify(rgas, "actorId");
				Map<Object, Map<String, Object>> roleMap = JpaUtil.index(roles, "id");
				for (Map<String, Object> user : users) {
					if (rgaMap.get(user.get(SecurityUserUtil.getUsernameProp())) != null) {
						for (RoleGrantedAuthority rga : rgaMap.get(user.get(SecurityUserUtil.getUsernameProp()))) {
							Map<String, Object> role = roleMap.get(rga.getRoleId());
							role.put("rgaId", rga.getId());
							@SuppressWarnings("unchecked")
							List<Map<String, Object>> list =  (List<Map<String, Object>>) user.get("roles");
							if (list == null) {
								list = new ArrayList<Map<String,Object>>();
								user.put("roles", list);
							}
							list.add(role);
						}
					}
					
				}
			}
		}
		return users;
	}
	
	@Override
	public void remove(String id) {
		UserDetails user = JpaUtil.getOne(SecurityUserUtil.getSecurityUserType(), id);
		JpaUtil.remove(user);
		JpaUtil
			.lind(RoleGrantedAuthority.class)
			.equal("actorId", user.getUsername())
			.delete();
	}
	
	@Override
	@Transactional
	public void add(Map<String, Object> user) throws Exception {
		if (SecurityUserUtil.getPasswordProp() != null) {
			user.put("password", passwordEncoder.encode((CharSequence) user.get("password")));
		}
		UserDetails bean = SecurityUserUtil.getSecurityUserType().newInstance();
		BeanUtils.populate(bean, user);
		JpaUtil.persist(bean);
	}

	@Override
	public void modify(Map<String, Object> user) throws Exception {
		if (SecurityUserUtil.getPasswordProp() != null) {
			UserDetails userDetails = JpaUtil.getOne(SecurityUserUtil.getSecurityUserType(), (String)user.get("username"));
			if (!StringUtils.equals(userDetails.getPassword(), (String)user.get("password"))) {
				user.put("password", passwordEncoder.encode((CharSequence) user.get("password")));
			}
		}
		
		UserDetails bean = SecurityUserUtil.getSecurityUserType().newInstance();
		BeanUtils.populate(bean, user);
		JpaUtil.merge(bean);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean isExist(String username) {
		return JpaUtil.linq(SecurityUserUtil.getSecurityUserType()).equal(SecurityUserUtil.getUsernameProp(), username).exists();
	}
	
	@Override
	@SecurityCacheEvict
	public String addRoleGrantedAuthority(RoleGrantedAuthority roleGrantedAuthority) {
		roleGrantedAuthority.setId(UUID.randomUUID().toString());
		JpaUtil.persist(roleGrantedAuthority);
		return roleGrantedAuthority.getId();
		
	}
	
	@Override
	@SecurityCacheEvict
	public void removeRoleGrantedAuthority(String id) {
		RoleGrantedAuthority roleGrantedAuthority = JpaUtil.getOne(RoleGrantedAuthority.class, id);
		JpaUtil.remove(roleGrantedAuthority);
	}
}
