package com.bstek.bdf3.export.excel.style;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import com.bstek.bdf3.export.model.ReportGrid;
import com.bstek.bdf3.export.model.ReportGridData;
import com.bstek.bdf3.export.model.ReportGridHeader;

public class GridStyleBuilder extends AbstractStyleBuilder {

  public Map<String, CellStyle> builderGridStyles(Workbook wb, ReportGrid gridModel) {
    Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
    ReportGridData gridData = gridModel.getGridDataModel();
    int[] contextBgColor = gridData.getContentBgColor();
    if (contextBgColor == null) {
      contextBgColor = new int[] {255, 255, 255};
    }
    int[] contextFontColor = gridData.getContentFontColor();
    if (contextFontColor == null) {
      contextFontColor = new int[] {0, 0, 0};
    }
    int contextFontAlign = gridData.getContentFontAlign();
    int contextFontSize = gridData.getContentFontSize();
    if (contextFontSize < 1) {
      contextFontSize = 10;
    }
    List<ReportGridHeader> headerList = gridModel.getGridHeaderModelList();
    ReportGridHeader header = headerList.get(0);
    int headerAlign = header.getAlign();
    int[] headerBgColor = header.getBgColor();
    if (headerBgColor == null) {
      headerBgColor = new int[] {216, 216, 216};
    }
    int[] headerFontColor = header.getFontColor();
    if (headerFontColor == null) {
      headerFontColor = new int[] {0, 0, 0};
    }
    int headerFontSize = header.getFontSize();
    if (headerFontSize < 1) {
      headerFontSize = 10;
    }
    if (wb instanceof HSSFWorkbook) {
      return this.createHSSFCellStyles(wb, contextBgColor, contextFontColor, contextFontSize,
          contextFontAlign, headerBgColor, headerFontColor, headerFontSize, headerAlign);
    } else if (wb instanceof SXSSFWorkbook) {
      return this.createXSSFCellStyles(wb, contextBgColor, contextFontColor, contextFontSize,
          contextFontAlign, headerBgColor, headerFontColor, headerFontSize, headerAlign);
    }
    return styles;
  }

