package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.menu.BaseMenuItem;
import com.bstek.dorado.view.widget.base.menu.Menu;

@Component("maintain.menuBuilder")
public class MenuBuilder extends AbstractBuilder<Menu> {
		
	protected Collection<BaseMenuItem> getChildren(Menu menu){
		return menu.getItems();
	}

}