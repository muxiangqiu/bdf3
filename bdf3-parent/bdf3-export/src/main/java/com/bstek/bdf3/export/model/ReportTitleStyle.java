package com.bstek.bdf3.export.model;

public class ReportTitleStyle {
	private int[] bgColor;
	private int[] fontColor;
	private int fontSize;
	public int[] getBgColor() {
		return bgColor;
	}
	public int[] getFontColor() {
		return fontColor;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setBgColor(int[] bgColor) {
		this.bgColor = bgColor;
	}
	public void setFontColor(int[] fontColor) {
		this.fontColor = fontColor;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

}
