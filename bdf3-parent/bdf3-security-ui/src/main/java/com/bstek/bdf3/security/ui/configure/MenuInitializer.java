package com.bstek.bdf3.security.ui.configure;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.jpa.JpaUtilAble;
import com.bstek.bdf3.security.domain.Url;

/**
 * 菜单初始化器。<br>
 * 当系统没有菜单的时候，自动生成系统默认菜单<br>
 * 另外，通过设置bdf3.security.ui.autoCreateIfMenuIsEmpty属性来禁用此功能
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月25日
 */
@Component
public class MenuInitializer extends JpaUtilAble {

	@Value("${bdf3.security.ui.autoCreateIfMenuIsEmpty:true}")
	private boolean autoCreateIfMenuIsEmpty;

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		if (autoCreateIfMenuIsEmpty) {
			EntityManager em = JpaUtil.createEntityManager(Url.class);
			try {
				
				if (!JpaUtil.linq(Url.class, em).exists()) {
					em.getTransaction().begin();
					Url url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("用户管理");
					url.setIcon("fa fa-user");
					url.setPath("bdf3.security.ui.view.UserMaintain.d");
					url.setNavigable(true);
					url.setOrder(0);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("菜单管理");
					url.setIcon("fa fa-sitemap");
					url.setPath("bdf3.security.ui.view.UrlMaintain.d");
					url.setNavigable(true);
					url.setOrder(1);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("角色管理");
					url.setIcon("fa fa-user-md");
					url.setPath("bdf3.security.ui.view.RoleMaintain.d");
					url.setNavigable(true);
					url.setOrder(2);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("菜单权限");
					url.setIcon("fa fa-code-fork");
					url.setPath("bdf3.security.ui.view.RoleUrlMaintain.d");
					url.setNavigable(true);
					url.setOrder(3);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("组件权限");
					url.setIcon("fa fa-cubes");
					url.setPath("bdf3.security.ui.view.PermissionMaintain.d");
					url.setNavigable(true);
					url.setOrder(4);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("角色分配");
					url.setIcon("fa fa-male");
					url.setPath("bdf3.security.ui.view.UserRoleMaintain.d");
					url.setNavigable(true);
					url.setOrder(5);
					em.persist(url);
					
					em.getTransaction().commit();
				}
			} finally {
				em.close();
			}
		}
	}
}