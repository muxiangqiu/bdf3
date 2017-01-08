package com.bstek.bdf3.autoconfigure.security;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import com.bstek.bdf3.security.SecurityConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@AutoConfigureAfter
@ConditionalOnClass(SecurityConfiguration.class)
@ConditionalOnProperty(prefix = "security.basic", name = "enabled", matchIfMissing = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER - 100)
@Import({SecurityConfiguration.class})
public class SecurityAutoConfiguration {
	
	public static final String KEY_GENERATOR_BEAN_NAME = "keyGenerator";

	@ConditionalOnClass(SimpleKeyGenerator.class)
	@ConditionalOnMissingBean(name = KEY_GENERATOR_BEAN_NAME)
	protected static class cacheConfiguration {
		
		@Bean
		public StaticKeyGenerator keyGenerator() {
			return new StaticKeyGenerator();
		}
	}
}
