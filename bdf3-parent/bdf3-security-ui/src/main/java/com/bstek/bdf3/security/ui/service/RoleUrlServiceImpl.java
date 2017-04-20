package com.bstek.bdf3.security.ui.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.Permission;
import com.bstek.bdf3.security.orm.Url;




/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月12日
 */
@Service
@Transactional(readOnly = true)
public class RoleUrlServiceImpl implements RoleUrlService {

	private SavePolicy permissionSavePolicy = new PermissionSavePolicy();

	
	@Override
	public List<Permission> load(String roleId) {
		return JpaUtil
				.linq(Permission.class)
				.toEntity()
				.equal("roleId", roleId)
				.equal("resourceType", Url.RESOURCE_TYPE)
				.collect(Url.class, "resourceId")
				.list();
	}
	
	@Override
	@SecurityCacheEvict
	@Transactional
	public void save(List<Permission> permissions) {
		JpaUtil.save(permissions, permissionSavePolicy);
	}
	
	class PermissionSavePolicy extends SmartSavePolicyAdapter {

		@Override
		public void beforeDelete(SaveContext context) {
			Permission permission = context.getEntity();
			Linq linq = JpaUtil.linq(Permission.class);
			linq
				.collect("resourceId")
				.equal("resourceType", Component.RESOURCE_TYPE)
				.exists(Component.class)
					.equalProperty("id", "resourceId")
					.equal("urlId", permission.getResourceId())
				.list();
			Set<String> ids = linq.getLinqContext().getSet("resourceId");
			if (ids != null) {
				JpaUtil
					.lind(Permission.class)
					.in("resourceId", ids)
					.delete();
				JpaUtil
					.lind(Component.class)
					.in("id", ids)
					.delete();
			}
			
		}

	}

}
