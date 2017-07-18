package com.bstek.bdf3.profile.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BDF3_COMP_CFG")
public class ComponentConfig implements java.io.Serializable, Cloneable {
	
	private static final long serialVersionUID = 400562346546025288L;
	public static final String HIDE_MODE_VISIBILITY = "visibility";
	public static final String HIDE_MODE_DISPLAY = "display";

	@Id
	@Column(name = "ID_", length = 50)
	private String id;

	@Column(name = "CONTROL_ID_")
	private String controlId;

	@Column(name = "NAME_", length = 50)
	private String name;

	@Column(name = "HIDE_MODE_", length = 10)
	private String hideMode;

	@Column(name = "COLS_", length = 40)
	private String cols;

	@Column(name = "META1_", length = 50)
	private String meta1;

	@Column(name = "META2_", length = 50)
	private String meta2;

	@Column(name = "META3_", length = 50)
	private String meta3;

	@Transient
	private List<ComponentConfigMember> componentConfigMembers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getControlId() {
		return controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	public String getHideMode() {
		return hideMode;
	}

	public void setHideMode(String hideMode) {
		this.hideMode = hideMode;
	}

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public String getMeta1() {
		return meta1;
	}

	public void setMeta1(String meta1) {
		this.meta1 = meta1;
	}

	public String getMeta2() {
		return meta2;
	}

	public void setMeta2(String meta2) {
		this.meta2 = meta2;
	}

	public String getMeta3() {
		return meta3;
	}

	public void setMeta3(String meta3) {
		this.meta3 = meta3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ComponentConfigMember> getComponentConfigMembers() {
		return componentConfigMembers;
	}

	public void setComponentConfigMembers(List<ComponentConfigMember> componentConfigMembers) {
		this.componentConfigMembers = componentConfigMembers;
	}


}
