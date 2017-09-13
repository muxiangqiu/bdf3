package com.bstek.bdf3.notice.ui.strategy;

import org.springframework.stereotype.Component;

import com.bstek.bdf3.notice.strategy.CategoryProvider;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月13日
 */
@Component
public class StaticCategoryProvider implements CategoryProvider {

	@Override
	public String provide() {
		return "bdf3";
	}

}
