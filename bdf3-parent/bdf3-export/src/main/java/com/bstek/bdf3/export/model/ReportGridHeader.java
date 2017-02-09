package com.bstek.bdf3.export.model;

import java.util.ArrayList;
import java.util.List;

public class ReportGridHeader implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String columnName;
	private String label;
	private int fontSize;
	private int[] bgColor;
	private int[] fontColor;
	private int level;
	private int align;
	private int dataAlign;
	private int width;
	private ReportGridHeader parent;
	private List<ReportGridHeader> headers = new ArrayList<ReportGridHeader>();
	public String getColumnName() {
		return columnName;
	}
	public String getLabel() {
		return label;
	}
	public int getFontSize() {
		return fontSize;
	}
	public int[] getBgColor() {
		return bgColor;
	}
	public int[] getFontColor() {
		return fontColor;
	}
	public int getLevel() {
		return level;
	}
	public int getAlign() {
		return align;
	}
	public int getWidth() {
		return width;
	}
	public ReportGridHeader getParent() {
		return parent;
	}
	public List<ReportGridHeader> getHeaders() {
		return headers;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public void setBgColor(int[] bgColor) {
		this.bgColor = bgColor;
	}
	public void setFontColor(int[] fontColor) {
		this.fontColor = fontColor;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setAlign(int align) {
		this.align = align;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setParent(ReportGridHeader parent) {
		this.parent = parent;
	}
	public void setHeaders(List<ReportGridHeader> headers) {
		this.headers = headers;
	}
	public void addGridHeader(ReportGridHeader header) {
		headers.add(header);
	}
	public int getDataAlign() {
		return dataAlign;
	}
	public void setDataAlign(int dataAlign) {
		this.dataAlign = dataAlign;
	}

}
