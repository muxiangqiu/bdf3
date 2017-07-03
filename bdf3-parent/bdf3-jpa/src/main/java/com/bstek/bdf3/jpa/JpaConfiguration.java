package com.bstek.bdf3.jpa;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bstek.bdf3.jpa.initiator.JpaUtilAble;
import com.bstek.bdf3.jpa.initiator.JpaUtilInitiator;

/**
 * 模块配置类
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月24日
 */
@Configuration
@ComponentScan
public class JpaConfiguration implements ApplicationContextAware {

	@Autowired
	List<JpaUtilInitiator> jpaUtilInitiators;
	
	@Autowired(required = false)
	List<JpaUtilAble> jpaUtilAbles;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		for (JpaUtilInitiator jpaUtilInitiator : jpaUtilInitiators) {
			jpaUtilInitiator.initialize(applicationContext);
		}
		if (jpaUtilAbles != null) {
			for (JpaUtilAble jpaUtilAble : jpaUtilAbles) {
				jpaUtilAble.afterPropertiesSet(applicationContext);
			}
		}
	}

}
