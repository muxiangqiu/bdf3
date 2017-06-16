package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.ComponentType;
import com.bstek.bdf3.security.ui.utils.ControlUtils;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.widget.base.menu.BaseMenuItem;
import com.bstek.dorado.view.widget.base.menu.TextMenuItem;

@org.springframework.stereotype.Component
public class MenuTextFilter extends AbstractFilter<TextMenuItem> {
	@Override
	public void invoke(TextMenuItem menuItem) {
		if (ControlUtils.isNoSecurtiy(menuItem)) {
			return;
		}
		if (ControlUtils.supportControlType(menuItem)) {
			String path = UrlUtils.getRequestPath();
			String componentId = getId(menuItem);
			if (componentId != null) {
				Component component = new Component();
				component.setComponentId(componentId);
				component.setPath(path);
				component.setComponentType(ComponentType.ReadWrite);
				if (!securityDecisionManager.decide(component)) {
					component.setComponentType(ComponentType.Read);
					if (!securityDecisionManager.decide(component)) {
						menuItem.setIgnored(true);
						return;
					} else {
						menuItem.setDisabled(true);
					}
				}
			}
		}
		filterChildren(menuItem);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected Collection<BaseMenuItem> getChildren(TextMenuItem menuItem){
		return menuItem.getItems();
	}

	protected String getId(TextMenuItem menuItem) {
		String id = menuItem.getName();
		if (StringUtils.isEmpty(id)) {
			id = menuItem.getCaption();
		}
		return id;
	}

	@Override
	public boolean support(Object control) {
		return TextMenuItem.class.isAssignableFrom(control.getClass());
	}

}