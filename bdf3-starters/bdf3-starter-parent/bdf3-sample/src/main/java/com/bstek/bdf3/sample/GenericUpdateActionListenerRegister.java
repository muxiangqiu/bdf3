package com.bstek.bdf3.sample;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public void afterPropertiesSet() throws Exception {
		listener.setOrder(100);
		listener.setPattern("*");
		this.setListener(listener);
		super.afterPropertiesSet();
	}

		
}
