package com.bstek.bdf3.export.pdf.model;

public class ReportData implements java.io.Serializable {
	private static final long serialVersionUID = 4070768286694803327L;
	private TextChunk textChunk;
	private int align;
	private LabelData labelData;
	private int colSpan;
	private int rowSpan;
	private int[] bgColor;

	public ReportData(TextChunk textChunk) {
		this.textChunk = textChunk;
	}

	public TextChunk getTextChunk() {
		return textChunk;
	}

	public void setTextChunk(TextChunk textChunk) {
		this.textChunk = textChunk;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public LabelData getLabelData() {
		return labelData;
	}

	public void setLabelData(LabelData labelData) {
		this.labelData = labelData;
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
	public int[] getBgColor() {
		return bgColor;
	}

	public void setBgColor(int[] bgColor) {
		this.bgColor = bgColor;
	}

}
