package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.tab.AbstractTabControl;
import com.bstek.dorado.view.widget.base.tab.Tab;

@Component
public class TabFilter extends AbstractFilter<AbstractTabControl> {

	protected Collection<Tab> getChildren(AbstractTabControl abstractTabControl){
		return abstractTabControl.getTabs();
	}
	
	@Override
	public boolean support(Object control) {
		return AbstractTabControl.class.isAssignableFrom(control.getClass());
	}


}

