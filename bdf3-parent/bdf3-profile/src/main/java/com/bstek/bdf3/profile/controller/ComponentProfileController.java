package com.bstek.bdf3.profile.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bstek.bdf3.profile.service.ComponentConfigService;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.variant.Record;

@Controller
public class ComponentProfileController {
	
	@Autowired
	private ComponentConfigService componentConfigService;

	@Expose
	public void resetComponentProfile(String controlId) {
		componentConfigService.resetComponentProfile(controlId);
	}

	@SuppressWarnings("unchecked")
	@Expose
	public void saveComponentProfile(Map<String, Object> parameter) {
		if (parameter != null) {
			String controlId = (String) parameter.get("controlId");
			String name = (String) parameter.get("name");
			String cols = parameter.get("cols").toString();
			Collection<Record> members = (Collection<Record>) parameter.get("members");
			componentConfigService.saveComponentProfile(controlId, name, cols, members);
		}
	}
	
}
