package com.bstek.bdf3.security.ui.filter;


import org.springframework.stereotype.Component;

import com.bstek.dorado.view.widget.datacontrol.DataPilot;


@Component
public class DataPilotFilter extends AbstractFilter<DataPilot> {


	@Override
	public boolean support(Object control) {
		return DataPilot.class.isAssignableFrom(control.getClass());
	}
}