package com.bstek.bdf3.log;

import java.util.Collection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bstek.bdf3.log.annotation.Log;
import com.bstek.bdf3.log.annotation.LogDefinition;
import com.bstek.bdf3.log.context.ContextHandler;
import com.bstek.bdf3.log.context.provider.ContextProvider;
import com.bstek.bdf3.log.logger.Logger;

/**
 *@author Kevin.yang
 *@since 2015年7月19日
 */
@Aspect
public class LogAspect implements ApplicationContextAware{
	protected ContextHandler contextHandler;
	protected Collection<ContextProvider> providers;
	protected ApplicationContext applicationContext;
	private boolean disabled;
	
	@Pointcut("@annotation(com.bstek.bdf3.log.annotation.NotLog)")
	public void notLog(){}
	
	@Pointcut("within(com.bstek.bdf3.log.proxy.LogProxy)")
	public void logProxy(){}
	
	@Pointcut(value = "@within(com.bstek.bdf3.log.annotation.Log)")
	public void logType(){}
	
	@Pointcut(value = "@annotation(com.bstek.bdf3.log.annotation.Log)")
	public void logMethod(){}
	
	@Pointcut(value = "logType() && logMethod() && !logProxy()")
	public void logTypeAndMethod(){}
	
	@Pointcut(value = "logType() && !logMethod() && !notLog() && !logProxy()")
	public void logOnlyType(){}
	
	@Pointcut(value = "!logType() && logMethod() && !logProxy()")
	public void logOnlyMethod(){}
	
	@Around(value = "logOnlyMethod() && @annotation(log)", argNames = "log")
	public Object logOnlyMethodAround(ProceedingJoinPoint joinPoint, Log log) throws Throwable{
		LogDefinition logDefinition =LogUtils.buildLogDefinition(log);
		return doLogAround(logDefinition, joinPoint);
	}
	
	@Around(value = "logMethod() && @target(log)", argNames = "log")
	public Object logOnlyTypeAround(ProceedingJoinPoint joinPoint, Log log) throws Throwable{
		LogDefinition logDefinition =LogUtils.buildLogDefinition(log);
		return doLogAround(logDefinition, joinPoint);
	}
	
	@Around(value = "logMethod() && @target(typeLog) && @annotation(methodLog)", argNames = "typeLog,methodLog")
	public Object logTypeMethodAround(ProceedingJoinPoint joinPoint, Log typeLog, Log methodLog) throws Throwable{
		LogDefinition logDefinition =LogUtils.buildLogDefinition(typeLog, methodLog);
		return doLogAround(logDefinition, joinPoint);
	}
	
	private Object doLogAround(LogDefinition logDefinition,
			ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = joinPoint.proceed();
		if (!disabled) {
			contextHandler.set(ContextProvider.LOG_DEFINITION, logDefinition);
			contextHandler.set(ContextProvider.JOIN_POINT, joinPoint);
			contextHandler.set(ContextProvider.RETURN_VALUE, returnValue);
			Logger logger = (Logger) applicationContext.getBean(logDefinition.getLogger());
			for (ContextProvider provider : providers) {
				if (provider.support(logDefinition)) {
					contextHandler.set(ContextProvider.TARGET, provider.getContext());
					logger.log();
					break;
				}
			}
		}
		return returnValue;
	}

	public void setContextHandler(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		providers = applicationContext.getBeansOfType(ContextProvider.class).values();
		
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	

}
