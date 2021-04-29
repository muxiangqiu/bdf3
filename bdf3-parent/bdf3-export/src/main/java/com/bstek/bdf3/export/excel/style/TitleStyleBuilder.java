package com.bstek.bdf3.export.excel.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import com.bstek.bdf3.export.model.ReportTitle;
import com.bstek.bdf3.export.model.ReportTitleStyle;

public class TitleStyleBuilder extends AbstractStyleBuilder {

  public CellStyle builder(ReportTitle reportTitle, Workbook wb) {
    if (reportTitle.getStyle() == null) {
      return null;
    }
    ReportTitleStyle style = reportTitle.getStyle();
    int[] bgColor = style.getBgColor();
    int[] fontColor = style.getFontColor();
    int fontSize = style.getFontSize();
    if (wb instanceof HSSFWorkbook) {
      return createHSSFCellStyle(wb, bgColor, fontColor, fontSize);
    } else if (wb instanceof SXSSFWorkbook) {
      return createXSSFCellStyle(wb, bgColor, fontColor, fontSize);
    }
    return null;
  }

  private HSSFCellStyle createHSSFCellStyle(Workbook wb, int[] bgColor, int[] fontColor,
      int fontSize) {
    HSSFWorkbook workbook = (HSSFWorkbook) wb;
    HSSFPalette palette = workbook.getCustomPalette();

    palette.setColorAtIndex((short) 9, (byte) fontColor[0], (byte) fontColor[1],
        (byte) fontColor[2]);
    palette.setColorAtIndex((short) 10, (byte) bgColor[0], (byte) bgColor[1], (byte) bgColor[2]);

    HSSFFont titleFont = workbook.createFont();
    titleFont.setCharSet(HSSFFont.DEFAULT_CHARSET);
    titleFont.setFontName("宋体");
    titleFont.setColor((short) 9);
    titleFont.setBold(true);
    titleFont.setFontHeightInPoints((short) fontSize);

    HSSFCellStyle titleStyle = (HSSFCellStyle) createBorderCellStyle(workbook, true);
    titleStyle.setFont(titleFont);
    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    titleStyle.setFillForegroundColor((short) 10);
    titleStyle.setAlignment(HorizontalAlignment.CENTER);
    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    return titleStyle;
  }

  private XSSFCellStyle createXSSFCellStyle(Workbook wb, int[] bgColor, int[] fontColor,
      int fontSize) {
    SXSSFWorkbook workbook = (SXSSFWorkbook) wb;
    XSSFFont titleFont = (XSSFFont) workbook.createFont();
    titleFont.setCharSet(HSSFFont.DEFAULT_CHARSET);
    titleFont.setFontName("宋体");

    XSSFColor color9 = new XSSFColor(new java.awt.Color(fontColor[0], fontColor[1], fontColor[2]));
    XSSFColor color10 = new XSSFColor(new java.awt.Color(bgColor[0], bgColor[1], bgColor[2]));

    if (!(fontColor[0] == 0 && fontColor[1] == 0 && fontColor[2] == 0)) {
      titleFont.setColor(color9);
    }
    titleFont.setBold(true);
    titleFont.setFontHeightInPoints((short) fontSize);

    XSSFCellStyle titleStyle = (XSSFCellStyle) createBorderCellStyle(workbook, true);
    titleStyle.setFont(titleFont);
    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    titleStyle.setFillForegroundColor(color10);
    titleStyle.setAlignment(HorizontalAlignment.CENTER);
    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    return titleStyle;
  }

}
