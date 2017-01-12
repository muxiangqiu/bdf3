package com.bstek.bdf3.sample.cola.base;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@SpringBootApplication
@Controller
public class SampleColaBaseApplication {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleColaBaseApplication.class, args);
	}
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String user() {
		return "base/user";
	}
}
