package com.bstek.bdf3.message.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bstek.bdf3.jpa.AdditionalSupport;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月4日
 */
@Entity
@Table(name = "BDF3_NOTIFY")
public class Notify extends AdditionalSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "TITLE_", length = 255)
	private String title;
	
	@Column(name = "CONTENT_")
	@Lob
	private String content;
	
	@Column(name = "TYPE_")
	private NotifyType type;
	
	@Column(name = "TARGET_", length = 64)
	private String target;
	
	@Column(name = "TARGET_TYPE_", length = 64)
	private String targetType;
	
	@Column(name = "ACTION_", length = 64)
	private String action;
	
	@Column(name = "SENDER_", length = 64)
	private String sender;
	
	@Column(name = "GROUP_", length = 64)
	private String group;
	
	@Column(name = "CREATED_AT_")
	private Date createdAt;
	
	@Transient
	private UserNotify userNotify;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public NotifyType getType() {
		return type;
	}

	public void setType(NotifyType type) {
		this.type = type;
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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public UserNotify getUserNotify() {
		return userNotify;
	}

	public void setUserNotify(UserNotify userNotify) {
		this.userNotify = userNotify;
	}
	
	
	

	
}
