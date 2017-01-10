package com.bstek.bdf3.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
@Entity
@Table(name = "BDF3_LOG_INFO")
public class LogInfo {

	@Id
	@Column(name = "ID_", length = 36)
	private String id;
	
	@Column(name = "MODULE_", length = 255)
	private String module;
	
	@Column(name = "TITLE_", length = 255)
	private String title;
	
	@Lob
	@Column(name = "DESC_")
	private String desc;
	
	@Column(name = "OPERATION_", length = 100)
	private String operation;
	
	@Column(name = "OPERATION_USER_", length = 30)
	private String operationUser;
	
	@Column(name = "OPERATION_USER_NICKNAME_", length = 30)
	private String operationUserNickname;
	
	@Column(name = "OPERATION_DATE_")
	private Date operationDate;
	
	@Column(name = "CATEGORY_", length = 100)
	private String category;
	
	@Column(name = "IP_", length = 20)
	private String IP;
	
	@Column(name = "SOURCE_", length = 255)
	private String source;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public String getOperationUserNickname() {
		return operationUserNickname;
	}

	public void setOperationUserNickname(String operationUserNickname) {
		this.operationUserNickname = operationUserNickname;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	
	
	
	
	

}
