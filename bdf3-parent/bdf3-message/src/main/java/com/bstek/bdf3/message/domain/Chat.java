package com.bstek.bdf3.message.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bstek.bdf3.jpa.AdditionalSupport;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年9月18日
 */
@Entity
@Table(name = "BDF3_CHAT")
public class Chat extends AdditionalSupport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "SENDER_", length = 64)
	private String sender;
	
	@Column(name = "RECEIVER_", length = 64)
	private String receiver;
	
	@Column(name = "RECENT_NOTIFY_ID_", length = 64)
	private String recentNotifyId;
	
	@Column(name = "RECENT_TIME_")
	private Date recentTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRecentNotifyId() {
		return recentNotifyId;
	}

	public void setRecentNotifyId(String recentNotifyId) {
		this.recentNotifyId = recentNotifyId;
	}

	public Date getRecentTime() {
		return recentTime;
	}

	public void setRecentTime(Date recentTime) {
		this.recentTime = recentTime;
	}

	
	
	
	

}
