package com.bstek.bdf3.autoconfigure.saas;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.bstek.bdf3.autoconfigure.security.SecurityAutoConfiguration;
import com.bstek.bdf3.saas.SaasConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@ConditionalOnClass(SaasConfiguration.class)
@AutoConfigureAfter({SecurityAutoConfiguration.class})
@Import(SaasConfiguration.class)
public class SaasAutoConfiguration {
	
	
	
	@ConditionalOnClass(SimpleKeyGenerator.class)
	protected static class cacheConfiguration {
		
		@Bean
		public OrganizationKeyGenerator keyGenerator() {
			return new OrganizationKeyGenerator();
		}
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new SaasJpaTransactionManager();
	}
	
}
