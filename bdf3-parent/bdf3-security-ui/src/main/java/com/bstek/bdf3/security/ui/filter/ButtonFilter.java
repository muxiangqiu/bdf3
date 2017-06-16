package com.bstek.bdf3.security.ui.filter;


import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.ComponentType;
import com.bstek.bdf3.security.ui.utils.ControlUtils;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.widget.base.Button;
import com.bstek.dorado.view.widget.base.menu.BaseMenuItem;
import com.bstek.dorado.view.widget.base.toolbar.MenuButton;

@org.springframework.stereotype.Component
public class ButtonFilter extends AbstractFilter<Button> {

	@Override
	public void invoke(Button button) {
		if (ControlUtils.isNoSecurtiy(button)) {
			return;
		}
		if (ControlUtils.supportControlType(button)) {
			String path = UrlUtils.getRequestPath();
			String componentId = getId(button);
			if (componentId != null) {
				Component component = new Component();
				component.setComponentId(componentId);
				component.setPath(path);
				component.setComponentType(ComponentType.ReadWrite);
				if (!securityDecisionManager.decide(component)) {
					component.setComponentType(ComponentType.Read);
					if (!securityDecisionManager.decide(component)) {
						button.setIgnored(true);
						return;
					} else {
						button.setDisabled(true);
					}
				}
			}
		}
		filterChildren(button);
	}
	
	protected Collection<BaseMenuItem> getChildren(Button button){
		if (MenuButton.class.isAssignableFrom(button.getClass())) {
			return ((MenuButton) button).getItems();
		}
		return Collections.emptyList();
	}
	
	
	@Override
	protected String getId(Button button){
		String id = button.getId();
		if (StringUtils.isEmpty(id)) {
			id = button.getCaption();
		}
		return id;
	}
	
	@Override
	public boolean support(Object control) {
		return Button.class.isAssignableFrom(control.getClass());
	}
	
}