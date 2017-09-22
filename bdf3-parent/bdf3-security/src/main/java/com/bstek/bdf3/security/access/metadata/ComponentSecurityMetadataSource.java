package com.bstek.bdf3.security.access.metadata;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;

import com.bstek.bdf3.security.access.provider.ComponentConfigAttributeProvider;
import com.bstek.bdf3.security.orm.Component;


/**
 * 组件安全元数据源
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月23日
 */
@org.springframework.stereotype.Component
public class ComponentSecurityMetadataSource implements SecurityMetadataSource {


	
	@Autowired
	private List<ComponentConfigAttributeProvider> providers;
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		return getComponentMap().get(object.toString());
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<String, Collection<ConfigAttribute>> entry : getComponentMap()
				.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}
	
	public Map<String, Collection<ConfigAttribute>> getComponentMap() {
		AnnotationAwareOrderComparator.sort(providers);
		Map<String, Collection<ConfigAttribute>> componentMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();
		for (ComponentConfigAttributeProvider provider : providers) {
			Map<String, Collection<ConfigAttribute>> map = provider.provide();
			if (MapUtils.isNotEmpty(map)) {
				componentMap.putAll(map);
			}
		}
		return componentMap;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Component.class.isAssignableFrom(clazz);
	}

}
