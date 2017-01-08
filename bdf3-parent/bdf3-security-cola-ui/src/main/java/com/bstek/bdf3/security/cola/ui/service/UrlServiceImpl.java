package com.bstek.bdf3.security.cola.ui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.cache.SecurityCacheEvict;
import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月20日
 */
@Service("cola.urlService")
public class UrlServiceImpl implements UrlService {

	@Override
	public List<Url> loadTree() {
		List<Url> result = new ArrayList<Url>();
		Map<String, List<Url>> childrenMap = new HashMap<String, List<Url>>();
		List<Url> urls = JpaUtil
				.linq(Url.class)
				.asc("order")
				.list();
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
	public List<Url> load() {
		return JpaUtil.linq(Url.class).asc("order").list();
	}
	
	@Override
	@SecurityCacheEvict
	public void remove(String id) {
		Url url = JpaUtil.getOne(Url.class, id);
		JpaUtil.remove(url);
	}
	
	@Override
	@SecurityCacheEvict
	public String add(Url url) {
		url.setId(UUID.randomUUID().toString());
		JpaUtil.persist(url);
		return url.getId();
	}

	@Override
	@SecurityCacheEvict
	public void modify(Url url) {
		JpaUtil.merge(url);
	}	
}
