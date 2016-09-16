package com.bstek.bdf3.security.ui.filter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.SplitPanel;

@Component
public class SplitPanelFilter extends AbstractFilter<SplitPanel> {

	protected Collection<Control> getChildren(SplitPanel splitpanel){
		Collection<Control> children = new ArrayList<Control>();
		if (splitpanel.getMainControl() != null) {
			children.add(splitpanel.getMainControl());
		}
		if (splitpanel.getSideControl() != null) {
			children.add(splitpanel.getSideControl());
		}
		return children;
	}

	@Override
	public boolean support(Object control) {
		return SplitPanel.class.isAssignableFrom(control.getClass());
	}

}
