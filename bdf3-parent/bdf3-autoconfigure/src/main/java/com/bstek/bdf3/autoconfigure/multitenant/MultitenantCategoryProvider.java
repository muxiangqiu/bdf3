package com.bstek.bdf3.autoconfigure.multitenant;

import org.malagu.multitenant.MultitenantUtils;

import com.bstek.bdf3.notice.strategy.CategoryProvider;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
public class MultitenantCategoryProvider implements CategoryProvider {

	@Override
	public String provide() {
		return MultitenantUtils.getLoginOrgId();
	}

}
