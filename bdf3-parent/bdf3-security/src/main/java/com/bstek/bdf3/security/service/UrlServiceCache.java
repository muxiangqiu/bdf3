package com.bstek.bdf3.security.service;

import java.util.List;

import com.bstek.bdf3.security.domain.Url;


/**
 * 主要内部使用，在接口代理模式下，实现缓存代理的中间类
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月30日
 */
interface UrlServiceCache {
	/**
	 * 获取所有菜单，以树形结构返回
	 * @return 所有菜单
	 */
	List<Url> findTree();
}
