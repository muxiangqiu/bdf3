package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import com.bstek.bdf3.security.ui.utils.ControlUtils;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.widget.Component;

@org.springframework.stereotype.Component
public class ViewFilter extends AbstractFilter<View> {

	@Override
	public void invoke(View view) {
		if (ControlUtils.isNoSecurtiy(view)) {
			return;
		}
		if (ControlUtils.supportControlType(view)) {
			String path = UrlUtils.getRequestPath();
			com.bstek.bdf3.security.orm.Component component = new com.bstek.bdf3.security.orm.Component();
			component.setPath(path);
			if (securityDecisionManager.findConfigAttributes(component) == null) {
				return;
			}
		}
		filterChildren(view);
	}

	@Override
	protected Collection<Component> getChildren(View view) {
		return view.getChildren();
	}

	@Override
	public boolean support(Object control) {
		return View.class.isAssignableFrom(control.getClass());
	}

}
