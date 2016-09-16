package com.bstek.bdf3.security.user.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月26日
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nickname {
	String label() default "昵称";
}
