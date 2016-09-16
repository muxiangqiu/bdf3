package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import com.bstek.dorado.view.View;
import com.bstek.dorado.view.widget.Component;

@org.springframework.stereotype.Component
public class ViewBuilder extends AbstractBuilder<View> {
	
	
	@Override
	protected String getName(View control) {
		return "View";
	}


	@Override
	protected boolean isEnabled(View control) {
		return false;
	}

	@Override
	protected Collection<Component> getChildren(View view){
		return view.getChildren();
	}

}

