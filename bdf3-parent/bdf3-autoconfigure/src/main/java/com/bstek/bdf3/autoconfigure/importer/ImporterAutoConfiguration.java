package com.bstek.bdf3.autoconfigure.importer;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.autoconfigure.security.SecurityAutoConfiguration;
import com.bstek.bdf3.importer.ImporterConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
@ConditionalOnClass(ImporterConfiguration.class)
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@Import(ImporterConfiguration.class)
public class ImporterAutoConfiguration {
	
}
