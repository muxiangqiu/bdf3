package com.bstek.bdf3.dictionary.ui.service;

import java.util.List;

import com.bstek.bdf3.dictionary.domain.Dictionary;
import com.bstek.bdf3.dictionary.domain.DictionaryItem;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月7日
 */
public interface DictionaryService {
	
	void load(Page<Dictionary> page);
	
	List<Dictionary> loadChildren(String parentId);
	
	List<DictionaryItem> loadDictionaryItems(String dictionaryId);
	
	List<DictionaryItem> loadDictionItemChildren(String parentId);
	
	boolean isExists(String code);
	
	void save(List<Dictionary> dictionaries);

}
