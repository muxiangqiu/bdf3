package com.bstek.bdf3.security.ui.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.security.ui.filter.ViewFilter;
import com.bstek.dorado.data.listener.GenericObjectListener;
import com.bstek.dorado.view.View;

@Component
public class GenericViewListener extends GenericObjectListener<View> {

	@Value("${bdf3.application.title:BSTEK APPLICATION FRAMEWORK}")
	private String applicationTitle;
	
	@Autowired
	private ViewFilter viewFilter;
	
	@Override
	public boolean beforeInit(View view) throws Exception {
		return true;
	}

	@Override
	public void onInit(View view) throws Exception {
		view.setTitle(applicationTitle);
		viewFilter.invoke(view);
	}

}
