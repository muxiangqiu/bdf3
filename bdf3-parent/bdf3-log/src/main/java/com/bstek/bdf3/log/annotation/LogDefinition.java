package com.bstek.bdf3.log.annotation;
/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
public class LogDefinition {

	public static final String DISABLED = "${false}";
	public static final String OPERATION = "${entityState=='NONE'?'查看':entityState=='MODIFIED'?'修改':entityState=='NEW'?'新增':entityState=='DELETED'?'删除':entityState=='MOVED'?'排序':'其他'}";
	public static final String OPERATION_USER = "${loginUsername}";
	public static final String OPERATION_USER_NICKNAME = "${loginUser.nickname}";

	
	private String module;
	
	private String title;
	
	private String desc;
	
	private String logger;
	
	private String context;
	
	private String operation;
	
	private String operationUser;
	
	private String operationUserNickname;
	
	private String dataPath;
	
	private boolean recursive;
	
	private String disabled;
	
	private String category;
	
	private String var;

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

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	
	
	

}
