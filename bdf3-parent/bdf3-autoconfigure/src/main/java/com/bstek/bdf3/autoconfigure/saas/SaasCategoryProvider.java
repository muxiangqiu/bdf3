package com.bstek.bdf3.autoconfigure.saas;

import com.bstek.bdf3.notice.strategy.CategoryProvider;
import com.bstek.bdf3.saas.SaasUtils;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
public class SaasCategoryProvider implements CategoryProvider {

	@Override
	public String provide() {
		return SaasUtils.getLoginOrgId();
	}

}