  private Map<String, CellStyle> createHSSFCellStyles(Workbook wb, int[] contextBgColor,
      int[] contextFontColor, int contextFontSize, int contextFontAlign, int[] headerBgColor,
      int[] headerFontColor, int headerFontSize, int headerAlign) {
    Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

    HSSFWorkbook workbook = (HSSFWorkbook) wb;
    HSSFPalette palette = workbook.getCustomPalette();
    palette.setColorAtIndex((short) 11, (byte) contextBgColor[0], (byte) contextBgColor[1],
        (byte) contextBgColor[2]);
    palette.setColorAtIndex((short) 12, (byte) contextFontColor[0], (byte) contextFontColor[1],
        (byte) contextFontColor[2]);
    palette.setColorAtIndex((short) 13, (byte) headerBgColor[0], (byte) headerBgColor[1],
        (byte) headerBgColor[2]);
    palette.setColorAtIndex((short) 14, (byte) headerFontColor[0], (byte) headerFontColor[1],
        (byte) headerFontColor[2]);

    HSSFFont headerFont = workbook.createFont();
    headerFont.setCharSet(HSSFFont.DEFAULT_CHARSET);
    headerFont.setFontName("宋体");
    headerFont.setColor((short) 14);
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) headerFontSize);
    CellStyle headerStyle = this.createBorderCellStyle(workbook, true);

    headerStyle.setFont(headerFont);
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerStyle.setFillForegroundColor((short) 13);
    this.setCellStyleAligment(headerStyle, headerAlign);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    styles.put(GridStyleType.headerStyle.name(), headerStyle);

    HSSFFont dataFont = workbook.createFont();
    dataFont.setColor((short) 12);
    dataFont.setFontHeightInPoints((short) contextFontSize);
    dataFont.setCharSet(HSSFFont.DEFAULT_CHARSET);
    dataFont.setFontName("宋体");

    CellStyle dataAlignLeftStyle = this.createBorderCellStyle(workbook, true);
    dataAlignLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dataAlignLeftStyle.setFillForegroundColor((short) 11);
    dataAlignLeftStyle.setFont(dataFont);
    dataAlignLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dataAlignLeftStyle.setWrapText(true);
    dataAlignLeftStyle.setAlignment(HorizontalAlignment.LEFT);
    styles.put(GridStyleType.dataAlignLeftStyle.name(), dataAlignLeftStyle);

    CellStyle dataAlignCenterStyle = this.createBorderCellStyle(workbook, true);
    dataAlignCenterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dataAlignCenterStyle.setFillForegroundColor((short) 11);
    dataAlignCenterStyle.setFont(dataFont);
    dataAlignCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dataAlignCenterStyle.setWrapText(true);
    dataAlignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
    styles.put(GridStyleType.dataAlignCenterStyle.name(), dataAlignCenterStyle);

    CellStyle dataAlignRightStyle = this.createBorderCellStyle(workbook, true);
    dataAlignRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dataAlignRightStyle.setFillForegroundColor((short) 11);
    dataAlignRightStyle.setFont(dataFont);
    dataAlignRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dataAlignRightStyle.setWrapText(true);
    dataAlignRightStyle.setAlignment(HorizontalAlignment.RIGHT);
    styles.put(GridStyleType.dataAlignRightStyle.name(), dataAlignRightStyle);

    CellStyle dateStyle = this.createBorderCellStyle(workbook, true);
    CreationHelper helper = workbook.getCreationHelper();
    dateStyle.setDataFormat(helper.createDataFormat().getFormat("m/d/yy h:mm"));
    dateStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dateStyle.setFillForegroundColor((short) 11);
    dateStyle.setFont(dataFont);
    dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    this.setCellStyleAligment(dateStyle, contextFontAlign);
    styles.put(GridStyleType.dateStyle.name(), dateStyle);

    return styles;
  }

  private Map<String, CellStyle> createXSSFCellStyles(Workbook wb, int[] contextBgColor,
      int[] contextFontColor, int contextFontSize, int contextFontAlign, int[] headerBgColor,
      int[] headerFontColor, int headerFontSize, int headerAlign) {
    Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

    SXSSFWorkbook workbook = (SXSSFWorkbook) wb;
    XSSFColor xssfContextBgColor =
        new XSSFColor(new java.awt.Color(contextBgColor[0], contextBgColor[1], contextBgColor[2]));
    XSSFColor xssfContextFontColor = new XSSFColor(
        new java.awt.Color(contextFontColor[0], contextFontColor[1], contextFontColor[2]));
    XSSFColor xssfHeaderBgColor =
        new XSSFColor(new java.awt.Color(headerBgColor[0], headerBgColor[1], headerBgColor[2]));
    XSSFColor xssfHeaderFontColor = new XSSFColor(
        new java.awt.Color(headerFontColor[0], headerFontColor[1], headerFontColor[2]));

    XSSFFont headerFont = (XSSFFont) workbook.createFont();
    headerFont.setCharSet(HSSFFont.DEFAULT_CHARSET);
    headerFont.setFontName("宋体");
    if (!(headerFontColor[0] == 0 && headerFontColor[1] == 0 && headerFontColor[2] == 0)) {
      headerFont.setColor(xssfHeaderFontColor);
    }
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) headerFontSize);
    XSSFCellStyle headerStyle = (XSSFCellStyle) this.createBorderCellStyle(workbook, true);
    headerStyle.setFont(headerFont);
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerStyle.setFillForegroundColor(xssfHeaderBgColor);
    this.setCellStyleAligment(headerStyle, headerAlign);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    styles.put(GridStyleType.headerStyle.name(), headerStyle);

    XSSFFont dataFont = (XSSFFont) workbook.createFont();
    if (!(contextFontColor[0] == 0 && contextFontColor[1] == 0 && contextFontColor[2] == 0)) {
      dataFont.setColor(xssfContextFontColor);
    }
    dataFont.setFontHeightInPoints((short) contextFontSize);
    dataFont.setCharSet(HSSFFont.DEFAULT_CHARSET);
    dataFont.setFontName("宋体");

    XSSFCellStyle dataAlignLeftStyle = (XSSFCellStyle) this.createBorderCellStyle(workbook, true);
    dataAlignLeftStyle.setFont(dataFont);
    dataAlignLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dataAlignLeftStyle.setFillForegroundColor(xssfContextBgColor);
    dataAlignLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dataAlignLeftStyle.setWrapText(true);
    dataAlignLeftStyle.setAlignment(HorizontalAlignment.LEFT);
    styles.put(GridStyleType.dataAlignLeftStyle.name(), dataAlignLeftStyle);

    XSSFCellStyle dataAlignCenterStyle = (XSSFCellStyle) this.createBorderCellStyle(workbook, true);
    dataAlignCenterStyle.setFont(dataFont);
    dataAlignCenterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dataAlignCenterStyle.setFillForegroundColor(xssfContextBgColor);
    dataAlignCenterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dataAlignCenterStyle.setWrapText(true);
    dataAlignCenterStyle.setAlignment(HorizontalAlignment.CENTER);
    styles.put(GridStyleType.dataAlignCenterStyle.name(), dataAlignCenterStyle);

    XSSFCellStyle dataAlignRightStyle = (XSSFCellStyle) this.createBorderCellStyle(workbook, true);
    dataAlignRightStyle.setFont(dataFont);
    dataAlignRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dataAlignRightStyle.setFillForegroundColor(xssfContextBgColor);
    dataAlignRightStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    dataAlignRightStyle.setWrapText(true);
    dataAlignRightStyle.setAlignment(HorizontalAlignment.RIGHT);
    styles.put(GridStyleType.dataAlignRightStyle.name(), dataAlignRightStyle);

    XSSFCellStyle dateStyle = (XSSFCellStyle) this.createBorderCellStyle(workbook, true);
    CreationHelper helper = workbook.getCreationHelper();
    dateStyle.setDataFormat(helper.createDataFormat().getFormat("m/d/yy h:mm"));
    dateStyle.setFont(dataFont);
    dateStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dateStyle.setFillForegroundColor(xssfContextBgColor);
    dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    this.setCellStyleAligment(dateStyle, contextFontAlign);
    styles.put(GridStyleType.dateStyle.name(), dateStyle);

    return styles;
  }

  public CellStyle createIndentationCellStyle(Workbook workbook, int s) {
    CellStyle dataStyle1 = this.createBorderCellStyle(workbook, true);
    Font dataFont = workbook.createFont();
    dataFont.setColor((short) 12);
    dataFont.setFontHeightInPoints((short) 10);
    dataStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    dataStyle1.setFillForegroundColor((short) 11);
    dataStyle1.setFont(dataFont);
    dataStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
    dataStyle1.setAlignment(HorizontalAlignment.LEFT);
    dataStyle1.setIndention(Short.valueOf(String.valueOf((s))));
    return dataStyle1;
  }

}
