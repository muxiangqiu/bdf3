package com.bstek.bdf3.security.ui.builder;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.Button;


@Component("maintain.buttonBuilder")
public class ButtonBuilder extends AbstractBuilder<Button> {

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
	protected String getDesc(Button button){
		if(button.getId()!=null){
			return button.getCaption();
		}
		return null;
	}
}