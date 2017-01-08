package com.bstek.bdf3.security.ui.service;

import java.util.List;

import com.bstek.bdf3.security.domain.Url;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月2日
 */
public interface FrameworkService {
	
	List<Url> loadUrlForLoginUser();
}
