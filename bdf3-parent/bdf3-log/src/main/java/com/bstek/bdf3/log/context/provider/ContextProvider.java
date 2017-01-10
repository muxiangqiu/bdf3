package com.bstek.bdf3.log.context.provider;

import com.bstek.bdf3.log.annotation.LogDefinition;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
public interface ContextProvider {
	
	String SMART_CONTEXT_PROVIDER = "SMART";
	String JOIN_POINT = "JOIN_POINT";
	String RETURN_VALUE = "RETURN_VALUE";
	String LOG_DEFINITION = "LOG_DEFINITION";
	String TARGET = "TARGET";
	String ENTITY_STATE = "entityState";
	String VAR = "entity";

	Object getContext();
	
	boolean support(LogDefinition logDefinition);
}
