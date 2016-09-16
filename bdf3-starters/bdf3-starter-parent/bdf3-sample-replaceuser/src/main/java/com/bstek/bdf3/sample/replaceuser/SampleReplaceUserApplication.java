package com.bstek.bdf3.sample.replaceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@SpringBootApplication
@EnableCaching
@Controller
public class SampleReplaceUserApplication {
	
	@RequestMapping("modify-user")
	public String modifyUser() {
		return "modify-user";
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleReplaceUserApplication.class, args);
	}
}
