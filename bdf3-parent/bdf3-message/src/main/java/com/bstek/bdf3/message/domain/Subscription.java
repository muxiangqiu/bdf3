package com.bstek.bdf3.message.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月4日
 */
@Entity
@Table(name = "BDF3_SUBSCRIPTION")
public class Subscription implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "TARGET_", length = 64)
	private String target;
	
	@Column(name = "TARGET_TYPE_", length = 64)
	private String targetType;
	
	@Column(name = "ACTION_", length = 64)
	private String action;
	
	@Column(name = "USER_", length = 64)
	private String user;
	
	@Column(name = "CREATED_AT_")
	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	
	
	

}
