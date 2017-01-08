package com.bstek.bdf3.security.ui.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.RoleGrantedAuthority;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月12日
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	private SavePolicy roleSavePolicy = new RoleSavePolicy();
	
	@Override
	public void load(Page<Role>page, Criteria criteria) {
		JpaUtil.linq(Role.class).where(criteria).paging(page);
	}
	
	@Override
	@SecurityCacheEvict
	@Transactional
	public void save(List<Role> roles) {
		JpaUtil.save(roles, roleSavePolicy);
	}
	
	class RoleSavePolicy extends SmartSavePolicyAdapter {

		@Override
		public void beforeDelete(SaveContext context) {
			if (context.getEntity() instanceof Role) {
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
}
