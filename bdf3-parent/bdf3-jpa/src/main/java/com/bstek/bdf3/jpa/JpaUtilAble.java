package com.bstek.bdf3.jpa;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.bstek.bdf3.jpa.strategy.GetEntityManagerFactoryStrategy;

/**
 * JpaUtil初始化类
 * JpaUtil在启动时候会进行初始化动作，初始化之前用JpaUtil<br>
 * 会抱错，如果需要在项目启动的时候用JpaUtil,可以通过继承JpaUtilAble类，<br>
 * 从而确保JpaUtil已经被初始化
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月14日
 */
public class JpaUtilAble implements InitializingBean {
	
	@Autowired
	private GetEntityManagerFactoryStrategy getEntityManagerFactoryStrategy;

	@Override
	public void afterPropertiesSet() throws Exception {
		JpaUtil.setGetEntityManagerFactoryStrategy(getEntityManagerFactoryStrategy);
	}
}
