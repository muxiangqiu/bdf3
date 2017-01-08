package com.bstek.bdf3.autoconfigure.dorado;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ClassUtils;

import com.bstek.dorado.web.loader.DoradoLoader;

public class DoradoPreloadSpringApplicationRunListener implements
		SpringApplicationRunListener {
	
	private SpringApplication application;


	public DoradoPreloadSpringApplicationRunListener(SpringApplication application, String[] args) {
		this.application = application;
	}

	@Override
	public void started() {
		if (ClassUtils.isPresent("com.bstek.dorado.web.loader.DoradoLoader", this.getClass().getClassLoader())){
			System.setProperty("doradoHome", "classpath:dorado-home/");
	
			DoradoLoader doradoLoader = DoradoLoader.getInstance();
			try {
				doradoLoader.preload(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Set<Object> sources = new HashSet<Object>();
			sources.addAll(doradoLoader
					.getContextLocations(false));
			application.setSources(sources);
		}
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
