package com.bstek.bdf3.dictionary.ui.el;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bstek.dorado.core.el.ContextVarsInitializer;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月8日
 */
public class DictionaryContextVarsInitializer implements ContextVarsInitializer {
	
	@Autowired
	private DictionaryExpressionObject dictionaryExpressionObject;

	@Override
	public void initializeContext(Map<String, Object> vars) throws Exception {
		vars.put("dict", dictionaryExpressionObject);
	}

}
