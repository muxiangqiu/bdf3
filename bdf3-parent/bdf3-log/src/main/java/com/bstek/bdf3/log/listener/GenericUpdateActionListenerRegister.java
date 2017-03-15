package com.bstek.bdf3.log.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.config.definition.GenericObjectListenerRegister;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月9日
 */
@Component
public class GenericUpdateActionListenerRegister extends GenericObjectListenerRegister {

	@Autowired
	private GenericUpdateActionListener listener;
	
	@Value("${bdf3.log.disabled}")
	private boolean disabled;
	
	@Value("${bdf3.log.defaultLog}")
	private boolean defaultLog;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (!disabled && defaultLog) {
			listener.setOrder(100);
			listener.setPattern("*");
			this.setListener(listener);
		}
		super.afterPropertiesSet();
	}

		
}
