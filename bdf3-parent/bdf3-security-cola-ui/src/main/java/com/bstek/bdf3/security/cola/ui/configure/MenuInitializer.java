package com.bstek.bdf3.security.cola.ui.configure;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.jpa.JpaUtilAble;
import com.bstek.bdf3.security.orm.Url;

/**
 * 菜单初始化器。<br>
 * 当系统没有菜单的时候，自动生成系统默认菜单<br>
 * 另外，通过设置bdf3.security.ui.autoCreateIfMenuIsEmpty属性来禁用此功能
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月25日
 */
@Component("cola.menuInitializer")
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
					url.setIcon("user icon");
					url.setPath("user");
					url.setNavigable(true);
					url.setOrder(0);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("菜单管理");
					url.setIcon("sitemap icon");
					url.setPath("url");
					url.setNavigable(true);
					url.setOrder(1);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("角色管理");
					url.setIcon("spy icon");
					url.setPath("role");
					url.setNavigable(true);
					url.setOrder(2);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("分配组件");
					url.setIcon("cubes icon");
					url.setPath("component");
					url.setNavigable(true);
					url.setOrder(3);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("公告管理");
					url.setIcon("volume up icon");
					url.setPath("announce/manage");
					url.setNavigable(true);
					url.setOrder(4);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("公告中心");
					url.setIcon("announcement icon");
					url.setPath("announce");
					url.setNavigable(true);
					url.setOrder(5);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("私信中心");
					url.setIcon("comments outline icon");
					url.setPath("message");
					url.setNavigable(true);
					url.setOrder(6);
					em.persist(url);
					
					url = new Url();
					url.setId(UUID.randomUUID().toString());
					url.setName("我的账户");
					url.setIcon("smile icon");
					url.setPath("me");
					url.setNavigable(true);
					url.setOrder(7);
					em.persist(url);
					
					em.getTransaction().commit();
				}
			} finally {
				em.close();
			}
		}
	}
}