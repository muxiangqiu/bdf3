package com.bstek.bdf3.autoconfigure.dbconsole;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.autoconfigure.security.SecurityAutoConfiguration;
import com.bstek.bdf3.dbconsole.DbConsoleConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月9日
 */
@Configuration
@ConditionalOnClass(DbConsoleConfiguration.class)
@AutoConfigureBefore({JpaRepositoriesAutoConfiguration.class})
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@Import(DbConsoleConfiguration.class)
public class DbConsoleAutoConfiguration {
	
}
