package com.bstek.bdf3.profile.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.config.definition.GenericObjectListenerRegister;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2017年7月18日
 */
@Component("profile.genericViewListenerRegister")
public class GenericViewListenerRegister extends GenericObjectListenerRegister {

	@Autowired
	private GenericViewListener listener;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		listener.setOrder(100);
		listener.setPattern("*");
		this.setListener(listener);
		super.afterPropertiesSet();
	}

		
}
