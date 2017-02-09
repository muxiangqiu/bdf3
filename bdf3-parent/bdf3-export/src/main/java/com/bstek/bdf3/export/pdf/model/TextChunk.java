package com.bstek.bdf3.export.pdf.model;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

public class TextChunk implements java.io.Serializable {
	private static final long serialVersionUID = -958134552108683L;
	private String text;
	private int[] fontColor = {0, 0, 0};
	private int fontSize = 9;
	private int fontStyle = Font.NORMAL;
	private int align = Element.ALIGN_CENTER;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int[] getFontColor() {
		return fontColor;
	}
	public void setFontColor(int[] fontColor) {
		this.fontColor = fontColor;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	public int getAlign() {
		return align;
	}
	public void setAlign(int align) {
		this.align = align;
	}
}
