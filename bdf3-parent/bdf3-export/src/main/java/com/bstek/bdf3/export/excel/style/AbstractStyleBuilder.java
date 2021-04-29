package com.bstek.bdf3.export.excel.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractStyleBuilder {

	public CellStyle createBorderCellStyle(Workbook workbook, boolean showBorder) {
		CellStyle style = workbook.createCellStyle();
		if (showBorder) {
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderTop(BorderStyle.THIN);
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
            font.setBold(true);
			style.setFont(font);
		}
	}

	public void setCellStyleAligment(CellStyle style, int i) {
        if (i == 0) {
            style.setAlignment(HorizontalAlignment.LEFT);
        } else if (i == 1) {
            style.setAlignment(HorizontalAlignment.CENTER);
        } else if (i == 2) {
            style.setAlignment(HorizontalAlignment.RIGHT);
        }
	}
}
