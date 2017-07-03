package com.bstek.bdf3.saas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年8月10日
 */
@Entity
@Table(name = "BDF3_DATA_SOURCE_INFO")
public class DataSourceInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_")
	private String id;
	
	@Column(name = "NAME_")
	private String name;
	
	@Column(name = "URL_")
	private String url;
	
	@Column(name = "USERNAME_")
	private String username;
	
	@Column(name = "PASSWORD_")
	private String password;
	
	@Column(name = "TYPE_")
	private String type;
	
	@Column(name = "DRIVERCLASSNAME_")
	private String driverClassName;
	
	@Column(name = "JNDINAME_")
	private String jndiName;
	
	@Column(name = "SHARED_")
	private boolean shared;

	@Column(name = "ENABLED_")
	private boolean enabled;
	
	@Column(name = "DEPLETION_INDEX_")
	private Integer depletionIndex = 0;

	

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getDepletionIndex() {
		return depletionIndex;
	}

	public void setDepletionIndex(Integer depletionIndex) {
		this.depletionIndex = depletionIndex;
	}


	
	
	
	

}
