package com.bstek.bdf3.security.ui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicy;
import com.bstek.bdf3.security.Constants;
import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月11日
 */
@Service("ui.urlService")
public class UrlServiceImpl implements UrlService {
	
	@Override
	public List<Url> load() {
		List<Url> result = new ArrayList<Url>();
		Map<String, List<Url>> childrenMap = new HashMap<String, List<Url>>();
		List<Url> urls = JpaUtil
				.linq(Url.class)
				.asc("order")
				.findAll();
		for (Url url : urls) {
			
			if (childrenMap.containsKey(url.getId())) {
				url.setChildren(childrenMap.get(url.getId()));
			} else {
				url.setChildren(new ArrayList<Url>());
				childrenMap.put(url.getId(), url.getChildren());
			}

			if (url.getParentId() == null) {
				result.add(url);
			} else {
				List<Url> children;
				if (childrenMap.containsKey(url.getParentId())) {
					children = childrenMap.get(url.getParentId());
				} else {
					children = new ArrayList<Url>();
					childrenMap.put(url.getParentId(), children);
				}
				children.add(url);
			}
		}
		return result;
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
	public void save(List<Url> urls) {
		JpaUtil.save(urls, new SmartSavePolicy() {
			
			@Override
			public void apply(SaveContext context) {
				Url url = context.getEntity();
				if (url.getParentId() == null) {
					Url parent = context.getParent();
					if (parent != null) {
						url.setParentId(parent.getId());
					}
				}
				
				super.apply(context);
				
				
			}
		});
	}
}

