package com.bstek.bdf3.autoconfigure;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class BDFPreloadSpringApplicationRunListener implements
		SpringApplicationRunListener {
	
	private SpringApplication application;


	public BDFPreloadSpringApplicationRunListener(SpringApplication application, String[] args) {
		this.application = application;
	}

	@Override
	public void started() {
		Properties properties = new Properties();
		String basePackage = application.getClass().getPackage().getName();
		String projectName = StringUtils.substringAfterLast(basePackage, ".");
		properties.put("bdf3.projectName", projectName);
		properties.put("bdf3.basePackage", basePackage);
		properties.put("spring.mvc.staticPathPattern", "static/**");
		properties.put("spring.cache.ehcache.config", "ehcache-security.xml");
		application.setDefaultProperties(properties);
	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment) {

	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {

	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {

	}

	@Override
	public void finished(ConfigurableApplicationContext context,
			Throwable exception) {

	}

}
