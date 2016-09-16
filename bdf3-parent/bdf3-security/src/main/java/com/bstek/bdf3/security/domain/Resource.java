package com.bstek.bdf3.security.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;

/**
 * 支持授权的资源接口
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月20日
 */
public interface Resource extends Serializable{
	
	/**
	 * 提供资源对于的权限信息
	 * @return 权限信息
	 */
	List<ConfigAttribute> getAttributes();
	
}
