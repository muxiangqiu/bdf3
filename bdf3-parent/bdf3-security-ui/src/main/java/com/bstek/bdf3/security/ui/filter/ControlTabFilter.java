package com.bstek.bdf3.security.ui.filter;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.tab.ControlTab;

@Component
public class ControlTabFilter extends AbstractFilter<ControlTab> {

	
	protected Collection<Control> getChildren(ControlTab controlTab){
		if(controlTab.getControl()!=null){
			return Arrays.asList(controlTab.getControl());
		}
		return null;
	}
	
	protected String getId(ControlTab controlTab){
		String id=controlTab.getName();
		if(StringUtils.isEmpty(id)){
			id=controlTab.getCaption();
		}
		return id;
	}
	
	@Override
	public boolean support(Object control) {
		return ControlTab.class.isAssignableFrom(control.getClass());
	}




}

