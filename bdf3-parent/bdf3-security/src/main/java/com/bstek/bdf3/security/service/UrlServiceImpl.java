package com.bstek.bdf3.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.Constants;
import com.bstek.bdf3.security.decision.manager.SecurityDecisionManager;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Url;
import com.bstek.bdf3.security.user.SecurityUserUtil;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月30日
 */
@Service
@Transactional(readOnly = true)
public class UrlServiceImpl implements UrlService {
	@Autowired
	private UrlServiceCache urlServiceCache;
	
	@Autowired
	private SecurityDecisionManager securityDecisionManager;
	
	@Autowired
	private GrantedAuthorityService grantedAuthorityService;
	
	@Override
	public List<Url> findAll() {
		List<Url> urls = JpaUtil.linq(Url.class).list();
		List<Permission> permissions = JpaUtil.linq(Permission.class).equal("resourceType", Url.RESOURCE_TYPE).list();
		if (!permissions.isEmpty()) {
			Map<String, Url> urlMap = JpaUtil.index(urls);
			for (Permission permission : permissions) {
				Url url = urlMap.get(permission.getResourceId());
				List<ConfigAttribute> configAttributes = url.getAttributes();
				if (configAttributes == null) {
					configAttributes = new ArrayList<ConfigAttribute>();
					url.setAttributes(configAttributes);
				}
				configAttributes.add(permission);
			}
		}
		return urls;
	}
	
	public List<Url> findTree() {
		return urlServiceCache.findTree();
	}
	
	@Override
	@Cacheable(cacheNames = Constants.URL_TREE_BY_USRNAME_CACHE_KEY, keyGenerator = Constants.KEY_GENERATOR_BEAN_NAME)
	public List<Url> findTreeByUsername(String username) {
		List<Url> urls = urlServiceCache.findTree();
		List<Url> result = new ArrayList<Url>();
		rebuildLoginUserGrantedAuthorities();
		for (Url url : urls) {
			if (decide(null, url)) {
				result.add(url);
			}
		}
		return result;
	}
	
	@Override
	public List<Url> getAccessibleUrlsByUsername(String username) {
		List<Url> urls = urlServiceCache.findTree();
		List<Url> result = new ArrayList<Url>();
		for (Url url : urls) {
			if (decide(username, url)) {
				result.add(url);
			}
		}
		return result;
	}
	
	private void rebuildLoginUserGrantedAuthorities() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityUserUtil.setAuthorities(userDetails, grantedAuthorityService.getGrantedAuthorities(userDetails));
	}

	private boolean decide(String username, Url url) {
		if (!url.isNavigable()) {
			return false;
		}
		if (securityDecisionManager.decide(username, url)) {
			List<Url> children = url.getChildren();
			List<Url> newChildren = new ArrayList<Url>();
			url.setChildren(newChildren);
			if (!CollectionUtils.isEmpty(children)) {
				for (Url child : children) {
					if (decide(username, child)) {
						newChildren.add(child);
					}
				}
			}
			return true;
		}
		return false;
		
		
	}


}
