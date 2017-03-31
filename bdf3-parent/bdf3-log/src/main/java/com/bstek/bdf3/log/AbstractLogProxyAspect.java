package com.bstek.bdf3.log;

import java.lang.reflect.Method;
import java.util.Collection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.bdf3.log.annotation.Log;
import com.bstek.bdf3.log.proxy.LogProxy;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
public abstract class AbstractLogProxyAspect implements ApplicationContextAware{

	protected Collection<LogProxy> proxies;
	
	@Autowired
	protected LogAspect logAspect;
	
	@Value("${bdf3.log.disabled}")
	private boolean disabled;

	abstract protected void logPointcut();
	
	@Around("logPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		if (!disabled) {
			for (LogProxy proxy : proxies) {
				if (proxy.support(joinPoint.getTarget())) {
					Class<?> targetCalss = AopUtils.getTargetClass(proxy);
					Log methodLog = null;
					Log typeLog = targetCalss.getAnnotation(Log.class);
					Method[] methods = targetCalss.getDeclaredMethods();
					for (Method method : methods) {
						if (method.getName().equals(joinPoint.getSignature().getName())) {
							methodLog = method.getAnnotation(Log.class);
							break;
						}
					}
					
					if (methodLog != null && typeLog != null) {
						return logAspect.logTypeMethodAround(joinPoint, typeLog, methodLog);
					} else if (methodLog != null) {
						return logAspect.logOnlyMethodAround(joinPoint, methodLog);
					} else if (typeLog != null) {
						return logAspect.logOnlyTypeAround(joinPoint, typeLog);
					}
				}
			}
		}
		return joinPoint.proceed();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		proxies = applicationContext.getBeansOfType(LogProxy.class).values();
		
	}

	
}
