package com.bstek.bdf3.sample.activiti.cola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class SampleActivitiColaApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleActivitiColaApplication.class, args);
	}
}
