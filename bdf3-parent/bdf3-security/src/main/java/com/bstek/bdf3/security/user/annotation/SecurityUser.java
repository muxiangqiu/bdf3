package com.bstek.bdf3.security.user.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 安全用户类型识别注解
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月26日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SecurityUser {
	/**
	 * 自定义用户修改页面地址
	 * @return 用户修改页面地址
	 */
	String modifyUserUrl() default "";
}
