package com.bstek.bdf3.profile.widget;

import java.util.HashMap;
import java.util.Map;

import com.bstek.bdf3.profile.Constants;
import com.bstek.dorado.annotation.ClientObject;
import com.bstek.dorado.annotation.ClientProperty;
import com.bstek.dorado.annotation.IdeProperty;
import com.bstek.dorado.view.View;
import com.bstek.dorado.view.ViewElement;
import com.bstek.dorado.view.annotation.ComponentReference;
import com.bstek.dorado.view.annotation.Widget;
import com.bstek.dorado.view.widget.action.AjaxAction;

@Widget(name = "AutoFormProfileAction", category = "BDF3", dependsPackage = "autoFormProfileAction", autoGenerateId = true)
@ClientObject(prototype = "dorado.widget.AutoFormProfileAction", shortTypeName = "AutoFormProfileAction")
public class AutoFormProfileAction extends AjaxAction {
	private String autoForm;

	@IdeProperty(highlight = 1)
	@ComponentReference("AutoForm")
	public String getAutoForm() {
		return autoForm;
	}

	public void setAutoForm(String autoForm) {
		this.autoForm = autoForm;
	}

	@IdeProperty(visible = false)
	public String getService() {
		return null;
	}
	
	boolean showLabelConfig = true;
	boolean showColSpanConfig = true;
	boolean showRowSpanConfig = true;
	boolean showVisibleConfig = true;
	boolean showHideModeConfig = true;

	@ClientProperty(escapeValue = "true")
	public boolean isShowLabelConfig() {
		return showLabelConfig;
	}

	public void setShowLabelConfig(boolean showLabelConfig) {
		this.showLabelConfig = showLabelConfig;
	}

	@ClientProperty(escapeValue = "true")
	public boolean isShowColSpanConfig() {
		return showColSpanConfig;
	}

	public void setShowColSpanConfig(boolean showColSpanConfig) {
		this.showColSpanConfig = showColSpanConfig;
	}

	@ClientProperty(escapeValue = "true")
	public boolean isShowRowSpanConfig() {
		return showRowSpanConfig;
	}

	public void setShowRowSpanConfig(boolean showRowSpanConfig) {
		this.showRowSpanConfig = showRowSpanConfig;
	}

	@ClientProperty(escapeValue = "true")
	public boolean isShowVisibleConfig() {
		return showVisibleConfig;
	}

	public void setShowVisibleConfig(boolean showVisibleConfig) {
		this.showVisibleConfig = showVisibleConfig;
	}

	@ClientProperty(escapeValue = "true")
	public boolean isShowHideModeConfig() {
		return showHideModeConfig;
	}

	public void setShowHideModeConfig(boolean showHideModeConfig) {
		this.showHideModeConfig = showHideModeConfig;
	}
	
	@Override
	public void setParent(ViewElement parent) {
		super.setParent(parent);
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
