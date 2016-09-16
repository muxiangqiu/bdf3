package com.bstek.bdf3.jpa;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Tuple;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstek.bdf3.jpa.domain.Dept;
import com.bstek.bdf3.jpa.domain.Gender;
import com.bstek.bdf3.jpa.domain.User;
import com.bstek.bdf3.jpa.lin.Linq;


@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BDF3JpaLinqTests.class)
public class BDF3JpaLinqTests {


	@Before
	public void setUp() {
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BDF3JpaLinqTests.class, args);
	}
	
	@Test
	@Transactional
	public void testLinq() {
		JpaUtil.linq(User.class);
	}
	
	@Test
	@Transactional
	public void testSimpleFindAll() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		Assert.notEmpty(JpaUtil.linq(User.class).findAll());
		JpaUtil.removeAll(User.class);
	}
	
	@Test
	@Transactional
	public void testAddIf() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("Kevin");
		JpaUtil.persist(user);
		
		user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("Tom");
		JpaUtil.persist(user);
		List<User> users =JpaUtil
				.linq(User.class)
				.addIf(null)
					.equal("name", "Bob")
				.endIf()
				.findAll();
		Assert.notEmpty(users);
		
		users =JpaUtil
			.linq(User.class)
			.addIf("")
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.notEmpty(users);
		
		users =JpaUtil
			.linq(User.class)
			.addIf(Collections.EMPTY_LIST)
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.notEmpty(users);
		
		users =JpaUtil
			.linq(User.class)
			.addIf(new Object())
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.state(users.isEmpty());
		
		users =JpaUtil
			.linq(User.class)
			.addIf(true)
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.state(users.isEmpty());
		
		users =JpaUtil
			.linq(User.class)
			.addIf(false)
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.notEmpty(users);
		
		List<String> list = new ArrayList<String>();
		list.add("test");
		users =JpaUtil
			.linq(User.class)
			.addIf(list)
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.state(users.isEmpty());
		
		JpaUtil.removeAll(User.class);
	}
	
	@Test
	@Transactional
	public void testAddIfNot() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("Kevin");
		JpaUtil.persist(user);
		
		user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("Tom");
		JpaUtil.persist(user);
		List<User> users =JpaUtil
				.linq(User.class)
				.addIfNot(null)
					.equal("name", "Bob")
				.endIf()
				.findAll();
		Assert.state(users.isEmpty());
		
		users =JpaUtil
			.linq(User.class)
			.addIfNot("")
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.state(users.isEmpty());
		
		users =JpaUtil
			.linq(User.class)
			.addIfNot(Collections.EMPTY_LIST)
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.state(users.isEmpty());
		
		users =JpaUtil
			.linq(User.class)
			.addIfNot(new Object())
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.notEmpty(users);
		
		List<String> list = new ArrayList<String>();
		list.add("test");
		users =JpaUtil
			.linq(User.class)
			.addIfNot(list)
				.equal("name", "Bob")
			.endIf()
			.findAll();
		Assert.notEmpty(users);
		
		JpaUtil.removeAll(User.class);
	}
	
	@Test
	@Transactional
	public void testFindOne() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		User user2 = new User();
		JpaUtil.persist(user);
		Assert.notNull(JpaUtil.linq(User.class).findOne());

		user2.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user2);
		boolean error = false;
		try {
			JpaUtil.linq(User.class).findOne();
		} catch (Exception e) {
			error = true;
		} finally {
			JpaUtil.removeAllInBatch(User.class);
		}
		Assert.isTrue(error);
	}
	
	@Test
	@Transactional
	public void testFindAll() {
		Assert.notNull(JpaUtil.linq(User.class).findAll());
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("tom");
		User user2 = new User();
		user2.setName("kevin");
		user2.setId(UUID.randomUUID().toString());
		
		JpaUtil.persist(user);
		JpaUtil.persist(user2);
		
		Assert.notEmpty(JpaUtil.linq(User.class).findAll());
		
		Assert.isTrue(JpaUtil.linq(User.class).equal("name", "tom").findAll().size() == 1);
		
		Pageable pageable = new PageRequest(0, 1);
		Page<User> page = JpaUtil.linq(User.class).findAll(pageable);
		Assert.isTrue(page.getSize() == 1);
		Assert.isTrue(page.getTotalElements() == 2);
		Assert.isTrue(page.getTotalPages() == 2);
		
		Pageable pageable2 = new PageRequest(0, 1, Direction.DESC, "name");
		Page<User> page2 = JpaUtil.linq(User.class).findAll(pageable2);
		Assert.isTrue(page2.getContent().get(0).getName() == "tom");
		Assert.isTrue(page2.getSize() == 1);
		Assert.isTrue(page2.getTotalElements() == 2);
		Assert.isTrue(page2.getTotalPages() == 2);
		
		Pageable pageable3 = new PageRequest(0, 1, Direction.ASC, "name");
		Page<User> page3 = JpaUtil.linq(User.class).findAll(pageable3);
		Assert.isTrue(page3.getContent().get(0).getName() == "kevin");
		Assert.isTrue(page3.getSize() == 1);
		Assert.isTrue(page3.getTotalElements() == 2);
		Assert.isTrue(page3.getTotalPages() == 2);
		
		Pageable pageable4 = new PageRequest(0, 1);
		Page<User> page4 = JpaUtil.linq(User.class).equal("name", "tom").findAll(pageable4);
		Assert.isTrue(page4.getSize() == 1);
		Assert.isTrue(page4.getTotalElements() == 1);
		Assert.isTrue(page4.getTotalPages() == 1);
		
		Pageable pageable5 = new PageRequest(0, 1, Direction.DESC, "name");
		Page<User> page5 = JpaUtil.linq(User.class).equal("name", "tom").findAll(pageable5);
		Assert.isTrue(page5.getContent().get(0).getName() == "tom");
		Assert.isTrue(page5.getSize() == 1);
		Assert.isTrue(page5.getTotalElements() == 1);
		Assert.isTrue(page5.getTotalPages() == 1);
		
		Pageable pageable6 = new PageRequest(0, 1, Direction.DESC, "name");
		Page<User> page6 = JpaUtil.linq(User.class).equal("name", "tom").findAll(pageable6);
		Assert.isTrue(page6.getContent().get(0).getName() == "tom");
		Assert.isTrue(page6.getSize() == 1);
		Assert.isTrue(page6.getTotalElements() == 1);
		Assert.isTrue(page6.getTotalPages() == 1);
		
		Page<User> page7 = JpaUtil.linq(User.class).equal("name", "tom").findAll(null);
		Assert.isTrue(page7.getContent().get(0).getName() == "tom");
		Assert.isTrue(page7.getSize() == 0);
		Assert.isTrue(page7.getTotalElements() == 1);
		Assert.isTrue(page7.getTotalPages() == 1);
		
		JpaUtil.removeAllInBatch(User.class);
		
	}
	
	@Test
	@Transactional
	public void testCount() {
		Assert.isTrue(JpaUtil.linq(User.class).count() == 0);
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
		
		Assert.isTrue(JpaUtil.linq(User.class).count() == 3);
		
		
		Assert.isTrue(JpaUtil.linq(User.class).equal("name", "tom").count() == 1);
		
		
		JpaUtil.removeAllInBatch(User.class);
		
	}
	
	@Test
	@Transactional
	public void testExists() {
		Assert.isTrue(!JpaUtil.linq(User.class).exists());
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
		
		Assert.isTrue(JpaUtil.linq(User.class).exists());
		
		Assert.isTrue(JpaUtil.linq(User.class).equal("name", "tom").exists());
		
		JpaUtil.removeAllInBatch(User.class);
	}
	
	@Test
	@Transactional
	public void testWhere() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("tom");
		user.setAge(3);
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("kevin");
		user.setAge(25);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("lili");
		user.setAge(25);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("lili");
		user.setAge(25);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("lili");
		user.setAge(25);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("wangwu");
		user.setAge(26);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		Dept dept = new Dept();
		dept.setId(UUID.randomUUID().toString());
		dept.setName("dept1");
		JpaUtil.persist(dept);
		
		user = new User();
		user.setName("lisi");
		user.setAge(66);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		user.setDeptId(dept.getId());
		JpaUtil.persist(user);
		
		
		user = new User();
		user.setName("lili");
		user.setAge(33);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		user.setDeptId(dept.getId());
		JpaUtil.persist(user);
		
		//=======================================================
		
		//between
		Linq linq = JpaUtil.linq(User.class);
		Root<User> root = linq.root();
		linq.between("age", 20, 32);
		Assert.isTrue(linq.findAll().size() == 5);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		Expression<Integer> age = root.get("age");
		linq.between(age, age, age);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.between(age, 20, 32);
		Assert.isTrue(linq.findAll().size() == 5);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.between(age, "age", "age");
		Assert.isTrue(linq.findAll().size() == 8);
		
		//equal
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.equal(age, age);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.equal(age, 66);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.equal("age", 66);
		Assert.isTrue(linq.findAll().size() == 1);
		
		//notEqual
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.notEqual(age, age);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.notEqual(age, 66);
		Assert.isTrue(linq.findAll().size() == 7);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.notEqual("age", 66);
		Assert.isTrue(linq.findAll().size() == 7);
		
		//ge
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.ge(age,33);
		Assert.isTrue(linq.findAll().size() == 2);
		
		linq = JpaUtil.linq(User.class);
		linq.ge("age", 33);
		Assert.isTrue(linq.findAll().size() == 2);
		
		//gt
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.gt(age, age);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.gt(age, 33);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		linq.gt("age", 33);
		Assert.isTrue(linq.findAll().size() == 1);
		
		//greaterThan
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.greaterThan(age, age);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.greaterThan(age, 33);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		linq.greaterThan("age", 33);
		Assert.isTrue(linq.findAll().size() == 1);
		

		//greaterThanOrEqualTo
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.greaterThanOrEqualTo(age, age);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.greaterThanOrEqualTo(age, 33);
		Assert.isTrue(linq.findAll().size() == 2);
		
		linq = JpaUtil.linq(User.class);
		linq.greaterThanOrEqualTo("age", 33);
		Assert.isTrue(linq.findAll().size() == 2);
		
		//le
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.le(age, 10);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.le(age, age);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		linq.le("age", "age");
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		linq.le("age", 10);
		Assert.isTrue(linq.findAll().size() == 1);
		
		//lt
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.lt(age, age);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.lt(age, 10);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		linq.lt("age", 10);
		Assert.isTrue(linq.findAll().size() == 1);
		
		//lessThan
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.lessThan(age, age);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.lessThan(age, 10);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		linq.lessThan("age", 10);
		Assert.isTrue(linq.findAll().size() == 1);
		

		//lessThanOrEqualTo
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.lessThanOrEqualTo(age, age);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.lessThanOrEqualTo(age, 3);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		linq.lessThanOrEqualTo("age", 3);
		Assert.isTrue(linq.findAll().size() == 1);

		//in
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.in(age, age, age);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.in(root.get("name"), "kevin", "tom");
		Assert.isTrue(linq.findAll().size() == 2);
		
		linq = JpaUtil.linq(User.class);
		linq.in("age", 33, 66);
		Assert.isTrue(linq.findAll().size() == 2);
		
		//notIn
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		age = root.get("age");
		linq.notIn(age, age, age);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		linq.notIn(root.get("name"), "kevin", "tom");
		Assert.isTrue(linq.findAll().size() == 6);
		
		linq = JpaUtil.linq(User.class);
		linq.notIn("age", 33, 66);
		Assert.isTrue(linq.findAll().size() == 6);
		
		//isFalse
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		Expression<Boolean> enbled = root.get("enabled");
		linq.isFalse(enbled);
		Assert.isTrue(linq.findAll().size() == 2);
		
		linq = JpaUtil.linq(User.class);
		linq.isFalse("enabled");
		Assert.isTrue(linq.findAll().size() == 2);
		
		//isTrue
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		enbled = root.get("enabled");
		linq.isTrue(enbled);
		Assert.isTrue(linq.findAll().size() == 6);
		
		linq = JpaUtil.linq(User.class);
		linq.isTrue("enabled");
		Assert.isTrue(linq.findAll().size() == 6);
	
		//like
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		Expression<String> name = root.get("name");
		linq.like(name, "li%");
		Assert.isTrue(linq.findAll().size() == 5);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		name = root.get("name");
		linq.like(name, name);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		linq.like("name", "li%");
		Assert.isTrue(linq.findAll().size() == 5);
		
		//notLike
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		name = root.get("name");
		linq.notLike(name, "li%");
		Assert.isTrue(linq.findAll().size() == 3);
		
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		name = root.get("name");
		linq.notLike(name, name);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		linq.notLike("name", "li%");
		Assert.isTrue(linq.findAll().size() == 3);
		
		//isNull
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		name = root.get("name");
		linq.isNull(name);
		Assert.isTrue(linq.findAll().size() == 0);
		
		linq = JpaUtil.linq(User.class);
		linq.isNull("name");
		Assert.isTrue(linq.findAll().size() == 0);
		
		//isNotNull
		linq = JpaUtil.linq(User.class);
		root = linq.root();
		name = root.get("name");
		linq.isNotNull(name);
		Assert.isTrue(linq.findAll().size() == 8);
		
		linq = JpaUtil.linq(User.class);
		linq.isNotNull("name");
		Assert.isTrue(linq.findAll().size() == 8);
		
		//exists
		linq = JpaUtil.linq(User.class);
		linq.exists(Dept.class).equalProperty("id", "deptId").end();
		Assert.isTrue(linq.findAll().size() == 2);
		
		linq = JpaUtil.linq(User.class);
		linq.exists(Dept.class).equalProperty("id", "deptId").equal("name", "dept1").end();
		Assert.isTrue(linq.findAll().size() == 2);
		
		//and
		linq = JpaUtil.linq(User.class);
		linq.and()
			.equal("name", "kevin")
			.equal("age", 25);
		Assert.isTrue(linq.findAll().size() == 1);
		
		linq = JpaUtil.linq(User.class);
		linq.and()
				.equal("name", "kevin")
				.equal("age", 25)
					.and()
						.equal("name", "kevin")
					.end()
			.end()
			.equal("name", "kevin");
			
		Assert.isTrue(linq.findAll().size() == 1);
		
		//and
		linq = JpaUtil.linq(User.class);
		linq.or()
			.equal("name", "kevin")
			.equal("age", 33);
		Assert.isTrue(linq.findAll().size() == 2);
		
		linq = JpaUtil.linq(User.class);
		linq.or()
				.equal("name", "kevin")
				.equal("age", 23)
					.or()
						.equal("name", "kevin")
					.end()
			.end()
			.equal("name", "kevin");
			
		Assert.isTrue(linq.findAll().size() == 1);
		
		
		//and or or
		linq = JpaUtil.linq(User.class);
		linq.or()
				.equal("name", "kevin")
				.and()
					.equal("age", 26)
					.equal("name", "wangwu")
				.end()
			.end();
			
		Assert.isTrue(linq.findAll().size() == 2);

				
		
		JpaUtil.removeAllInBatch(User.class);
		JpaUtil.removeAllInBatch(Dept.class);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Transactional
	public void testSelect() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("tom");
		user.setAge(3);
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("kevin");
		user.setAge(25);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("lili");
		user.setAge(25);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("lili");
		user.setAge(25);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("lili");
		user.setAge(25);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		user = new User();
		user.setName("wangwu");
		user.setAge(26);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		JpaUtil.persist(user);
		
		Dept dept = new Dept();
		dept.setId(UUID.randomUUID().toString());
		dept.setName("dept1");
		JpaUtil.persist(dept);
		
		user = new User();
		user.setName("lisi");
		user.setAge(66);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		user.setDeptId(dept.getId());
		JpaUtil.persist(user);
		
		
		user = new User();
		user.setName("lili");
		user.setAge(33);
		user.setBirthday(new Date());
		user.setEnabled(true);
		user.setGender(Gender.male);
		user.setId(UUID.randomUUID().toString());
		user.setDeptId(dept.getId());
		JpaUtil.persist(user);
		
		//=======================================================
		
		//between
		Linq linq = JpaUtil.linq(User.class);
		linq.select("id");
		//Root<User> root = linq.getRoot();
		Assert.isInstanceOf(User.class, linq.findAll().iterator().next());
		
		linq = JpaUtil.linq(User.class, String.class);
		linq.select("name");
		Assert.isInstanceOf(String.class, linq.findAll().iterator().next());
		
		linq = JpaUtil.linq(User.class, Tuple.class);
		linq.select("name", "id");
		Assert.isInstanceOf(Tuple.class, linq.findAll().iterator().next());
		
		linq = JpaUtil.linq(User.class, Object[].class);
		linq.select("name", "id");
		Assert.isInstanceOf(Object[].class, linq.findAll().iterator().next());
		
		
		linq = JpaUtil.linq(User.class);
		linq.aliasToBean().select("name", "id");
		Assert.isInstanceOf(User.class, linq.findAll().iterator().next());
		
		linq = JpaUtil.linq(User.class);
		linq.aliasToMap().select("name", "id");
		Assert.isInstanceOf(Map.class, linq.findAll().iterator().next());
		
		
		linq = JpaUtil.linq(User.class);
		linq.aliasToBean().select("name", "id");
		user = (User) linq.findAll().iterator().next();
		Assert.isTrue(user.getName() != null);
		
		linq = JpaUtil.linq(User.class);
		linq.aliasToMap().select("name", "id");
		Map<String, Object> map = (Map<String, Object>) linq.findAll().iterator().next();
		Assert.isTrue(map.get("name") != null);
		
		linq = JpaUtil.linq(User.class);
		linq.aliasToMap().select("name").groupBy("name");
		List<Map<String, Object>> list =  linq.findAll();
		Assert.isTrue(list.size() == 5);
		
		linq = JpaUtil.linq(User.class);
		linq.aliasToMap().select("name").groupBy("name").having().equal("name", "kevin");
		list =  linq.findAll();
		Assert.isTrue(list.size() == 1);
		
		JpaUtil.removeAllInBatch(User.class);
		JpaUtil.removeAllInBatch(Dept.class);
	}
}