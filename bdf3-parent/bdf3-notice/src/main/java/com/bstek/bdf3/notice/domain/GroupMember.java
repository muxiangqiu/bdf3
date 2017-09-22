package com.bstek.bdf3.notice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年9月11日
 */
@Entity
@Table(name = "BDF3_GROUP_MEMBER")
public class GroupMember {
	
	@Id
	@Column(name = "ID_", length = 64)
	private String id;
	
	@Column(name = "GROUP_ID_", length = 64)
	private String groupId;
	
	@Column(name = "MEMBER_ID_", length = 64)
	private String memberId;
	
	@Column(name = "NICKNAME_", length = 64)
	private String nickname;
	
	@Column(name = "MEMBER_TYPE_", length = 64)
	private String memberType;
	
	@Column(name = "ADMINISTRATOR_")
	private boolean administrator;
	
	@Column(name = "ACTIVE_")
	private boolean active;
	
	@Column(name = "READ_ONLY_")
	private boolean readOnly;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	
	
	
	

}
