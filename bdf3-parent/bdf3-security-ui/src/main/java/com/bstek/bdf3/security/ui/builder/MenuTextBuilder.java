package com.bstek.bdf3.security.ui.builder;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.menu.TextMenuItem;

@Component("maintain.menuTextBuilder")
public class MenuTextBuilder extends AbstractBuilder<TextMenuItem> {

	protected String getId(TextMenuItem menuItem){
		String id=menuItem.getName();
		if(StringUtils.isEmpty(id)){
			id=menuItem.getCaption();
		}
		return id;
	}
	
	protected String getDesc(TextMenuItem menuItem){
		if(StringUtils.isNotEmpty(menuItem.getName())){
			return menuItem.getCaption();
		}
		return null;
	}


}