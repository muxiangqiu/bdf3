package com.bstek.bdf3.export.model;

import java.io.Serializable;

public class ReportFormData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String label;
	private int labelAlign;
	private Object data;
	private int dataAlign;
	private int dataStyle;
	private int colSpan;
	private int rowSpan;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getLabelAlign() {
		return labelAlign;
	}
	public void setLabelAlign(int labelAlign) {
		this.labelAlign = labelAlign;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public int getDataAlign() {
		return dataAlign;
	}
	public void setDataAlign(int dataAlign) {
		this.dataAlign = dataAlign;
	}
	public int getDataStyle() {
		return dataStyle;
	}
	public void setDataStyle(int dataStyle) {
		this.dataStyle = dataStyle;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}
}
