package com.bstek.bdf3.security.ui.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.bstek.dorado.view.widget.Component;
import com.bstek.dorado.view.widget.base.Panel;

@org.springframework.stereotype.Component
public class PanelFilter extends AbstractFilter<Panel> {

	protected Collection<Component> getChildren(Panel panel){
		Collection<Component> children = new ArrayList<Component>();
		children.addAll((Collection<? extends Component>) panel.getTools());
		children.addAll((Collection<? extends Component>) panel.getChildren());
		children.addAll((Collection<? extends Component>) panel.getButtons());

		return children;
	}

	
	protected String getId(Panel panel){
		String id=panel.getId();
		if(StringUtils.isEmpty(id)){
			id=panel.getCaption();
		}
		return id;
	}
	
	@Override
	public boolean support(Object control) {
		return Panel.class.isAssignableFrom(control.getClass());
	}



}
