package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.tab.AbstractTabControl;
import com.bstek.dorado.view.widget.base.tab.Tab;

@Component("maintain.tabBuilder")
public class TabBuilder extends AbstractBuilder<AbstractTabControl> {

	protected Collection<Tab> getChildren(AbstractTabControl abstractTabControl){
		return abstractTabControl.getTabs();
	}



}

