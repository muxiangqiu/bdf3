package com.bstek.bdf3.profile.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bstek.dorado.core.el.Expression;
import com.bstek.dorado.core.el.ExpressionHandler;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月18日
 */
@Component
public class ProfileKeyProviderImpl implements ProfileKeyProvider {
	
	@Autowired
	@Qualifier("dorado.expressionHandler")
	private ExpressionHandler expressionHandler;
	
	@Value("${bdf3.profile.profileKeyEl}")
	private String profileKeyEl;

	@Override
	public String getProfileKey() {
		Expression expression = expressionHandler.compile("${" + profileKeyEl + "}");
		if (expression == null) {
			return profileKeyEl;
		}
        return (String) expression.evaluate();
	}

}
