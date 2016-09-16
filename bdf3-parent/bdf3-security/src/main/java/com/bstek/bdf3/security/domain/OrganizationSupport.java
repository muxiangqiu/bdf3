package com.bstek.bdf3.security.domain;
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月13日
 */
public interface OrganizationSupport {
	<T> T getOrganization();
	<T> void setOrganization(T t);
	
}
