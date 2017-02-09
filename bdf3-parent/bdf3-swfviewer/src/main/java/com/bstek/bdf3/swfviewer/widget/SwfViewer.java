package com.bstek.bdf3.swfviewer.widget;

import com.bstek.dorado.annotation.ClientObject;
import com.bstek.dorado.annotation.ClientProperty;
import com.bstek.dorado.annotation.IdeProperty;
import com.bstek.dorado.view.annotation.Widget;
import com.bstek.dorado.view.widget.Control;

@Widget(name = "SwfViewer", dependsPackage = "swfviewer", category = "BDF3")
@ClientObject(prototype = "dorado.widget.SwfViewer", shortTypeName = "SwfViewer")
public class SwfViewer extends Control {

	private String swfUrl;

	private String handlerName;

	private String parameter;

	private String version;

	private String expressInstallSwfurl;

	private String flashvars;

	private String params;

	private String attributes;

	private ShowType showType;
	
	private boolean printEnabled;

	public SwfViewer() {
		setWidth("180");
		setHeight("180");
		setShowType(ShowType.complex);
		setPrintEnabled(true);
	}

	@ClientProperty(escapeValue = "400")
	@Override
	public String getWidth() {
		return super.getWidth();
	}

	@ClientProperty(escapeValue = "400")
	@Override
	public String getHeight() {
		return super.getHeight();
	}

	@IdeProperty(highlight = 1)
	public String getSwfUrl() {
		return swfUrl;
	}

	public void setSwfUrl(String swfUrl) {
		this.swfUrl = swfUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getExpressInstallSwfurl() {
		return expressInstallSwfurl;
	}

	public void setExpressInstallSwfurl(String expressInstallSwfurl) {
		this.expressInstallSwfurl = expressInstallSwfurl;
	}

	public String getFlashvars() {
		return flashvars;
	}

	public void setFlashvars(String flashvars) {
		this.flashvars = flashvars;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	@ClientProperty(escapeValue = "complex")
	public ShowType getShowType() {
		return showType;
	}

	public void setShowType(ShowType showType) {
		this.showType = showType;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	@ClientProperty(escapeValue = "true")
	public boolean isPrintEnabled() {
		return printEnabled;
	}

	public void setPrintEnabled(boolean printEnabled) {
		this.printEnabled = printEnabled;
	}

}
