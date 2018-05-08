package com.bstek.bdf3.security.service;

import org.malagu.linq.JpaUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.security.orm.User;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年6月6日
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Override
	public boolean isAdministrator(String username) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			if (((User) principal).getUsername().equals(username)) {
				return ((User) principal).isAdministrator();
			} 
			if (username == null) {
				return ((User) principal).isAdministrator();
			}
		}
		User user = JpaUtil.linq(User.class).idEqual(username).findOne();
		return user.isAdministrator();
	}

	@Override
	public boolean isAdministrator() {
		return isAdministrator(null);
	}

}
