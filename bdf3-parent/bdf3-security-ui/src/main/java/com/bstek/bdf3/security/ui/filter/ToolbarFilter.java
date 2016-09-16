package com.bstek.bdf3.security.ui.filter;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.toolbar.ToolBar;

@Component
public class ToolbarFilter extends AbstractFilter<ToolBar> {
	
	protected Collection<Control> getChildren(ToolBar toolBar){
		return toolBar.getItems();
	}
	
	@Override
	public boolean support(Object control) {
		return ToolBar.class.isAssignableFrom(control.getClass());
	}
	

}
