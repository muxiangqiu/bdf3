package com.bstek.bdf3.autoconfigure.log;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.autoconfigure.security.SecurityAutoConfiguration;
import com.bstek.bdf3.log.LogConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
@ConditionalOnClass(LogConfiguration.class)
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@Import(LogConfiguration.class)
public class LogAutoConfiguration {
	
}
