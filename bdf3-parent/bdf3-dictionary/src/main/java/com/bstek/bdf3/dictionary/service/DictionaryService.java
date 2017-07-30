package com.bstek.bdf3.dictionary.service;

import java.util.List;

import com.bstek.bdf3.dictionary.domain.Dictionary;
import com.bstek.bdf3.dictionary.domain.DictionaryItem;

/**
 * 字典服务类
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月7日
 */
public interface DictionaryService {
	
	/**
	 * 根据字典编码获取字典
	 * @param code 字典编码
	 * @return 字典
	 */
	Dictionary getDictionaryBy(String code);
	
	/**
	 * 根据字典编码获取字典的默认字典项
	 * @param code 字典编码
	 * @return 字典项
	 */
	DictionaryItem getDefaultValueItemBy(String code);
	
	/**
	 * 根据字典编码获取字典的默认字典项的值
	 * @param code 字典编码
	 * @return 字典项的值
	 */
	String getDefaultValueBy(String code);
	
	/**
	 * 根据字典编码获取字典的所有字典项（包括字典项的子级字典项的树型结构）
	 * @param code 字典编码
	 * @return 所有字典项
	 */
	List<DictionaryItem> getDictionaryItemsBy(String code);

	/**
	 * 根据字典项的键获取字典项
	 * @param key 字典项的键
	 * @return 字典项
	 */
	DictionaryItem getDictionaryItem(String key);

	/**
	 * 根据字典项的键获取字典项的键
	 * @param code 字典编码
	 * @return 字典项的键
	 */
	String getDefaultKeyBy(String code);

}
