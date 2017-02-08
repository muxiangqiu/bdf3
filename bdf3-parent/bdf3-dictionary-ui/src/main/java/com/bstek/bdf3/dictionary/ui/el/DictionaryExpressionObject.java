package com.bstek.bdf3.dictionary.ui.el;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.dictionary.domain.DictionaryItem;
import com.bstek.bdf3.dictionary.service.DictionaryService;

/**
 * 字典表达式辅助对象
 * 
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月8日
 */
@Component
public class DictionaryExpressionObject {

	@Autowired
	private DictionaryService dictionaryService;
	
	public List<DictionaryItem> items(String code) {		
		return dictionaryService.getDictionaryItemsBy(code);
	}
	
	public String defaultValue(String code) {		
		return dictionaryService.getDefaultValueBy(code);
	}
	
	public DictionaryItem defaultValueItem(String code) {		
		return dictionaryService.getDefaultValueItemBy(code);
	}
	
	public DictionaryItem item(String key) {
		return dictionaryService.getDictionaryItem(key);
	}
	
	
}
