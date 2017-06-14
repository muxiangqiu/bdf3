package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import com.bstek.bdf3.security.decision.manager.SecurityDecisionManager;
import com.bstek.bdf3.security.orm.Component;
import com.bstek.bdf3.security.orm.ComponentType;
import com.bstek.bdf3.security.ui.utils.ControlUtils;
import com.bstek.bdf3.security.ui.utils.UrlUtils;
import com.bstek.dorado.view.AbstractViewElement;
import com.bstek.dorado.view.widget.Control;

/**
 * @author Kevin.yang
 */
@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractFilter<T extends AbstractViewElement> implements Filter<T>{
	
	@Autowired
	protected Collection<Filter> filters;
	
	@Autowired
	private DefaultFilter defaultFilter;
	
	@Autowired
	protected SecurityDecisionManager securityDecisionManager;
	
	@Override
	public void invoke(T control) {
		if (ControlUtils.isNoSecurtiy(control)) {
			return;
		}
		if (ControlUtils.supportControlType(control)) {
			String path = UrlUtils.getRequestPath();
			String componentId = getId(control);
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
		
		filterChildren(control);
	}
	
	protected void filterChildren(T control) {
		if (getChildren(control) != null) {
			for (Object child : getChildren(control)) {
				if (child instanceof Control) {
					boolean exist = false;
					Control c = (Control) child;
					for (Filter filter : filters) {
						if (filter.support(c)) {
							exist = true;
							filter.invoke(c);
							break;
						}
					}
					if (!exist) {
						defaultFilter.invoke(c);
					}
				}
				
			}
		}
	}
	
	protected String getId(T control){
		return control.getId();
	}
	
	
	protected Collection getChildren(T control){
		return Collections.EMPTY_LIST;
	}

}
