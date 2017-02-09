package com.bstek.bdf3.export.model;

public class ReportTitle {
	private boolean showPageNo = true;
	private boolean repeatHeader = false;
	private boolean showBorder = true;
	private boolean showTitle = false;
	private String title;
	private ReportTitleStyle style;
	public boolean isShowPageNo() {
		return showPageNo;
	}
	public boolean isRepeatHeader() {
		return repeatHeader;
	}
	public boolean isShowBorder() {
		return showBorder;
	}
	public boolean isShowTitle() {
		return showTitle;
	}
	public ReportTitleStyle getStyle() {
		return style;
	}
	public void setShowPageNo(boolean showPageNo) {
		this.showPageNo = showPageNo;
	}
	public void setRepeatHeader(boolean repeatHeader) {
		this.repeatHeader = repeatHeader;
	}
	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}
	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}
	public void setStyle(ReportTitleStyle style) {
		this.style = style;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
