package com.bstek.bdf3.security.ui.filter;

import org.springframework.beans.factory.annotation.Autowired;

import com.bstek.bdf3.security.decision.manager.SecurityDecisionManager;
import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.ComponentType;
import com.bstek.bdf3.security.ui.utils.ControlUtils;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.AbstractViewElement;

/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年7月7日
 */
@org.springframework.stereotype.Component
public class DefaultFilter implements Filter<AbstractViewElement> {

	@Autowired
	protected SecurityDecisionManager securityDecisionManager;

	@Override
	public boolean support(Object control) {
		return false;
	}

	@Override
	public void invoke(AbstractViewElement control) {
		if (ControlUtils.isNoSecurtiy(control)) {
			return;
		}
		if (ControlUtils.supportControlType(control)) {
			String path = UrlUtils.getRequestPath();
			String componentId = control.getId();
			if (componentId != null) {
				Component component = new Component();
				component.setComponentId(componentId);
				component.setPath(path);
				component.setComponentType(ComponentType.Read);
				if (!securityDecisionManager.decide(component)) {
					control.setIgnored(true);
					return;
				}
			}
		}

	}
}
