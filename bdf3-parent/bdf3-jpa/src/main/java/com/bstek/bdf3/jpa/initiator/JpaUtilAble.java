package com.bstek.bdf3.jpa.initiator;

import org.springframework.context.ApplicationContext;

/**
 * JpaUtil可用接口
 * JpaUtil在启动时候会进行初始化动作，初始化之前用JpaUtil<br>
 * 会抱错，如果需要在项目启动的时候用JpaUtil,可以通过实现JpaUtilAble接口，<br>
 * 从而确保JpaUtil已经被初始化
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月3日
 */
public interface JpaUtilAble {
	void afterPropertiesSet(ApplicationContext applicationContext);
}
