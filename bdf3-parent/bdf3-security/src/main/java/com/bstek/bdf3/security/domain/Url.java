package com.bstek.bdf3.security.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.access.ConfigAttribute;

/**
 * 菜单信息
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年1月25日
 */
@Entity
@Table(name = "BDF3_URL")
public class Url implements Resource {
	

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单资源类型
	 */
	public static final String RESOURCE_TYPE = "URL";
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "NAME_", length = 64)
	private String name;
	
	@Column(name = "ICON_", length = 255)
	private String icon;
	
	@Column(name = "PATH_", length = 512)
	private String path;
	
	@Column(name = "PARENT_ID_", length = 64)
	private String parentId;
	
	@Column(name = "ORDER_")
	private Integer order;
	
	@Column(name = "NAVIGABLE_")
	private boolean navigable;
	
	@Column(name = "DESCRIPTION_", length = 255)
	private String description;
	
	@Transient
	private List<Url> children;
	
	@Transient
	private List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 名称
	 * @return name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 图标
	 * @return icon
	 */
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 路径
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 双亲菜单ID
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 排序号，小的在前
	 * @return order
	 */
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * 可导航，true为可以在导航菜单出现，否则不能
	 * @return navigable
	 */
	public boolean isNavigable() {
		return navigable;
	}

	public void setNavigable(boolean navigable) {
		this.navigable = navigable;
	}

	/**
	 * 描述
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 子级菜单
	 * @return children
	 */
	public List<Url> getChildren() {
		return children;
	}

	public void setChildren(List<Url> children) {
		this.children = children;
	}

	public List<ConfigAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ConfigAttribute> attributes) {
		this.attributes = attributes;
	}
	
	
	
	

}
