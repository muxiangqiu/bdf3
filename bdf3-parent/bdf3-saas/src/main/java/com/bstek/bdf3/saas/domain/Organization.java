package com.bstek.bdf3.saas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月10日
 */
@Entity
@Table(name = "BDF3_ORGANIZATION")
public class Organization implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_")
	private String id;
	
	@Column(name = "NAME_")
	private String name;
	
	@Column(name = "DATA_SOURCE_INFO_ID_")
	private String dataSourceInfoId;
	
	@Transient
	private DataSourceInfo dataSourceInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataSourceInfoId() {
		return dataSourceInfoId;
	}

	public void setDataSourceInfoId(String dataSourceInfoId) {
		this.dataSourceInfoId = dataSourceInfoId;
	}

	public DataSourceInfo getDataSourceInfo() {
		return dataSourceInfo;
	}

	public void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
		this.dataSourceInfo = dataSourceInfo;
	}


}
