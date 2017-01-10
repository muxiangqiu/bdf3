package com.bstek.bdf3.importer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstek.dorado.annotation.PropertyDef;

/**
 *@author Kevin.yang
 *@since 2015年8月22日
 */
@Entity
@Table(name = "BDF3_MAPPING_RULE")
public class Entry implements java.io.Serializable {

	private static final long serialVersionUID = -6974265493073776949L;

	@Id
	@Column(name = "ID_", length = 36)
	@PropertyDef(label = "ID")
	private String id;
	
	@PropertyDef(label = "关键字")
	@Column(name = "KEY_", length = 100)
	private String key;
	
	@Column(name = "VALUE_", length = 100)
	@PropertyDef(label = "值")
	private String value;
	
	@Column(name = "MAPPING_RULE_ID_", length = 36)
	@PropertyDef(label = "映射规则ID")
	private String mappingRuleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMappingRuleId() {
		return mappingRuleId;
	}

	public void setMappingRuleId(String mappingRuleId) {
		this.mappingRuleId = mappingRuleId;
	}
	
	
	
	
	
	
	

}
