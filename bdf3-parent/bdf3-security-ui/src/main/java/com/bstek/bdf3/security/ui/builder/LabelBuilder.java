package com.bstek.bdf3.security.ui.builder;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.bstek.dorado.view.manager.ViewConfig;
import com.bstek.dorado.view.widget.form.Label;

@Component("maintain.labelBuilder")
public class LabelBuilder extends AbstractBuilder<Label> {
	
	protected String getId(Label label){
		String id = label.getId();
		if (StringUtils.isEmpty(id)) {
			id = label.getText();
		}
		return id;
	}
	
	protected String getDesc(Label label, ViewConfig viewConfig){
		String desc = super.getDesc(label, viewConfig);
		if (desc != null) {
			return desc;
		}
		if(label.getId()!=null){
			return label.getText();
		}
		return null;
	}


}
