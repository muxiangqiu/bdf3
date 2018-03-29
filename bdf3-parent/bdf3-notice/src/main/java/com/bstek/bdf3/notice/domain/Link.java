package com.bstek.bdf3.notice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:muxiangqiu@gmail.com)
 * @since 2018年3月25日
 */
@Entity
@Table(name = "BDF3_LINK")
public class Link {
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "NAME_")
	private String name;
	
	@Column(name = "ICON_")
	private String icon;
	
	@Column(name = "GROUP_ID_", length = 64)
	private String groupId;
	
	@Column(name = "URL_", length = 512)
	private String url;

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
