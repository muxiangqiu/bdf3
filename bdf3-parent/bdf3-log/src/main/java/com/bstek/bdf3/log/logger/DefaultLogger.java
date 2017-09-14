package com.bstek.bdf3.log.logger;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.bdf3.dorado.jpa.BeanUtils;
import com.bstek.bdf3.dorado.jpa.FieldUtils;
import com.bstek.bdf3.dorado.jpa.GenricTypeUtils;
import com.bstek.bdf3.dorado.jpa.JpaUtil;
import com.bstek.bdf3.log.annotation.LogDefinition;
import com.bstek.bdf3.log.context.ContextHandler;
import com.bstek.bdf3.log.context.provider.ContextProvider;
import com.bstek.bdf3.log.model.LogInfo;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.web.DoradoContext;

/**
 *@author Kevin.yang
 *@since 2015年7月20日
 */
@Component
public class DefaultLogger implements Logger {

	@Autowired
	protected ContextHandler contextHandler;
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public void log() {
		Object obj = contextHandler.get(ContextProvider.TARGET);
		LogDefinition log = (LogDefinition) contextHandler.get(ContextProvider.LOG_DEFINITION);
		if (obj instanceof Collection) {
			Collection target = (Collection) obj;
			for (Object item : target) {
				setContext(log, item);
				doLog(log);
				doChildernLog(item, log);
			}
		} else if (obj != null) {
			setContext(log, obj);
			doLog(log);
			doChildernLog(obj, log);
		} else {
			doLog(log);
		}

	}
	
	protected void doChildernLog(Object parent, LogDefinition log) {
		if (log.isRecursive()) {
			List<Field> fields = FieldUtils.getFields(parent.getClass());
			for (Field field : fields) {
				Object value = BeanUtils.getFieldValue(parent, field);
				Class<?> pt = field.getType();
				if (!EntityUtils.isSimpleValue(value)) {
					if (Collection.class.isAssignableFrom(pt)) {
						Class<?> gt = GenricTypeUtils.getGenricType(field);
						if (gt != null && JpaUtil.isEntityClass(gt)) {
							contextHandler.set(ContextProvider.TARGET, value);
							log();
						}
					} else if(JpaUtil.isEntityClass(pt)) {
						contextHandler.set(ContextProvider.TARGET, value);
						log();
					}
				}
			}
		}
	}

	protected void doLog(LogDefinition log) {
		boolean disabled = contextHandler.compile(log.getDisabled());
		if (!disabled) {
			Object logInfo = getLogInfo(log);
			JpaUtil.persist(logInfo);
			
			
		}
		
	}

	protected Object getLogInfo(LogDefinition log) {
		LogInfo logInfo = new LogInfo();
		logInfo.setId(UUID.randomUUID().toString());
		logInfo.setCategory(contextHandler.compileText(log.getCategory()));
		logInfo.setDesc(contextHandler.compileText(log.getDesc()));
		logInfo.setIP(contextHandler.compileText(getIP()));
		logInfo.setModule(contextHandler.compileText(log.getModule()));
		logInfo.setOperation(contextHandler.compileText(log.getOperation()));
		logInfo.setOperationDate(new Date());
		logInfo.setOperationUser(contextHandler.compileText(log.getOperationUser()));
		logInfo.setOperationUserNickname(contextHandler.compileText(log.getOperationUserNickname()));
		logInfo.setSource(DoradoContext.getCurrent().getRequest().getHeader("Referer"));
		logInfo.setTitle(contextHandler.compileText(log.getTitle()));
		
		return logInfo;
	}
	
	protected String getIP() {
		HttpServletRequest request = DoradoContext.getCurrent().getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

	protected void setContext(LogDefinition log, Object entity) {
		EntityState state = EntityUtils.getState(entity);
		contextHandler.set(log.getVar(), entity);
		contextHandler.set(ContextProvider.ENTITY_STATE, state.name());
	}

	public void setContextHandler(ContextHandler contextHandler) {
		this.contextHandler = contextHandler;
	}

}
