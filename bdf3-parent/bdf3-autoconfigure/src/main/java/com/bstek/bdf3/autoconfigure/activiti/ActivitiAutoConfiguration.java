package com.bstek.bdf3.autoconfigure.activiti;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bstek.bdf3.activiti.ActivitiConfiguration;
import com.bstek.bdf3.autoconfigure.message.MessageAutoConfiguration;
import com.bstek.bdf3.autoconfigure.security.SecurityAutoConfiguration;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月3日
 */
@Configuration
@AutoConfigureAfter({MessageAutoConfiguration.class, SecurityAutoConfiguration.class})
@ConditionalOnClass(ActivitiConfiguration.class)
@Import(ActivitiConfiguration.class)
public class ActivitiAutoConfiguration {

}
