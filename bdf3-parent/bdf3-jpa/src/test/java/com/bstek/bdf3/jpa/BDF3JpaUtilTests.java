package com.bstek.bdf3.jpa;


import java.util.Arrays;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstek.bdf3.jpa.domain.User;


@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
public class BDF3JpaUtilTests {


	@Before
	public void setUp() {
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BDF3JpaUtilTests.class, args);
	}
	
	@Test
	@Transactional
	public void testJpaUtil() {
		
	}
	
	@Test
	@Transactional
	public void testSave() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		Assert.notEmpty(JpaUtil.findAll(User.class), "Not Success.");
		JpaUtil.removeAll(User.class);
	}
	
	@Test
	@Transactional
	public void testMerge() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		Assert.notEmpty(JpaUtil.findAll(User.class), "Not Success.");
		User u = new User();
		u.setId(user.getId());
		u.setAge(1);
		JpaUtil.merge(u);
		Assert.isTrue(JpaUtil.findOne(User.class).getAge() == 1, "Not Success.");
		JpaUtil.removeAll(User.class);
	}
	
	@Test
	@Transactional
	public void testRemove() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persistAndFlush(user);
		JpaUtil.remove(user);
		
		User user2 = new User();
		user2.setId(UUID.randomUUID().toString());
		JpaUtil.persistAndFlush(user2);
		JpaUtil.remove(Arrays.asList(user2));
		Assert.isTrue(JpaUtil.findAll(User.class).isEmpty(), "Not Success.");
	}
	
	@Test
	@Transactional
	public void testRemoveAll() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persistAndFlush(user);
		JpaUtil.removeAll(User.class);
		Assert.isTrue(JpaUtil.findAll(User.class).isEmpty(), "Not Success.");
	}
	
	@Test
	@Transactional
	public void testRemoveAllInBatch() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persistAndFlush(user);
		JpaUtil.removeAllInBatch(User.class);
		Assert.isTrue(JpaUtil.findAll(User.class).isEmpty(), "Not Success.");
	}
	
	@Test
	@Transactional
	public void testFindOne() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		User user2 = new User();
		JpaUtil.persist(user);
		Assert.notNull(JpaUtil.findOne(User.class), "Not Success.");

		user2.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user2);
		boolean error = false;
		try {
			JpaUtil.findOne(User.class);
		} catch (Exception e) {
			error = true;
		} finally {
			JpaUtil.removeAllInBatch(User.class);
		}
		Assert.isTrue(error, "Not Success.");
	}
	
	@Test
	@Transactional
	public void testFindAll() {
		Assert.notNull(JpaUtil.findAll(User.class), "Not Success.");
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("tom");
		User user2 = new User();
		user2.setName("kevin");
		user2.setId(UUID.randomUUID().toString());
		
		JpaUtil.persist(user);
		JpaUtil.persist(user2);
		
		Assert.notEmpty(JpaUtil.findAll(User.class), "Not Success.");
		
		EntityManager em = JpaUtil.getEntityManager(User.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Assert.isTrue(JpaUtil.findAll(cq).size() == 2, "Not Success.");
		
		cq.getRoots().clear();
		Root<User> root = cq.from(User.class);
		Assert.isTrue(JpaUtil.findAll(cq).size() == 2, "Not Success.");
		
		cq.where(cb.equal(root.get("name"), "tom"));
		Assert.isTrue(JpaUtil.findAll(cq).size() == 1, "Not Success.");
		
		Pageable pageable = new PageRequest(0, 1);
		Page<User> page = JpaUtil.findAll(User.class, pageable);
		Assert.isTrue(page.getSize() == 1, "Not Success.");
		Assert.isTrue(page.getTotalElements() == 2, "Not Success.");
		Assert.isTrue(page.getTotalPages() == 2, "Not Success.");
		
		Pageable pageable2 = new PageRequest(0, 1, Direction.DESC, "name");
		Page<User> page2 = JpaUtil.findAll(User.class, pageable2);
		Assert.isTrue(page2.getContent().get(0).getName() == "tom", "Not Success.");
		Assert.isTrue(page2.getSize() == 1, "Not Success.");
		Assert.isTrue(page2.getTotalElements() == 2, "Not Success.");
		Assert.isTrue(page2.getTotalPages() == 2, "Not Success.");
		
		Pageable pageable3 = new PageRequest(0, 1, Direction.ASC, "name");
		Page<User> page3 = JpaUtil.findAll(User.class, pageable3);
		Assert.isTrue(page3.getContent().get(0).getName() == "kevin", "Not Success.");
		Assert.isTrue(page3.getSize() == 1, "Not Success.");
		Assert.isTrue(page3.getTotalElements() == 2, "Not Success.");
		Assert.isTrue(page3.getTotalPages() == 2, "Not Success.");
		
		Pageable pageable4 = new PageRequest(0, 1);
		Page<User> page4 = JpaUtil.findAll(cq, pageable4);
		Assert.isTrue(page4.getSize() == 1, "Not Success.");
		Assert.isTrue(page4.getTotalElements() == 1, "Not Success.");
		Assert.isTrue(page4.getTotalPages() == 1, "Not Success.");
		
		Pageable pageable5 = new PageRequest(0, 1, Direction.DESC, "name");
		Page<User> page5 = JpaUtil.findAll(cq, pageable5);
		Assert.isTrue(page5.getContent().get(0).getName() == "tom", "Not Success.");
		Assert.isTrue(page5.getSize() == 1, "Not Success.");
		Assert.isTrue(page5.getTotalElements() == 1, "Not Success.");
		Assert.isTrue(page5.getTotalPages() == 1, "Not Success.");
		
		Pageable pageable6 = new PageRequest(0, 1, Direction.DESC, "name");
		Page<User> page6 = JpaUtil.findAll(cq, pageable6);
		Assert.isTrue(page6.getContent().get(0).getName() == "tom", "Not Success.");
		Assert.isTrue(page6.getSize() == 1, "Not Success.");
		Assert.isTrue(page6.getTotalElements() == 1, "Not Success.");
		Assert.isTrue(page6.getTotalPages() == 1, "Not Success.");
		
		Page<User> page7 = JpaUtil.findAll(cq, null);
		Assert.isTrue(page7.getContent().get(0).getName() == "tom", "Not Success.");
		Assert.isTrue(page7.getSize() == 0, "Not Success.");
		Assert.isTrue(page7.getTotalElements() == 1, "Not Success.");
		Assert.isTrue(page7.getTotalPages() == 1, "Not Success.");
		
		JpaUtil.removeAllInBatch(User.class);
		
	}
	
	@Test
	@Transactional
	public void testCount() {
		Assert.isTrue(JpaUtil.count(User.class) == 0, "Not Success.");
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("tom");
		User user2 = new User();
		user2.setName("kevin");
		user2.setId(UUID.randomUUID().toString());
		User user3 = new User();
		user3.setName("kevin");
		user3.setId(UUID.randomUUID().toString());
		
		JpaUtil.persist(user);
		JpaUtil.persist(user2);
		JpaUtil.persist(user3);
		
		Assert.isTrue(JpaUtil.count(User.class) == 3, "Not Success.");
		
		EntityManager em = JpaUtil.getEntityManager(User.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		Assert.isTrue(JpaUtil.count(User.class) == 3, "Not Success.");
		
		cq.where(cb.equal(root.get("name"), "tom"));
		Assert.isTrue(JpaUtil.count(cq) == 1, "Not Success.");
		
		
		JpaUtil.removeAllInBatch(User.class);
		
	}
	
	@Test
	@Transactional
	public void testExists() {
		Assert.isTrue(!JpaUtil.exists(User.class), "Not Success.");
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("tom");
		User user2 = new User();
		user2.setName("kevin");
		user2.setId(UUID.randomUUID().toString());
		User user3 = new User();
		user3.setName("kevin");
		user3.setId(UUID.randomUUID().toString());
		
		JpaUtil.persist(user);
		JpaUtil.persist(user2);
		JpaUtil.persist(user3);
		
		Assert.isTrue(JpaUtil.exists(User.class), "Not Success.");
		
		EntityManager em = JpaUtil.getEntityManager(User.class);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);

		Assert.isTrue(JpaUtil.exists(cq), "Not Success.");
		
		cq.where(cb.equal(root.get("name"), "tom"));
		Assert.isTrue(JpaUtil.exists(cq), "Not Success.");
		
		JpaUtil.removeAllInBatch(User.class);
	}
}