package com.bstek.bdf3.security.ui.filter;


import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.ComponentType;
import com.bstek.bdf3.security.ui.utils.ControlUtils;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.widget.base.Button;

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