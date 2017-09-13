package com.bstek.bdf3.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.bdf3.jpa.JpaUtil;
import com.bstek.bdf3.security.orm.User;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年12月10日
 */
@SpringBootApplication
@EnableCaching
@RestController
public class SampleApplication {
	
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleApplication.class, args);
	}
	
	
	@GetMapping("/load")
	@Transactional
	public Page<User> load(Pageable pageable) {
		return JpaUtil.linq(User.class).paging(pageable);
	}
	
}
