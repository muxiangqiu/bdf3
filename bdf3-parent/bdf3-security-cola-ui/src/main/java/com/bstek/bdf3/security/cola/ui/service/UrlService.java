package com.bstek.bdf3.security.cola.ui.service;

import java.util.List;

import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月20日
 */
public interface UrlService {

	List<Url> loadTree();

	List<Url> load();

	void remove(String id);

	String add(Url url);

	void modify(Url url);

}
