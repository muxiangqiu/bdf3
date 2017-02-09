package com.bstek.bdf3.export.model;

import java.util.List;
import java.util.Map;

public class ReportGridData implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int[] contentBgColor;
	private int contentFontAlign;
	private int contentFontSize;
	private int[] contentFontColor;
	private List<Map<String, Object>> datas;
	private String treeColumn;

	public int[] getContentBgColor() {
		return contentBgColor;
	}
	public void setContentBgColor(int[] contentBgColor) {
		this.contentBgColor = contentBgColor;
	}
	public int getContentFontAlign() {
		return contentFontAlign;
	}
	public void setContentFontAlign(int contentFontAlign) {
		this.contentFontAlign = contentFontAlign;
	}
	public int getContentFontSize() {
		return contentFontSize;
	}
	public void setContentFontSize(int contentFontSize) {
		this.contentFontSize = contentFontSize;
	}
	public int[] getContentFontColor() {
		return contentFontColor;
	}
	public void setContentFontColor(int[] contentFontColor) {
		this.contentFontColor = contentFontColor;
	}
	public List<Map<String, Object>> getDatas() {
		return datas;
	}
	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}
	public String getTreeColumn() {
		return treeColumn;
	}
	public void setTreeColumn(String treeColumn) {
		this.treeColumn = treeColumn;
	}
}
