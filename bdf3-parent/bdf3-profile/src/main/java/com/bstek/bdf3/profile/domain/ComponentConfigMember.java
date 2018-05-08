package com.bstek.bdf3.profile.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BDF3_COMP_MEMB")
public class ComponentConfigMember implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = -7658024545081912471L;

	@Id
	@Column(name = "ID_", length = 50)
	private String id;

	@Column(name = "CONTROL_NAME_", length = 50)
	private String controlName;

	@Column(name = "CONTROL_TYPE_", length = 20)
	private String controlType;

	@Column(name = "ORDER_")
	private Integer order;

	@Column(name = "PARENT_CONTROL_NAME_", length = 50)
	private String parentControlName;

	@Column(name = "CAPTION_", length = 50)
	private String caption;

	@Column(name = "WIDTH_", length = 50)
	private String width;

	@Column(name = "COL_SPAN_")
	private Integer colSpan;

	@Column(name = "ROW_SPAN_")
	private Integer rowSpan;

	@Column(name = "VISIBLE_")
	private Boolean visible;
	
	@Column(name = "COMPONENT_CONFIG_ID_", length = 50)
	private String componentConfigId;

	@Transient
	private List<ComponentConfigMember> children;

	@Transient
	private ComponentConfig componentConfig;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getControlName() {
		return this.controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getControlType() {
		return this.controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getParentControlName() {
		return this.parentControlName;
	}

	public void setParentControlName(String parentControlName) {
		this.parentControlName = parentControlName;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Integer getColSpan() {
		return this.colSpan;
	}

	public void setColSpan(Integer colSpan) {
		this.colSpan = colSpan;
	}

	public Integer getRowSpan() {
		return this.rowSpan;
	}

	public void setRowSpan(Integer rowSpan) {
		this.rowSpan = rowSpan;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<ComponentConfigMember> getChildren() {
		return children;
	}

	public void setChildren(List<ComponentConfigMember> children) {
		this.children = children;
	}

	public void addChildren(ComponentConfigMember member) {
		if (this.children == null) {
			this.children = new ArrayList<ComponentConfigMember>();
		}
		this.children.add(member);
	}

	public ComponentConfig getComponentConfig() {
		return componentConfig;
	}

	public void setComponentConfig(ComponentConfig componentConfig) {
		this.componentConfig = componentConfig;
	}
	
	public String getComponentConfigId() {
		return componentConfigId;
	}

	public void setComponentConfigId(String componentConfigId) {
		this.componentConfigId = componentConfigId;
	}

}
