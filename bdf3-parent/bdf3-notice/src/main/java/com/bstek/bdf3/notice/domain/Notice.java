package com.bstek.bdf3.notice.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
@Entity
@Table(name = "BDF3_NOTICE")
public class Notice {

	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "TITLE_", length = 255)
	private String title;
	
	@Column(name = "CONTENT_", length = 512)
	private String content;
	
	@Column(name = "SENDER_", length = 64)
	private String sender;
	
	@Column(name = "TYPE_", length = 64)
	private String type;
	
	@Column(name = "SEND_TIME_")
	private Date sendTime;
	
	@Column(name = "EXPIRE_TIME_")
	private Date expireTime;
	
	@Column(name = "GROUP_ID_", length = 64)
	private String groupId;
	
	@Column(name = "ALL_")
	private boolean all;

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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
