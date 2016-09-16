package com.bstek.bdf3.security.ui.controller;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.security.domain.User;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
@Transactional(readOnly = true)
public class UserController {
	
	@DataProvider
	public void load(Page<User>page, Criteria criteria) {
		JpaUtil.linq(User.class).where(criteria).findAll(page);
	}
	
	@DataResolver
	@Transactional
	public void save(List<User> users) {
		JpaUtil.save(users);
	}
	

}
