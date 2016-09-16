package com.bstek.bdf3.message.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月4日
 */
@Entity
@Table(name = "BDF3_NOTIFY_CONFIG")
public class NotifyConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "REASON_", length = 64)
	private String reason;
	
	@Column(name = "ACTION_", length = 64)
	private String action;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	

	
	

}
