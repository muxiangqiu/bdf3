package com.bstek.bdf3.security.ui.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.security.domain.Component;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.RoleGrantedAuthority;
import com.bstek.bdf3.security.domain.Url;
import com.bstek.bdf3.security.domain.User;
import com.bstek.bdf3.security.service.UrlService;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月9日
 */
@Service
@Transactional(readOnly = true)
public class PersonalCenterServiceImpl implements PersonalCenterService {

	@Autowired
	private UrlService urlService;
	
	@Override
	public User getUser(String username) {
		User user = JpaUtil.getOne(User.class, username);
		user.setPassword(null);
		try {
			user = EntityUtils.toEntity(user);
			EntityUtils.setValue(user, "roles", getRoles(username));
			EntityUtils.setValue(user, "urls", getUrls(username));
			EntityUtils.setValue(user, "permissions", getPermissions(username));
			EntityUtils.setState(user, EntityState.NONE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	@Override
	public List<Role> getRoles(String username) {
		return JpaUtil.linq(Role.class)
					.exists(RoleGrantedAuthority.class)
						.equalProperty("roleId", "id")
						.equal("actorId", username)
					.end()
					.list();
	}
	
	@Override
	public List<Url> getUrls(String username) {
		return urlService.getAccessibleUrlsByUsername(username);
	}
	
	@Override
	public List<Permission> getPermissions(String username) {
		List<Permission> permissions = JpaUtil.linq(Permission.class)
				.toEntity()
				.collect(Role.class, "roleId")
				.collect(Component.class, "resourceId")
				.equal("resourceType", Component.RESOURCE_TYPE)
				.exists(RoleGrantedAuthority.class)
					.equalProperty("roleId", "roleId")
					.equal("actorId", username)
				.end()
				.list();
		if (!permissions.isEmpty()) {
			Set<Component> components = JpaUtil.collect(permissions, "component");
			if (!components.isEmpty()) {
				List<Url> urls = JpaUtil.linq(Url.class).in("id", JpaUtil.collect(components, "urlId")).list();
				Map<String, Component> componentMap = JpaUtil.index(components);
				Map<String, Url> urlMap = JpaUtil.index(urls);
				for (Permission p : permissions) {
					Url url = urlMap.get(componentMap.get(p.getResourceId()).getUrlId());
					EntityUtils.setValue(p, "url", url);
				}

			}
		}
		return permissions;
	}

}
