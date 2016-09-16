package com.bstek.bdf3.security.ui.service;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.lin.Linq;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.SavePolicy;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.security.Constants;
import com.bstek.bdf3.security.domain.Component;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月12日
 */
@Service("ui.roleUrlService")
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
				.findAll();
	}
	
	@Override
	@CacheEvict(cacheNames = {
		Constants.URL_TREE_CACHE_KEY, 
		Constants.URL_TREE_BY_USRNAME_CACHE_KEY, 
		Constants.REQUEST_MAP_CACHE_KEY,
		Constants.URL_ATTRIBUTE_BY_TARGET_CACHE_KEY,
		Constants.COMPONENT_MAP_CACHE_KEY,
		Constants.COMPONENT_ATTRIBUTE_MAP_CACHE_KEY,
		Constants.COMPONENT_ATTRIBUTE_BY_TARGET_CACHE_KEY}, allEntries = true)
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
				.findAll();
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
