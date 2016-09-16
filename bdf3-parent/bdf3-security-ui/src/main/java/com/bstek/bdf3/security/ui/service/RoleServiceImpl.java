package com.bstek.bdf3.security.ui.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.security.Constants;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.RoleGrantedAuthority;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月12日
 */
@Service("ui.roleService")
public class RoleServiceImpl implements RoleService {

	private SavePolicy roleSavePolicy = new RoleSavePolicy();
	
	@Override
	public void load(Page<Role>page, Criteria criteria) {
		JpaUtil.linq(Role.class).where(criteria).findAll(page);
	}
	
	@Override
	@CacheEvict({
		Constants.URL_TREE_CACHE_KEY, 
		Constants.URL_TREE_BY_USRNAME_CACHE_KEY, 
		Constants.REQUEST_MAP_CACHE_KEY,
		Constants.URL_ATTRIBUTE_BY_TARGET_CACHE_KEY,
		Constants.COMPONENT_MAP_CACHE_KEY,
		Constants.COMPONENT_ATTRIBUTE_MAP_CACHE_KEY,
		Constants.COMPONENT_ATTRIBUTE_BY_TARGET_CACHE_KEY})
	public void save(List<Role> roles) {
		JpaUtil.save(roles, roleSavePolicy);
	}
	
	class RoleSavePolicy extends SmartSavePolicyAdapter {

		@Override
		public void beforeDelete(SaveContext context) {
			Role role = context.getEntity();
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
}
