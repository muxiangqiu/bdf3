package com.bstek.bdf3.security.cola.ui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.access.provider.ComponentProvider;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.decision.manager.SecurityDecisionManager;
import com.bstek.bdf3.security.domain.Component;
import com.bstek.bdf3.security.domain.ComponentType;
import com.bstek.bdf3.security.domain.Permission;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月3日
 */
@Service("cola.componentService")
public class ComponentServiceImpl implements ComponentService {
	
	@Autowired
	private ComponentProvider componentProvider;
	
	@Autowired
	private SecurityDecisionManager securityDecisionManager;

	@Override
	public List<Component> load(String roleId, String urlId) {
		List<Component> components = JpaUtil.linq(Component.class).equal("urlId", urlId).list();
		if (!components.isEmpty()) {
			Set<String> ids = JpaUtil.collectId(components);
			List<Permission> permissions = JpaUtil.linq(Permission.class)
				.equal("roleId", roleId)
				.equal("resourceType", Component.RESOURCE_TYPE)
				.in("resourceId", ids)
				.list();
			
			Map<String, Permission> permissionMap = JpaUtil.index(permissions, "resourceId");

			for (Component component : components) {
				component.setComponentType(ComponentType.ReadWrite);
				Permission permission = permissionMap.get(component.getId());
				if (permission != null) {
					if (StringUtils.endsWith(permission.getAttribute(), ComponentType.Read.name())) {
						component.setComponentType(ComponentType.Read);
					} 
					component.setAuthorized(true);
					component.setConfigAttributeId(permission.getId());
				}
			}
		}
		return components;
	}

	@Override
	@SecurityCacheEvict
	public void remove(String id) {
		Component component = JpaUtil.getOne(Component.class, id);
		JpaUtil.remove(component);
		JpaUtil.lind(Permission.class)
			.equal("resourceType", Component.RESOURCE_TYPE)
			.equal("resourceId", id)
			.delete();
	}

	@Override
	public String add(Component component) {
		component.setId(UUID.randomUUID().toString());
		JpaUtil.persist(component);
		return component.getId();
	}

	@Override
	@SecurityCacheEvict
	public void modify(Component component) {
		JpaUtil.merge(component);
	}
	
	@Override
	public List<Component> loadComponentsByPath(String path) {
		List<Component> result = new ArrayList<Component>();
		Map<String, Collection<Component>> componentMap = componentProvider.provide();
		Collection<Component> components = componentMap.get(path);
		if (components != null) {
			for (Component c : components) {
			Component component = new Component();
			component.setComponentId(c.getComponentId());
			component.setPath(c.getPath());
			component.setAttributes(c.getAttributes());
			component.setComponentType(ComponentType.ReadWrite);
			if (securityDecisionManager.decide(component)) {
				component.setAuthorized(true);
			} else {
				component.setComponentType(ComponentType.Read);
				if (securityDecisionManager.decide(component)) {
					component.setAuthorized(true);
				}
			}
			component.setAttributes(null);
			result.add(component);
		}
		}
		
		return result;
	}

}
