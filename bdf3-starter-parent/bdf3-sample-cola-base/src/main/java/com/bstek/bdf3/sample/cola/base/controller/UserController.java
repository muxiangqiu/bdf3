package com.bstek.bdf3.sample.cola.base.controller;



import java.util.List;

import org.malagu.linq.JpaUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.sample.cola.base.domain.User;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
@RestController
@Transactional(readOnly = true)
@RequestMapping("/api")
public class UserController {
	
	@RequestMapping(path = "/user/load", method = RequestMethod.GET)
	public List<User> load(Pageable pageable, @RequestParam(name = "searchKey", required = false) String searchKey) {
		return JpaUtil
			.linq(User.class)
			.addIf(searchKey)
				.or()
					.like("username", "%" + searchKey + "%")
					.like("nickname", "%" + searchKey + "%")
				.end()
			.endIf()
			.list(pageable);
	}
	
	@RequestMapping(path = "/user/remove/{id}", method = RequestMethod.DELETE)
	@Transactional
	public void remove(@PathVariable String id) {
		User user = JpaUtil.getOne(User.class, id);
		JpaUtil.remove(user);
	}
	
	@RequestMapping(path = "/user/add", method = RequestMethod.POST)
	@Transactional
	public void add(@RequestBody User user) throws Exception {
		JpaUtil.persist(user);
	}

	@RequestMapping(path = "/user/modify", method = RequestMethod.PUT)
	@Transactional
	public void modify(@RequestBody User user) throws Exception {
		JpaUtil.merge(user);
	}
	

}
