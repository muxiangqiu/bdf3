package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.menu.BaseMenuItem;
import com.bstek.dorado.view.widget.base.menu.Menu;

@Component
public class MenuFilter extends AbstractFilter<Menu> {
		
	protected Collection<BaseMenuItem> getChildren(Menu menu){
		return menu.getItems();
	}

	@Override
	public boolean support(Object control) {
		return Menu.class.isAssignableFrom(control.getClass());
	}

}