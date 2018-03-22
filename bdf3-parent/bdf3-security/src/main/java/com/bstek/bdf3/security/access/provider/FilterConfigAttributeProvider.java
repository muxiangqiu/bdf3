package com.bstek.bdf3.security.access.provider;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

/**
 * 菜单权限信息提供者
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月22日
 */
public interface FilterConfigAttributeProvider {
	/**
	 * 提供菜单权限信息，以Map结构返回，Key为路径，<br>
	 * Value为对应的权限信息
	 * @return 权限信息
	 */
	Map<String, Collection<ConfigAttribute>> provide();
}
