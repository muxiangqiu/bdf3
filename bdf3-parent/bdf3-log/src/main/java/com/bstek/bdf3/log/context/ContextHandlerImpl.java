package com.bstek.bdf3.log.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bstek.dorado.core.el.Expression;
import com.bstek.dorado.core.el.ExpressionHandler;


/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
@Component
public class ContextHandlerImpl implements ContextHandler{

	@Autowired
	@Qualifier("dorado.expressionHandler")
	private ExpressionHandler expressionHandler;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T compile(String value) {
		Expression expression = expressionHandler.compile(value);
		if (expression == null) {
			return (T) value;
		}
        return (T) expression.evaluate();
	}

	public void setExpressionHandler(ExpressionHandler expressionHandler) {
		this.expressionHandler = expressionHandler;
	}

	@Override
	public void set(String key, Object value) {
		expressionHandler.getJexlContext().set(key, value);
	}

	@Override
	public Object get(String key) {
		return expressionHandler.getJexlContext().get(key);
	}

	@Override
	public boolean has(String key) {
		return expressionHandler.getJexlContext().has(key);
	}

}
