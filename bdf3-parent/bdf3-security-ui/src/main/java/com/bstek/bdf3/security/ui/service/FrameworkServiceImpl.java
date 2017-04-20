package com.bstek.bdf3.security.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.security.orm.Url;
import com.bstek.bdf3.security.service.UrlService;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月2日
 */
@Service
@Transactional(readOnly = true)
public class FrameworkServiceImpl implements FrameworkService {

	@Autowired
	private UrlService urlService;
	
	@Override
	public List<Url> loadUrlForLoginUser() {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return urlService.findTreeByUsername(user.getUsername());
	}

}
