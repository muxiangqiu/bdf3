package com.bstek.bdf3.dictionary.ui.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dictionary.domain.Dictionary;
import com.bstek.bdf3.dictionary.domain.DictionaryItem;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月7日
 */
@Service("ui.dictionaryService")
@Transactional(readOnly = true)
public class DictionaryServiceImpl implements DictionaryService {
	
	@Override
	public void load(Page<Dictionary> page) {
		JpaUtil
			.linq(Dictionary.class)
			.isNull("parentId")
			.paging(page);	
	}

	@Override
	public List<Dictionary> loadChildren(String parentId) {
		return JpaUtil
				.linq(Dictionary.class)
				.equal("parentId", parentId)
				.list();
	}

	@Override
	public List<DictionaryItem> loadDictionaryItems(String dictionaryId) {
		return JpaUtil
				.linq(DictionaryItem.class)
				.equal("dictionaryId", dictionaryId)
				.isNull("parentId")
				.list();
	}

	@Override
	public List<DictionaryItem> loadDictionItemChildren(String parentId) {
		return JpaUtil
				.linq(DictionaryItem.class)
				.equal("parentId", parentId)
				.list();
	}
	
	@Override
	public boolean isExists(String code) {
		return JpaUtil
				.linq(Dictionary.class)
				.equal("code", code)
				.exists();
	}

	@Override
	@Transactional
	public void save(List<Dictionary> dictionaries) {
		JpaUtil.save(dictionaries);
		
	}

}
