package com.bstek.bdf3.security.ui.controller;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.security.domain.Permission;
import com.bstek.bdf3.security.domain.Role;
import com.bstek.bdf3.security.domain.RoleGrantedAuthority;
import com.bstek.bdf3.security.domain.Url;
import com.bstek.bdf3.security.domain.User;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@Controller
@Transactional(readOnly = true)
public class UserRoleController {
	
	@DataProvider
	public List<RoleGrantedAuthority> load(String username) {
		return JpaUtil.linq(RoleGrantedAuthority.class)
				.toEntity()
				.equal("actorId", username)
				.collect(Role.class, "roleId")
				.findAll();
	}
	
	@DataProvider
	public List<Url> loadUrls(String username) {
		return JpaUtil.linq(Url.class)
				.exists(Permission.class)
					.equalProperty("resourceId", "id")
					.exists(RoleGrantedAuthority.class)
						.equalProperty("roleId", "roleId")
						.equal("actorId", username)
				.findAll();
	}
	
	@DataResolver
	@Transactional
	public void save(List<User> users) {
		for (User user : users) {
			JpaUtil.save(user.getAuthorities());
		}
		
	}
	

}
