package com.bstek.bdf3.security.ui.builder;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.bstek.dorado.view.manager.ViewConfig;
import com.bstek.dorado.view.widget.base.menu.BaseMenuItem;
import com.bstek.dorado.view.widget.base.menu.TextMenuItem;

@Component("maintain.menuTextBuilder")
public class MenuTextBuilder extends AbstractBuilder<TextMenuItem> {

	@SuppressWarnings("deprecation")
	@Override
	protected Collection<BaseMenuItem> getChildren(TextMenuItem menuItem){
		return menuItem.getItems();
	}
	
	protected String getId(TextMenuItem menuItem){
		String id=menuItem.getName();
		if(StringUtils.isEmpty(id)){
			id=menuItem.getCaption();
		}
		return id;
	}
	
	protected String getDesc(TextMenuItem menuItem, ViewConfig viewConfig){
		String desc = super.getDesc(menuItem, viewConfig);
		if (desc != null) {
			return desc;
		}
		if(StringUtils.isNotEmpty(menuItem.getName())){
			return menuItem.getCaption();
		}
		return null;
	}


}