package com.bstek.bdf3.security.ui.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicy;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.RoleGrantedAuthority;
import com.bstek.bdf3.security.domain.User;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月2日
 */
@Service
@Transactional(readOnly = true)
public class RoleAssignmentServiceImpl implements RoleAssignmentService {
	
	@Override
	public void loadUsersWithout(Page<User> page, Criteria criteria, String roleId) {
		JpaUtil
			.linq(User.class)
			.where(criteria)
			.notExists(RoleGrantedAuthority.class)
				.equalProperty("actorId", "username")
				.equal("roleId", roleId)
			.end()
			.paging(page);;
	}
	
	@Override
	public void loadUsersWithin(Page<User> page, Criteria criteria, String roleId) {
		JpaUtil
			.linq(User.class)
			.where(criteria)
			.exists(RoleGrantedAuthority.class)
				.equalProperty("actorId", "username")
				.equal("roleId", roleId)
			.end()
			.paging(page);;
	}
	
	
	@Override
	@Transactional
	public void save(List<Role> roles) {		
		JpaUtil.save(roles, new SmartSavePolicy() {

			@Override
			public void apply(SaveContext context) {
				if (context.getEntity() instanceof Role) {
					Role role = context.getEntity();
					List<GrantedAuthority> authorities = role.getAuthorities();
					if (authorities != null) {
						for (GrantedAuthority authority : authorities) {
							if (authority instanceof RoleGrantedAuthority) {
								RoleGrantedAuthority a = (RoleGrantedAuthority) authority;
								EntityState state = EntityUtils.getState(authority);
								if (EntityState.DELETED.equals(state)) {
									JpaUtil.lind(RoleGrantedAuthority.class)
										.equal("actorId", a.getActorId())
										.equal("roleId", a.getRoleId())
										.delete();

								} else {
									JpaUtil.save(authority);
								}
							}
							
						}
					}
					EntityState state = EntityUtils.getState(role);
					if (EntityState.DELETED.equals(state)) {
						JpaUtil
							.lind(Permission.class)
							.equal("roleId", role.getId())
							.delete();
						
						JpaUtil
							.lind(RoleGrantedAuthority.class)
							.equal("roleId", role.getId())
							.delete();
					}
				}
				super.apply(context);
				
			}
			
		});
	}
	
}
