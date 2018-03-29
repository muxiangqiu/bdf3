package com.bstek.bdf3.notice.domain;
/**
 * @author Kevin Yang (mailto:muxiangqiu@gmail.com)
 * @since 2018年3月27日
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BDF3_GROUP_TEMPLATE")
public class GroupTemplate {
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "GROUP_ID_", length = 64)
	private String groupId;
	
	@Column(name = "TEMPLATE_ID_", length = 64)
	private String templateId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
