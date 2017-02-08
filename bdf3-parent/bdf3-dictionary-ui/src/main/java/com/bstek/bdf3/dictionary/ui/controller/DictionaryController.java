package com.bstek.bdf3.dictionary.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.dictionary.domain.Dictionary;
import com.bstek.bdf3.dictionary.domain.DictionaryItem;
import com.bstek.bdf3.dictionary.ui.service.DictionaryService;
import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Page;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月7日
 */
@Controller
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;
	
	@DataProvider
	public void load(Page<Dictionary> page) {
		dictionaryService.load(page);
	}

	@DataProvider
	public List<Dictionary> loadChildren(String parentId) {
		return dictionaryService.loadChildren(parentId);
	}

	@DataProvider
	public List<DictionaryItem> loadDictionaryItems(String dictionaryId) {
		return dictionaryService.loadDictionaryItems(dictionaryId);
	}

	@DataProvider
	public List<DictionaryItem> loadDictionaryItemChildren(String parentId) {
		return dictionaryService.loadDictionItemChildren(parentId);
	}
	
	@Expose
	public String isExists(String code) {
		if (dictionaryService.isExists(code)) {
			return "编码已存在。";
		}
		return null;
	}

	@DataResolver
	public void save(List<Dictionary> dictionaries) {
		dictionaryService.save(dictionaries);
		
	}
}
