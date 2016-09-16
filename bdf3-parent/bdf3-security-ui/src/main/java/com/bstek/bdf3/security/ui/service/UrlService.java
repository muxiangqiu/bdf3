package com.bstek.bdf3.security.ui.service;

import java.util.List;

import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月11日
 */
public interface UrlService {

	void save(List<Url> urls);

	List<Url> load();

}
