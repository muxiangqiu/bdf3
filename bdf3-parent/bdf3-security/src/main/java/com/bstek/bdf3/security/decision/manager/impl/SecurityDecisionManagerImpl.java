package com.bstek.bdf3.security.decision.manager.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bstek.bdf3.security.decision.manager.SecurityDecisionManager;
import com.bstek.bdf3.security.domain.Resource;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月7日
 */
@Service
public class SecurityDecisionManagerImpl implements SecurityDecisionManager {

	@Autowired
	private List<SecurityMetadataSource> securityMetadataSources;
	
	@Autowired
	private AccessDecisionManager accessDecisionManager;
	
	@Override
	public boolean decide(Resource resource) {
		if (resource != null) {
			Collection<ConfigAttribute> attributes = findConfigAttributes(resource);
			Authentication authentication = getAuthentication();
			try {
				accessDecisionManager.decide(authentication, resource, attributes);
			} catch (AccessDeniedException e) {
				return false;
			} catch (InsufficientAuthenticationException e) {
				return false;
			}

		}
		return true;
	}

	protected Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public Collection<ConfigAttribute> findConfigAttributes(Resource resource) {
		Collection<ConfigAttribute> attributes = resource.getAttributes();
		if (CollectionUtils.isEmpty(attributes)) {
			for (SecurityMetadataSource securityMetadataSource : securityMetadataSources) {
				if (securityMetadataSource.supports(resource.getClass())) {
					attributes = securityMetadataSource.getAttributes(resource);
				}
			}
		} 
		return attributes;
	}
	
	

}
