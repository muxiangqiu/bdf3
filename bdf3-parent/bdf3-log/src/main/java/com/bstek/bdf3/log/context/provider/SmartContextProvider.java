package com.bstek.bdf3.log.context.provider;

import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.log.annotation.LogDefinition;
import com.bstek.bdf3.log.context.ContextHandler;
import com.bstek.dorado.data.provider.Page;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
@Component
public class SmartContextProvider implements ContextProvider {

	@Autowired
	protected ContextHandler contextHandler;

	@Override
	public Object getContext() {
		JoinPoint joinPoint = (JoinPoint) contextHandler.get(JOIN_POINT);
		LogDefinition logDefinition = (LogDefinition) contextHandler.get(LOG_DEFINITION);
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			for (Object arg : args) {
				if (arg instanceof Page) {
					return ((Page<?>)arg).getEntities();
				}
			}
			if (args[0] instanceof Collection) {
				if (LogDefinition.DISABLED.equals(logDefinition.getDisabled())) {
					logDefinition.setDisabled("${" + ContextProvider.ENTITY_STATE + "=='NONE'}");
				}
				return args[0];
			}
		}
		Object returnValue = contextHandler.get(RETURN_VALUE);
		return returnValue;
	}

	@Override
	public boolean support(LogDefinition logDefinition) {
		return SMART_CONTEXT_PROVIDER.equals(logDefinition.getContext());
	}
	
	public void setContextHandler(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

}
