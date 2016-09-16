package com.bstek.bdf3.autoconfigure.dorado;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DoradoApplicationContextInilializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	public void initialize(ConfigurableApplicationContext applicationContext) {
		try {			
//			Context context = CommonContext.init();
//			DoradoLoader.getInstance().setFailSafeContext(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
