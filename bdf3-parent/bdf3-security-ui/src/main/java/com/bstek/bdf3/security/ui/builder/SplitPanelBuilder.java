package com.bstek.bdf3.security.ui.builder;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.SplitPanel;

@Component("maintain.splitPanelBuilder")
public class SplitPanelBuilder extends AbstractBuilder<SplitPanel> {

	protected Collection<Control> getChildren(SplitPanel splitpanel){
		Collection<Control> children=new ArrayList<Control>();
		if(splitpanel.getMainControl()!=null){
			children.add(splitpanel.getMainControl());
		}
		if(splitpanel.getSideControl()!=null){
			children.add(splitpanel.getSideControl());
		}
		return children;
	}


}
