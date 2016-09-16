package com.bstek.bdf3.security;

import org.springframework.core.Ordered;

/**
 * 常量类
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
public final class Constants {

	/**
	 * 所有菜单权限缓存关键字
	 */
	public final static String REQUEST_MAP_CACHE_KEY = "RequestMap";
	/**
	 * 特定菜单权限缓存关键字
	 */
	public final static String URL_ATTRIBUTE_BY_TARGET_CACHE_KEY = "UrlAttributeByTargetCacheKey";
	/**
	 * 树形菜单缓存关键字
	 */
	public final static String URL_TREE_CACHE_KEY = "UrlTree";
	/**
	 * 特定用户下的树形菜单缓存关键字
	 */
	public final static String URL_TREE_BY_USRNAME_CACHE_KEY = "UrlTreeByUsername";
	public final static String[] URL_SECURITY = new String[] {REQUEST_MAP_CACHE_KEY, URL_ATTRIBUTE_BY_TARGET_CACHE_KEY, URL_TREE_CACHE_KEY, URL_TREE_BY_USRNAME_CACHE_KEY};

	/**
	 * 所有组件权限缓存关键字
	 */
	public final static String COMPONENT_ATTRIBUTE_MAP_CACHE_KEY = "ComponentAttributeMap";
	
	/**
	 * 所有组件缓存关键字
	 */
	public final static String COMPONENT_MAP_CACHE_KEY = "ComponentMap";
	
	/**
	 * 特定组件权限婚车关键字
	 */
	public final static String COMPONENT_ATTRIBUTE_BY_TARGET_CACHE_KEY = "ComponentAttributeByTargetCacheKey";
	public final static String[] COMPONENT_SECURITY = new String[] {COMPONENT_ATTRIBUTE_MAP_CACHE_KEY, COMPONENT_ATTRIBUTE_BY_TARGET_CACHE_KEY};
	
	public final static String[] URL_COMPONENT_SECURITY = new String[] {REQUEST_MAP_CACHE_KEY, URL_ATTRIBUTE_BY_TARGET_CACHE_KEY, URL_TREE_CACHE_KEY, URL_TREE_BY_USRNAME_CACHE_KEY, COMPONENT_ATTRIBUTE_MAP_CACHE_KEY, COMPONENT_ATTRIBUTE_BY_TARGET_CACHE_KEY};

	public static final String KEY_GENERATOR_BEAN_NAME = "keyGenerator";
	
	public static final int SECURITY_CONFIGURER_ORDER = Ordered.LOWEST_PRECEDENCE - 1000;
	
}
