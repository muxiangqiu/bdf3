package com.bstek.bdf3.profile.widget;

import java.util.HashMap;
import java.util.Map;

import com.bstek.bdf3.profile.Constants;
import com.bstek.dorado.annotation.ClientObject;
import com.bstek.dorado.annotation.IdeProperty;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.ViewElement;
import com.bstek.dorado.view.ViewElementUtils;
import com.bstek.dorado.view.annotation.ComponentReference;
import com.bstek.dorado.view.annotation.Widget;
import com.bstek.dorado.view.widget.action.AjaxAction;

@Widget(name = "DataGridProfileAction", category = "BDF3", dependsPackage = "dataGridProfileAction", autoGenerateId = true)
@ClientObject(prototype = "dorado.widget.DataGridProfileAction", shortTypeName = "DataGridProfileAction")
public class DataGridProfileAction extends AjaxAction {
	private String dataGrid;

	@IdeProperty(visible = false)
	public String getService() {
		return null;
	}

	@IdeProperty(highlight = 1)
	@ComponentReference("DataGrid")
	public String getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(String dataGrid) {
		this.dataGrid = dataGrid;
	}
	
	@Override
	public void setParent(ViewElement parent) {
		super.setParent(parent);
		ViewElementUtils.getParentView(parent);
		if (!isIgnored()) {
			View view = getParentView(parent);
			Map<String, Object> metaData = view.getMetaData();
			if (metaData == null) {
				metaData = new HashMap<>();
				view.setMetaData(metaData);
			}
			metaData.put(Constants.NEED_PROFILE_FLAG_NAME, true);
		}
		
	}
	
	private View getParentView(ViewElement parent) {
		if (parent instanceof View) {
			return (View) parent;
		} else {
			return getParentView(parent.getParent());
		}
	}
}
