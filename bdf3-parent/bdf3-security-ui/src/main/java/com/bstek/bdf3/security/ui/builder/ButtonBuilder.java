package com.bstek.bdf3.security.ui.builder;


import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.bstek.dorado.view.manager.ViewConfig;
import com.bstek.dorado.view.widget.base.Button;
import com.bstek.dorado.view.widget.base.menu.BaseMenuItem;
import com.bstek.dorado.view.widget.base.toolbar.MenuButton;


@Component("maintain.buttonBuilder")
public class ButtonBuilder extends AbstractBuilder<Button> {

	@Override
	protected Collection<BaseMenuItem> getChildren(Button button){
		if (MenuButton.class.isAssignableFrom(button.getClass())) {
			return ((MenuButton) button).getItems();
		}
		return Collections.emptyList();
	}
	
	@Override
	protected String getId(Button button){
		String id = button.getId();
		if (StringUtils.isEmpty(id)) {
			id = button.getCaption();
		}
		return id;
	}
	
	@Override
	protected String getIcon(Button button) {
		if(StringUtils.isNotEmpty(button.getIcon())){
			return button.getIcon();
		}
		return super.getIcon(button);
	}

	
	@Override
	protected String getDesc(Button button, ViewConfig viewConfig){
		String desc = super.getDesc(button, viewConfig);
		if (desc != null) {
			return desc;
		}
		if(button.getId()!=null){
			return button.getCaption();
		}
		return null;
	}
}