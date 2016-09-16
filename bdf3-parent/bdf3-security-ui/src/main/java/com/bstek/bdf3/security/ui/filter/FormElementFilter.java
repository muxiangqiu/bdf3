package com.bstek.bdf3.security.ui.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bstek.bdf3.security.domain.Component;
import com.bstek.bdf3.security.domain.ComponentType;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.data.type.EntityDataType;
import com.bstek.dorado.data.type.property.PropertyDef;
import com.bstek.dorado.view.ViewElement;
import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.form.FormElement;
import com.bstek.dorado.view.widget.form.autoform.AutoForm;

@org.springframework.stereotype.Component
public class FormElementFilter extends AbstractFilter<FormElement> {

	@Override
	public void invoke(FormElement element) {
		String path = UrlUtils.getRequestPath();
		String componentId = getId(element);
		if (componentId != null) {
			Component component = new Component();
			component.setComponentId(componentId);
			component.setPath(path);
			component.setComponentType(ComponentType.ReadWrite);
			if (!securityDecisionManager.decide(component)) {
				component.setComponentType(ComponentType.Read);
				if (!securityDecisionManager.decide(component)) {
					element.setIgnored(true);
					return;
				} else {
					element.setReadOnly(true);;
				}
			}
		}
		filterChildren(element);
	}
	
	protected Collection<Control> getChildren(FormElement element){
		if(element.getEditor()!=null){
			return Arrays.asList(element.getEditor());
		}
		return null;
	}
	
	protected String getId(FormElement element){
		String id = element.getId();
		if (StringUtils.isEmpty(id)) {
			id = element.getProperty();
		}
		if(StringUtils.isEmpty(id)){
			id = element.getLabel();
		}
		if (StringUtils.isEmpty(id)) {
			ViewElement viewElement = element.getParent();
			if (viewElement instanceof AutoForm) {
				EntityDataType entityDataType = ((AutoForm) viewElement).getDataType();
				Map<String, PropertyDef> dataTypePropertyDefs = null;
				if (entityDataType != null) {
					dataTypePropertyDefs = entityDataType.getPropertyDefs();
				}
				id = getFormElementLabel(element, dataTypePropertyDefs);
			}
		}
		return id;
	}
	

	private String getFormElementLabel(FormElement element, Map<String, PropertyDef> dataTypePropertyDefs) {
		String property = element.getProperty();
		if (StringUtils.isNotEmpty(property) && dataTypePropertyDefs != null) {
			PropertyDef pd = dataTypePropertyDefs.get(property);
			if (pd != null && StringUtils.isNotEmpty(pd.getLabel())) {
				return pd.getLabel();
			}
		}
		return property;
	}
	
	@Override
	public boolean support(Object control) {
		return FormElement.class.isAssignableFrom(control.getClass());
	}

}
