package com.bstek.bdf3.autoconfigure.dorado;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ClassUtils;

import com.bstek.dorado.core.CommonContext;
import com.bstek.dorado.core.Context;
import com.bstek.dorado.web.loader.DoradoLoader;

public class DoradoPreloadSpringApplicationRunListener implements
		SpringApplicationRunListener {
	
	private SpringApplication application;


	public DoradoPreloadSpringApplicationRunListener(SpringApplication application, String[] args) {
		this.application = application;
	}

	@Override
	public void environmentPrepared(ConfigurableEnvironment environment) {

	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext applicationContext) {
		if (ClassUtils.isPresent("com.bstek.dorado.web.loader.DoradoLoader", this.getClass().getClassLoader())){
			System.setProperty("doradoHome", "classpath:dorado-home/");
	
			DoradoLoader doradoLoader = DoradoLoader.getInstance();
			try {
				Context context = CommonContext.init(applicationContext);
				DoradoLoader.getInstance().setFailSafeContext(context);
				doradoLoader.preload(true);;
			} catch (Exception e) {
				e.printStackTrace();
			}
			Set<String> sources = new LinkedHashSet<String>();
			sources.addAll(doradoLoader
					.getContextLocations(false));
			application.setSources(sources);
		}
	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {

	}

	@Override
	public void starting() {
		
	}

	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void running(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void started(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub
		
	}

}
