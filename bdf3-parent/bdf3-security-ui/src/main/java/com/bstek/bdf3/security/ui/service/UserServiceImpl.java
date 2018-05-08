package com.bstek.bdf3.security.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.dorado.jpa.policy.SaveContext;
import com.bstek.bdf3.dorado.jpa.policy.impl.SmartSavePolicyAdapter;
import com.bstek.bdf3.security.orm.RoleGrantedAuthority;
import com.bstek.bdf3.security.orm.User;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月2日
 */
@Service("ui.userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Override
	public void load(Page<User> page, Criteria criteria) {
		JpaUtil.linq(User.class).where(criteria).paging(page);
	}
	
	@Override
	public String validateOldPassword(String oldPassword) {
		if (oldPassword != null) {
			UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String password = JpaUtil.getOne(User.class, user.getUsername()).getPassword();
			if (passwordEncoder.matches(oldPassword, password)) {
				return null;
			}
		}
		
		return "原来密码输入不正确。";
	}
	
	@Override
	@Transactional
	public void save(List<User> users) {
		JpaUtil.save(users, new SmartSavePolicyAdapter() {

			@Override
			public boolean beforeInsert(SaveContext context) {
				User user = context.getEntity();
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				return true;
			}

			@Override
			public boolean beforeDelete(SaveContext context) {
				User user = context.getEntity();
				JpaUtil.lind(RoleGrantedAuthority.class)
					.equal("actorId", user.getUsername())
					.delete();
				return true;
			}
			
			
			
		});
	}
	
	@Override
	@Transactional
	public void changePassword(String username, String newPassword) {
		User u = JpaUtil.getOne(User.class, username);
		u.setPassword(passwordEncoder.encode(newPassword));
	}

	@Override
	public boolean isExist(String username) {
		return JpaUtil.linq(User.class).equal("username", username).exists();
	}

	@Override
	@Transactional
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		JpaUtil.persistAndFlush(user);
	}
}
