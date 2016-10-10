package com.bstek.bdf3.saas.service.allocator.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.saas.domain.Organization;
import com.bstek.bdf3.saas.service.EntityManagerFactoryService;
import com.bstek.bdf3.saas.service.allocator.ResourceAllocator;
import com.bstek.bdf3.security.domain.Url;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月12日
 */
@Component
@Order(100)
public class DatabaseResourceAllocator implements ResourceAllocator{
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Autowired
	private EntityManagerFactoryService entityManagerFactoryService;
	
	

	@Override
	public void allocate(Organization organization) {
		EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(emf);
		em.createNativeQuery("create database " + organization.getId()).executeUpdate();
		EntityManagerFactory entityManagerFactory = entityManagerFactoryService.getOrCreateEntityManagerFactory(organization);
		EntityManager entityManager = null;
		try {
			entityManagerFactory.getMetamodel().entity(Url.class);
			entityManager = entityManagerFactory.createEntityManager();
		} catch (IllegalArgumentException e) {
			entityManager = emf.createEntityManager();
		}
		try {
			
			if (!JpaUtil.linq(Url.class, entityManager).exists()) {
				entityManager.getTransaction().begin();
				Url url = new Url();
				url.setId(UUID.randomUUID().toString());
				url.setName("用户管理");
				url.setIcon("user icon");
				url.setPath("user");
				url.setNavigable(true);
				url.setOrder(0);
				entityManager.persist(url);
				
				url = new Url();
				url.setId(UUID.randomUUID().toString());
				url.setName("菜单管理");
				url.setIcon("sitemap icon");
				url.setPath("url");
				url.setNavigable(true);
				url.setOrder(1);
				entityManager.persist(url);
				
				url = new Url();
				url.setId(UUID.randomUUID().toString());
				url.setName("角色管理");
				url.setIcon("spy icon");
				url.setPath("role");
				url.setNavigable(true);
				url.setOrder(2);
				entityManager.persist(url);
				
				url = new Url();
				url.setId(UUID.randomUUID().toString());
				url.setName("分配组件");
				url.setIcon("cubes icon");
				url.setPath("component");
				url.setNavigable(true);
				url.setOrder(3);
				entityManager.persist(url);
				
				url = new Url();
				url.setId(UUID.randomUUID().toString());
				url.setName("公告管理");
				url.setIcon("volume up icon");
				url.setPath("announce/manage");
				url.setNavigable(true);
				url.setOrder(4);
				entityManager.persist(url);
				
				url = new Url();
				url.setId(UUID.randomUUID().toString());
				url.setName("公告中心");
				url.setIcon("announcement icon");
				url.setPath("announce");
				url.setNavigable(true);
				url.setOrder(5);
				entityManager.persist(url);
				
				url = new Url();
				url.setId(UUID.randomUUID().toString());
				url.setName("私信中心");
				url.setIcon("comments outline icon");
				url.setPath("message");
				url.setNavigable(true);
				url.setOrder(6);
				entityManager.persist(url);
				
				
				entityManager.getTransaction().commit();
			}
		} finally {
			entityManager.close();
		}
	}
	
	
}
