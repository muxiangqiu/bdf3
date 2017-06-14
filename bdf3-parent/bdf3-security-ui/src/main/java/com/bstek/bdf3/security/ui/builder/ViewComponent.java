package com.bstek.bdf3.security.ui.builder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年3月6日
 */
public class ViewComponent {
	private String id;
	private String icon;
	private String desc;
	private boolean enabled = true;
	private boolean sortabled = false;
	private boolean use;
	private String name;
	private Collection<ViewComponent> children = new ArrayList<ViewComponent>();

	public Collection<ViewComponent> getChildren() {
		return children;
	}

	public void setChildren(Collection<ViewComponent> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isSortabled() {
		return sortabled;
	}

	public void setSortabled(boolean sortabled) {
		this.sortabled = sortabled;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

	public void addChildren(ViewComponent component) {
		this.children.add(component);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	

}
