package com.bstek.bdf3.dictionary.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dictionary.domain.Dictionary;
import com.bstek.bdf3.dictionary.domain.DictionaryItem;
import com.bstek.bdf3.jpa.JpaUtil;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月7日
 */
@Service
@Transactional(readOnly = true)
public class DictionaryServiceImpl implements DictionaryService {

	@Override
	public Dictionary getDictionaryBy(String code) {
		return JpaUtil.linq(Dictionary.class).equal("code", code).findOne();
	}

	@Override
	public DictionaryItem getDefaultValueItemBy(String code) {
		
		return JpaUtil
				.linq(DictionaryItem.class)
				.isTrue("enabled")
				.exists(Dictionary.class)
					.equal("code", code)
					.equalProperty("defaultValue", "key")
					.equalProperty("id", "dictionaryId")
				.end()
				.findOne();
	}

	@Override
	public String getDefaultValueBy(String code) {
		return getDefaultValueItemBy(code).getValue();
	}
	
	@Override
	public String getDefaultKeyBy(String code) {
		return getDefaultValueItemBy(code).getKey();
	}

	@Override
	public List<DictionaryItem> getDictionaryItemsBy(String code) {
		List<DictionaryItem> list = JpaUtil
				.linq(DictionaryItem.class)
				.isTrue("enabled")
				.exists(Dictionary.class)
					.equal("code", code)
					.equalProperty("id", "dictionaryId")
				.end()
				.asc("order")
				.list();
		Map<String, List<DictionaryItem>> map = JpaUtil.classify(list, "parentId");
		List<DictionaryItem> top = map.get(null);
		if (top != null) {
			for (DictionaryItem item : top) {
				item.setChildren(map.get(item.getId()));
			}
		}
		
		return top;
	}

	@Override
	public DictionaryItem getDictionaryItem(String key) {
		return JpaUtil
				.linq(DictionaryItem.class)
				.isTrue("enabled")
				.equal("key", key)
				.findOne();
	}

}
