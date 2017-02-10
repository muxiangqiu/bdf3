package com.bstek.bdf3.dbconsole.utils;

import com.bstek.bdf3.dbconsole.DbConstants;
import com.bstek.dorado.core.Configure;
import com.bstek.dorado.core.el.Expression;
import com.bstek.dorado.core.el.ExpressionHandler;
import com.bstek.dorado.web.DoradoContext;

public class UserConfigUtils {

	/**
	 * 获取登录用户
	 * 
	 * @return 返回用户名
	 */
	public static String getUserName() {
		ExpressionHandler expressionHandler;
		try {
			expressionHandler = (ExpressionHandler) DoradoContext.getCurrent().getServiceBean("expressionHandler");
			String el = "${" + Configure.getString(DbConstants.DEFAULT_LOGIN_USERNAME_ATTR) + "}";
			Expression expression = expressionHandler.compile(el);
			if (expression == null) {
				return el;
			}
	        return (String) expression.evaluate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
