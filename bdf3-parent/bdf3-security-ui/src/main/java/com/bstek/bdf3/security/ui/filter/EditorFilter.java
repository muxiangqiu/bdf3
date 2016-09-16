package com.bstek.bdf3.security.ui.filter;


import com.bstek.bdf3.security.domain.Component;
import com.bstek.bdf3.security.domain.ComponentType;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.widget.form.AbstractEditor;

@org.springframework.stereotype.Component
public class EditorFilter extends AbstractFilter<AbstractEditor> {

	@Override
	public void invoke(AbstractEditor editor) {
		String path = UrlUtils.getRequestPath();
		String componentId = getId(editor);
		if (componentId != null) {
			Component component = new Component();
			component.setComponentId(componentId);
			component.setPath(path);
			component.setComponentType(ComponentType.ReadWrite);
			if (!securityDecisionManager.decide(component)) {
				component.setComponentType(ComponentType.Read);
				if (!securityDecisionManager.decide(component)) {
					editor.setIgnored(true);
					return;
				} else {
					editor.setReadOnly(true);;
				}
			}
		}
		filterChildren(editor);
	}
	
	@Override
	public boolean support(Object control) {
		return AbstractEditor.class.isAssignableFrom(control.getClass());
	}

}

