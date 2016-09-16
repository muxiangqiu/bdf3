package com.bstek.bdf3.jpa;


import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstek.bdf3.jpa.domain.User;
import com.bstek.bdf3.jpa.lin.Linu;


@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BDF3JpaLinuTests.class)
public class BDF3JpaLinuTests {


	@Before
	public void setUp() {
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BDF3JpaLinuTests.class, args);
	}
	
	@Test
	@Transactional
	public void testLinq() {
		JpaUtil.linu(User.class);
	}
	
	@Test
	@Transactional
	public void testUpdate() {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("tom");
		JpaUtil.persist(user);
		
		user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("lisi");
		JpaUtil.persist(user);
		Linu linu = JpaUtil.linu(User.class);
		linu.equal("name", "lisi").set("name", "kevin");
		Assert.isTrue(linu.update() == 1);
		JpaUtil.removeAll(User.class);
	}
	
}