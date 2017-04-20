package com.bstek.bdf3.security.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.access.ConfigAttribute;

/**
 * 组件信息
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月20日
 */
@Entity
@Table(name = "BDF3_COMPONENT")
public class Component implements Resource{
	
	private static final long serialVersionUID = 1L;
	
	public static final String RESOURCE_TYPE = "COMPONENT";
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "COMPONENT_ID_", length = 255)
	private String componentId;
	
	@Column(name = "COMPONENT_TYPE", length = 64)
	@Enumerated(EnumType.STRING)
	private ComponentType componentType;
	
	@Column(name = "NAME_", length = 64)
	private String name;
	
	@Column(name = "URL_ID_", length = 64)
	private String urlId;
	
	@Column(name = "PATH_", length = 512)
	private String path;
	
	@Column(name = "DESCRIPTION_", length = 512)
	private String description;
	
	@Transient
	private boolean authorized;
	
	@Transient
	private String configAttributeId;
	
	@Transient
	private List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 组件ID
	 * @return componentId
	 */
	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	/**
	 * 组件类型
	 * @return componentType
	 */
	public ComponentType getComponentType() {
		return componentType;
	}

	public void setComponentType(ComponentType componentType) {
		this.componentType = componentType;
	}

	/**
	 * 组件名称
	 * @return name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 组件所在页面的菜单ID
	 * @return urlId
	 */
	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	/**
	 * 组件所在页面的URL
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 组件描述
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAttributes(List<ConfigAttribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public List<ConfigAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public String toString() {
		if (componentId == null) {
			return path;
		}
		return path + "#" + componentId;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public String getConfigAttributeId() {
		return configAttributeId;
	}

	public void setConfigAttributeId(String configAttributeId) {
		this.configAttributeId = configAttributeId;
	}
	
	

}
