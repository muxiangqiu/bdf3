package com.bstek.bdf3.dictionary.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年2月7日
 */
@Entity
@Table(name = "BDF3_DICTIONARY")
public class Dictionary implements Serializable{

	private static final long serialVersionUID = -278246679080521810L;

	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "CODE_", length = 64)
	private String code;
	
	@Column(name = "NAME_")
	private String name;
	
	@Column(name = "DEFAULT_VALUE_", length = 64)
	private String defaultValue;
	
	@Column(name = "PARENT_ID_", length = 64)
	private String parentId;
	
	@Transient
	private List<Dictionary> children;
	
	@Transient
	private List<DictionaryItem> dictionaryItems;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Dictionary> getChildren() {
		return children;
	}

	public void setChildren(List<Dictionary> children) {
		this.children = children;
	}

	public List<DictionaryItem> getDictionaryItems() {
		return dictionaryItems;
	}

	public void setDictionaryItems(List<DictionaryItem> dictionaryItems) {
		this.dictionaryItems = dictionaryItems;
	}
	
	
}
