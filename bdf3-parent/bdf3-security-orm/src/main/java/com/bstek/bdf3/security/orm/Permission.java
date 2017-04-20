package com.bstek.bdf3.security.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.access.ConfigAttribute;

/**
 * 权限信息
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月20日
 */
@Entity
@Table(name = "BDF3_PERMISSION")
public class Permission implements ConfigAttribute{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "ROLE_ID_", length = 64)
	private String roleId;
	
	@Column(name = "RESOURCE_ID_", length = 64)
	private String resourceId;
	
	@Column(name = "RESOURCE_TYPE_", length = 32)
	private String resourceType;
	
	@Column(name = "ATTRIBUTE_", length = 255)
	private String attribute;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 角色ID
	 * @return roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 资源ID，如菜单和组件，以及用户定义的资源
	 * @return resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}
	
	/**
	 * 资源类型，菜单为URL，组件为COMPONENT
	 * @return resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@Override
	/**
	 * 权限鉴别属性，默认格式：ROLE_{roleId}，可以扩展
	 * @return attribute
	 */
	public String getAttribute() {
		return attribute;
	}

}
