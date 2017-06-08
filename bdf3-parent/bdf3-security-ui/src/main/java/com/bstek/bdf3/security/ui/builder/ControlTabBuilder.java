package com.bstek.bdf3.security.ui.builder;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.Control;
import com.bstek.dorado.view.widget.base.tab.ControlTab;

@Component("maintain.controlTabBuilder")
public class ControlTabBuilder extends AbstractBuilder<ControlTab> {

	
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
	
	protected String getDesc(ControlTab controlTab){
		String desc = super.getDesc(controlTab);
		if (desc != null) {
			return desc;
		}
		if(StringUtils.isNotEmpty(controlTab.getName())){
			return controlTab.getCaption();		
		}
		return null;
	}




}

