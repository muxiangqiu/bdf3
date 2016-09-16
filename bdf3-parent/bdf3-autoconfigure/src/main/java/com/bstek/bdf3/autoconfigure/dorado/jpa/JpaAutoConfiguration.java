package com.bstek.bdf3.autoconfigure.dorado.jpa;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.autoconfigure.dorado.DoradoAutoConfiguration;
import com.bstek.bdf3.dorado.jpa.configure.JpaConfiguration;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
@ConditionalOnClass(JpaConfiguration.class)
@AutoConfigureAfter({HibernateJpaAutoConfiguration.class, DoradoAutoConfiguration.class})
@Import(JpaConfiguration.class)
public class JpaAutoConfiguration {
	
	
}
