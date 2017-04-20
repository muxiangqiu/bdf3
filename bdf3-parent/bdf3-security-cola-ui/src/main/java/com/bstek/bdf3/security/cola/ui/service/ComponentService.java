package com.bstek.bdf3.security.cola.ui.service;

import java.util.List;

import com.bstek.bdf3.security.orm.Component;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月3日
 */
public interface ComponentService {

	List<Component> load(String roleId, String urlId);

	void remove(String id);

	String add(Component component);

	void modify(Component component);

	List<Component> loadComponentsByPath(String path);

}
