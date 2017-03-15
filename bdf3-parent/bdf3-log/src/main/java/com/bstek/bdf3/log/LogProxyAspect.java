package com.bstek.bdf3.log;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月11日
 */
@Aspect
@Component
public class LogProxyAspect extends AbstractLogProxyAspect {

	@Override
	@Pointcut("@annotation(com.bstek.dorado.annotation.DataResolver)")
	protected void logPointcut() {

	}

}
