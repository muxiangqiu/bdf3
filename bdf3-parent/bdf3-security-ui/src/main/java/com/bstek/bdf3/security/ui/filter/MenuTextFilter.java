package com.bstek.bdf3.security.ui.filter;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.security.domain.Component;
import com.bstek.bdf3.security.domain.ComponentType;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.widget.base.menu.TextMenuItem;

@org.springframework.stereotype.Component
public class MenuTextFilter extends AbstractFilter<TextMenuItem> {
	@Override
	public void invoke(TextMenuItem menuItem) {
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
		filterChildren(menuItem);
	}
	
	protected String getId(TextMenuItem menuItem){
		String id=menuItem.getName();
		if(StringUtils.isEmpty(id)){
			id=menuItem.getCaption();
		}
		return id;
	}
	
	@Override
	public boolean support(Object control) {
		return TextMenuItem.class.isAssignableFrom(control.getClass());
	}

}