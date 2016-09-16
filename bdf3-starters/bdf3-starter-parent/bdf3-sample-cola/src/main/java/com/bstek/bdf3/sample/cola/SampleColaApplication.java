package com.bstek.bdf3.sample.cola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class SampleColaApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleColaApplication.class, args);
	}
}
