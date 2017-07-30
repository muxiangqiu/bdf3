package com.bstek.bdf3.log.context.provider;

import com.bstek.bdf3.log.annotation.LogDefinition;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
public interface ContextProvider {
	
	String SMART_CONTEXT_PROVIDER = "SMART";
	String JOIN_POINT = "JOIN_POINT";
	String LOG_DEFINITION = "LOG_DEFINITION";
	String TARGET = "TARGET";
	String ENTITY_STATE = "entityState";
	String VAR = "entity";
	String ARGS = "args";
	String RETURN_VALUE = "returnValue";

	Object getContext();
	
	boolean support(LogDefinition logDefinition);
}
