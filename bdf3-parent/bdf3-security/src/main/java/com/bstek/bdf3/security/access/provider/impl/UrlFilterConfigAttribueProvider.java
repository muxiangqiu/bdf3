package com.bstek.bdf3.security.access.provider.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bstek.bdf3.security.Constants;
import com.bstek.bdf3.security.access.provider.FilterConfigAttributeProvider;
import com.bstek.bdf3.security.orm.Url;
import com.bstek.bdf3.security.service.UrlService;
/**
 * 默认菜单权限信息提供者
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Component
@Order(100)
public class UrlFilterConfigAttribueProvider implements
		FilterConfigAttributeProvider  {
	
	@Autowired(required = true)
	private UrlService urlService;
	

	@Override
	@Cacheable(cacheNames = Constants.REQUEST_MAP_CACHE_KEY, keyGenerator = Constants.KEY_GENERATOR_BEAN_NAME)
	public Map<RequestMatcher, Collection<ConfigAttribute>> provide() {
		List<Url> urls = urlService.findAll();
		Map<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		for (Url url : urls) {
			if (validate(url)) {
				requestMap.put(new AntPathRequestMatcher("/" + url.getPath(), null), url.getAttributes());
			}
		}
		return requestMap;
	}

	protected boolean validate(Url url) {
		if (StringUtils.isEmpty(url.getPath())) {
			return false;
		}
		return true;
	}

}
