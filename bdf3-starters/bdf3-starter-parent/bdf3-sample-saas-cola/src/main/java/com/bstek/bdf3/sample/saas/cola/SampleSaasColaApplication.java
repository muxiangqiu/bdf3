package com.bstek.bdf3.sample.saas.cola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class SampleSaasColaApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleSaasColaApplication.class, args);
	}
}
