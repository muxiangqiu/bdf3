package com.bstek.bdf3.export.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractStyleBuilder {

	public CellStyle createBorderCellStyle(Workbook workbook, boolean showBorder) {
		CellStyle style = workbook.createCellStyle();
		if (showBorder) {
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderBottom(CellStyle.BORDER_THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		}
		return style;
	}

	public void setCellStyleFont(Workbook workbook, CellStyle style, int i) {
		Font font = workbook.createFont();
		if (i == 0) {
			// 正常
		} else if (i == 4) {
			// 下划线
			font.setUnderline(Font.U_SINGLE);
			style.setFont(font);
		} else if (i == 2) {
			// 倾斜
			font.setItalic(true);
			style.setFont(font);
		} else if (i == 1) {
			// 加粗
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}
	}

	public void setCellStyleAligment(CellStyle style, int i) {
		if (i == 0) {
			style.setAlignment(CellStyle.ALIGN_LEFT);
		} else if (i == 1) {
			style.setAlignment(CellStyle.ALIGN_CENTER);
		} else if (i == 2) {
			style.setAlignment(CellStyle.ALIGN_RIGHT);
		}
	}
}
