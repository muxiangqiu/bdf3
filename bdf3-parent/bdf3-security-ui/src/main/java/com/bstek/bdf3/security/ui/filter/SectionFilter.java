package com.bstek.bdf3.security.ui.filter;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.ComponentType;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.accordion.Section;

@org.springframework.stereotype.Component
public class SectionFilter extends AbstractFilter<Section> {

	@Override
	public void invoke(Section section) {
		String path = UrlUtils.getRequestPath();
		String componentId = getId(section);
		if (componentId != null) {
			Component component = new Component();
			component.setComponentId(componentId);
			component.setPath(path);
			component.setComponentType(ComponentType.ReadWrite);
			if (!securityDecisionManager.decide(component)) {
				component.setComponentType(ComponentType.Read);
				if (!securityDecisionManager.decide(component)) {
					section.setIgnored(true);
					return;
				} else {
					section.setDisabled(true);
				}
			}
		}
		filterChildren(section);
	}
	
	protected String getId(Section section){
		String name = section.getCaption();
		if (StringUtils.isEmpty(name)) {
			name = section.getName();
		}
		return name;
	}
	
	protected Collection<Control> getChildren(Section section){
		if(section.getControl()!=null){
			return Arrays.asList(section.getControl());
		}
		return null;
	}
	
	@Override
	public boolean support(Object control) {
		return Section.class.isAssignableFrom(control.getClass());
	}

	

}
