package com.bstek.bdf3.security.ui.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.form.Label;

@Component
public class LabelFilter extends AbstractFilter<Label> {
	
	protected String getId(Label label){
		String id = label.getId();
		if (StringUtils.isEmpty(id)) {
			id = label.getText();
		}
		return id;
	}
	
	@Override
	public boolean support(Object control) {
		return Label.class.isAssignableFrom(control.getClass());
	}


}
