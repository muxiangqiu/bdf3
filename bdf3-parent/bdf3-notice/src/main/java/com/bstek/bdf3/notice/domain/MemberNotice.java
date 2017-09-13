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
@Table(name = "BDF3_MEMBER_NOTICE")
public class MemberNotice {
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "MEMBER_ID_", length = 64)
	private String memberId;
	
	@Column(name = "NOTICE_ID_", length = 64)
	private String noticeId;
	
	@Column(name = "READ_TIME_")
	private Date readTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	

	
	
	

}
