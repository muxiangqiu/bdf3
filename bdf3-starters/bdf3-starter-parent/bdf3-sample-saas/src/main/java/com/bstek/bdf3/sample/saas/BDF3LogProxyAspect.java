package com.bstek.bdf3.sample.saas;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.log.AbstractLogProxyAspect;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年1月11日
 */
@Aspect
@Component
public class BDF3LogProxyAspect extends AbstractLogProxyAspect {

	@Override
	@Pointcut("@annotation(com.bstek.dorado.annotation.DataResolver)")
	protected void logPointcut() {

	}

}
