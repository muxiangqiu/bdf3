package com.bstek.bdf3.security;


import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.jpa.JpaUtilAble;
import com.bstek.bdf3.security.domain.User;

/**
 * 安全初始化器。<br>
 * 项目第一次运行时，可能没有一个可以用来登录的用户，在这种情况下，<br>
 * 安全初始化器创建一个默认用户，用户名为admin，密码为123456。<br>
 * 另外，通过设置bdf3.security.autoCreateIfUserIsEmpty属性来禁用此功能
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月25日
 */
@Component
public class SecurityInitializer extends JpaUtilAble {

	@Value("${bdf3.security.autoCreateIfUserIsEmpty:true}")
	private boolean autoCreateIfUserIsEmpty;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		if (autoCreateIfUserIsEmpty) {

			EntityManager em = JpaUtil.createEntityManager(User.class);
			try {
				if (!JpaUtil.linq(User.class, em).exists()) {
					User user = new User();
					user.setNickname("管理员");
					user.setUsername("admin");
					user.setPassword(passwordEncoder.encode("123456"));
					em.getTransaction().begin();
					em.persist(user);
					em.getTransaction().commit();
				}
			} finally {
				em.close();
			}
		}
	}
}
