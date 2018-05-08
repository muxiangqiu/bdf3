package com.bstek.bdf3.autoconfigure.multitenant;

import org.malagu.multitenant.domain.Organization;
import org.malagu.multitenant.strategy.CurrentOrganizationStrategy;

import com.bstek.bdf3.security.ContextUtils;
import com.bstek.bdf3.security.orm.User;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年12月14日
 */
public class CurrentOrganizationStrategyImpl implements CurrentOrganizationStrategy {

	@Override
	public Organization getCurrent() {
		User user = ContextUtils.getLoginUser();
		if (user != null) {
			return user.getOrganization();
		}
		return null;
	}

}
