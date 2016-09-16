package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.toolbar.ToolBar;

@Component("maintain.toolbarBuilder")
public class ToolbarBuilder extends AbstractBuilder<ToolBar> {
	
	protected Collection<Control> getChildren(ToolBar toolBar){
		return toolBar.getItems();
	}
	

}
