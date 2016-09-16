package com.bstek.bdf3.security.access.provider;

import java.util.Collection;
import java.util.Map;

import com.bstek.bdf3.security.domain.Component;

/**
 * 组件提供者
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月6日
 */
public interface ComponentProvider {

	Map<String, Collection<Component>> provide();
}
