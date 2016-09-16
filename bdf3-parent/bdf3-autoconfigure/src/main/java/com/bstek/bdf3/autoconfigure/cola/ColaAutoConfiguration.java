package com.bstek.bdf3.autoconfigure.cola;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ui.velocity.VelocityEngineFactory;

import com.bstek.bdf3.cola.configure.ColaConfiguration;
import com.bstek.bdf3.cola.velocity.ClasspathResourceLoader;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
@ConditionalOnClass(ColaConfiguration.class)
@AutoConfigureBefore(VelocityAutoConfiguration.class)
@Import(ColaConfiguration.class)
public class ColaAutoConfiguration {
		
	@Configuration
	public static class VelocityConfigurer extends org.springframework.web.servlet.view.velocity.VelocityConfigurer {
	
		private static final String BDF3_MACRO_RESOURCE_LOADER_NAME = "bdf3Macro";
	
		private static final String BDF3_MACRO_RESOURCE_LOADER_CLASS = "bdf3Macro.resource.loader.class";
		
		private VelocityEngine velocityEngine;
		
		@Autowired
		private VelocityProperties properties;
		
		
		protected void applyProperties(VelocityEngineFactory factory) {
			factory.setResourceLoaderPath(this.properties.getResourceLoaderPath());
			factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
			Properties velocityProperties = new Properties();
			velocityProperties.setProperty("input.encoding",
					this.properties.getCharsetName());
			velocityProperties.putAll(this.properties.getProperties());
			factory.setVelocityProperties(velocityProperties);
		}
		
		public void setVelocityEngine(VelocityEngine velocityEngine) {
			this.velocityEngine = velocityEngine;
		}
		
		@Override
		public void afterPropertiesSet() throws IOException, VelocityException {
			if (this.velocityEngine == null) {
				applyProperties(this);
				this.velocityEngine = createVelocityEngine();
			}
		}
		
		@Override
		protected void postProcessVelocityEngine(VelocityEngine velocityEngine) {
			super.postProcessVelocityEngine(velocityEngine);
			velocityEngine.setProperty(
					BDF3_MACRO_RESOURCE_LOADER_CLASS, ClasspathResourceLoader.class.getName());
			velocityEngine.addProperty(
					VelocityEngine.RESOURCE_LOADER, BDF3_MACRO_RESOURCE_LOADER_NAME);
			
			if (logger.isInfoEnabled()) {
				logger.info("ClasspathResourceLoader with name '" + BDF3_MACRO_RESOURCE_LOADER_NAME +
						"' added to configured VelocityEngine");
			}
		
		}
		
		@Override
		public VelocityEngine getVelocityEngine() {
			return this.velocityEngine;
		}
		
	}



}
