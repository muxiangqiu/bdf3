package com.bstek.bdf3.security.ui.builder;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.base.SimpleIconButton;


@Component("maintain.simpleIconButtonBuilder")
public class SimpleIconButtonBuilder extends AbstractBuilder<SimpleIconButton> {	
	
	@Override
	protected String getIcon(SimpleIconButton button) {
		if(StringUtils.isNotEmpty(button.getIcon())){
			return button.getIcon();
		}
		return super.getIcon(button);
	}



	@Override
	protected String getDesc(SimpleIconButton button){
		String desc = super.getDesc(button);
		if (desc != null) {
			return desc;
		}
		if(button.getId()!=null){
			if(StringUtils.isNotEmpty(button.getIcon())){
				return button.getIcon();
			}else if(StringUtils.isNotEmpty(button.getIconClass())){
				return button.getIconClass();
			}
		}
		return null;
	}
}