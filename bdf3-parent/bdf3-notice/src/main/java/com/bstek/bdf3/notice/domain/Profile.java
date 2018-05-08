package com.bstek.bdf3.notice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:muxiangqiu@gmail.com)
 * @since 2018年3月27日
 */
@Entity
@Table(name = "BDF3_PROFILE")
public class Profile {
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "TEMPLATE_ID_", length = 64)
	private String templateId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	
	
	
}
