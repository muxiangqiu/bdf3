package com.bstek.bdf3.log;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.log.annotation.Log;
import com.bstek.bdf3.log.annotation.LogDefinition;
import com.bstek.dorado.core.Configure;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
public final class LogUtils {

	public static LogDefinition buildLogDefinition(Log log) {
		LogDefinition logDefinition = new LogDefinition();
		if (StringUtils.isEmpty(log.module())) {
			logDefinition.setModule("${" + Configure.getString(Constants.DEFAULT_MODULE_ATTR) + "}");
		} else {
			logDefinition.setModule(log.module());
		}
		if (StringUtils.isEmpty(log.category())) {
			logDefinition.setCategory("${" + Configure.getString(Constants.DEFAULT_CATEGORY_ATTR) +"}");
		} else {
			logDefinition.setCategory(log.category());
		}
		if (StringUtils.isEmpty(log.context())) {
			logDefinition.setContext(Configure.getString(Constants.DEFAULT_CONTEXT_ATTR));
		} else {
			logDefinition.setContext(log.context());
		}
		if (StringUtils.isEmpty(log.desc())) {
			logDefinition.setDesc("${" + Configure.getString(Constants.DEFAULT_DESC_ATTR) + "}");
		} else {
			logDefinition.setDesc(log.desc());
		}
		if (StringUtils.isEmpty(log.disabled())) {
			logDefinition.setDisabled("${" + Configure.getString(Constants.DEFAULT_DISABLED_ATTR) + "}");
		} else {
			logDefinition.setDisabled(log.disabled());
		}
		if (StringUtils.isEmpty(log.logger())) {
			logDefinition.setLogger(Configure.getString(Constants.DEFAULT_LOGGER_ATTR));
		} else {
			logDefinition.setLogger(log.logger());
		}
		if (StringUtils.isEmpty(log.operation())) {
			logDefinition.setOperation("${" + Configure.getString(Constants.DEFAULT_OPERATION_ATTR) + "}");
		} else {
			logDefinition.setOperation(log.operation());
		}
		if (StringUtils.isEmpty(log.operationUser())) {
			logDefinition.setOperationUser("${" + Configure.getString(Constants.DEFAULT_OPERATION_USER_ATTR) + "}");
		} else {
			logDefinition.setOperationUser(log.operationUser());
		}
		if (StringUtils.isEmpty(log.operationUserNickname())) {
			logDefinition.setOperationUserNickname("${" + Configure.getString(Constants.DEFAULT_OPERATION_USER_NICKNAME_ATTR) + "}");
		} else {
			logDefinition.setOperationUserNickname(log.operationUserNickname());
		}
		if (StringUtils.isEmpty(log.var())) {
			logDefinition.setVar(Configure.getString(Constants.DEFAULT_VAR_ATTR));
		} else {
			logDefinition.setVar(log.var());
		}
		if (StringUtils.isEmpty(log.title())) {
			logDefinition.setTitle("${" + Configure.getString(Constants.DEFAULT_TITLE_ATTR) + "}");
		} else {
			logDefinition.setTitle(log.title());
		}
		logDefinition.setDataPath(log.dataPath());
		logDefinition.setRecursive(log.recursive());
		return logDefinition;
	}
	
	public static LogDefinition buildLogDefinition(Log typeLog, Log methodLog) {
		LogDefinition logDefinition = buildLogDefinition(typeLog);
		if (!logDefinition.getModule().equals(methodLog.module())) {
			logDefinition.setModule(methodLog.module());
		}
		if (!logDefinition.getCategory().equals(methodLog.category())) {
			logDefinition.setCategory(methodLog.category());
		}
		
		if (!logDefinition.getContext().equals(methodLog.context())) {
			logDefinition.setContext(methodLog.context());
		}
		if (!logDefinition.getDataPath().equals(methodLog.dataPath())) {
			logDefinition.setDataPath(methodLog.dataPath());
		}
		if (!logDefinition.getDesc().equals(methodLog.desc())) {
			logDefinition.setDesc(methodLog.desc());
		}
		if (!logDefinition.getDisabled().equals(methodLog.disabled())) {
			logDefinition.setDisabled(methodLog.disabled());
		}
		if (!logDefinition.getOperation().equals(methodLog.operation())) {
			logDefinition.setOperation(methodLog.operation());
		}
		if (!logDefinition.getOperationUser().equals(methodLog.operationUser())) {
			logDefinition.setOperationUser(methodLog.operationUser());
		}
		if (!logDefinition.getOperationUserNickname().equals(methodLog.operationUserNickname())) {
			logDefinition.setOperationUser(methodLog.operationUserNickname());
		}
		if (logDefinition.isRecursive() != methodLog.recursive()) {
			logDefinition.setRecursive(methodLog.recursive());
		}
		if (!logDefinition.getTitle().equals(methodLog.title())) {
			logDefinition.setTitle(methodLog.title());
		}
		if (!logDefinition.getVar().equals(methodLog.var())) {
			logDefinition.setVar(methodLog.var());
		}
		return logDefinition;
	}
	
	
}
