package com.bstek.bdf3.dictionary.service;

import java.util.List;

import com.bstek.bdf3.dictionary.domain.Dictionary;
import com.bstek.bdf3.dictionary.domain.DictionaryItem;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月7日
 */
public interface DictionaryService {
	
	Dictionary getDictionaryBy(String code);
	
	DictionaryItem getDefaultValueItemBy(String code);
	
	String getDefaultValueBy(String code);
	
	List<DictionaryItem> getDictionaryItemsBy(String code);

	DictionaryItem getDictionaryItem(String key);

}
