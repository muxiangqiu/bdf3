package com.bstek.bdf3.export.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.export.excel.style.FormStyleBuilder;
import com.bstek.bdf3.export.excel.style.GridStyleBuilder;
import com.bstek.bdf3.export.excel.style.GridStyleType;
import com.bstek.bdf3.export.excel.style.TitleStyleBuilder;
import com.bstek.bdf3.export.model.ReportForm;
import com.bstek.bdf3.export.model.ReportFormData;
import com.bstek.bdf3.export.model.ReportGrid;
import com.bstek.bdf3.export.model.ReportGridHeader;
import com.bstek.bdf3.export.model.ReportTitle;
import com.bstek.bdf3.export.utils.ExportUtils;

@Component(ExcelReportBuilder.BEAN_ID)
public class ExcelReportBuilder extends AbstractExcelReportBuilder {

	public static final String BEAN_ID = "bdf3.ExcelReportBuilder";

	public int addTitleToSheet(ReportTitle reportTitle, Sheet sheet, int lastCol) {
		return this.addTitleToSheet(reportTitle, sheet, 0, 0, lastCol);
	}

	public int addTitleToSheet(ReportTitle reportTitle, Sheet sheet, int row, int firstCol, int lastCol) {
		if (!reportTitle.isShowTitle()) {
			return row;
		}
		Row titleRow = sheet.createRow(row);
		Cell titleCell = titleRow.createCell(firstCol);
		titleCell.setCellType(Cell.CELL_TYPE_STRING);
		titleCell.setCellValue(reportTitle.getTitle());
		CellStyle titleStyle = new TitleStyleBuilder().builder(reportTitle, sheet.getWorkbook());
		titleCell.setCellStyle(titleStyle);
		CellRangeAddress rangle = new CellRangeAddress(row, row, firstCol, lastCol);
		sheet.addMergedRegion(rangle);
		this.setCellRangeAddressBorder(rangle, sheet);
		return row + 1;
	}

	public int addFormToSheet(ReportForm reportFormModel, Sheet sheet, int rowNo) throws Exception {
		rowNo = buildFormExcelData(reportFormModel, sheet, rowNo);
		return rowNo;
	}

	public int addGridToSheet(ReportGrid reportGridModel, Sheet sheet, int initRowNo) {
		Map<String, CellStyle> styles = new GridStyleBuilder().builderGridStyles(sheet.getWorkbook(), reportGridModel);
		int nextRow = this.buildGridExcelHeader(reportGridModel, sheet, initRowNo, styles);
		nextRow = this.buildGridExcelData(reportGridModel, sheet, nextRow, styles);
		return nextRow;
	}

	private int buildGridExcelHeader(ReportGrid gridModel, Sheet sheet, int starHeaderRow, Map<String, CellStyle> styles) {
		Map<Integer, Object> rowMap = new HashMap<Integer, Object>();
		this.calculateMaxHeaderLevel(gridModel, gridModel.getGridHeaderModelList());
		int maxHeaderLevel = gridModel.getMaxHeaderLevel();
		for (int i = 0; i < maxHeaderLevel; i++) {
			Row row = sheet.createRow((short) i + starHeaderRow);
			rowMap.put(i + starHeaderRow, row);
		}
		List<ReportGridHeader> topHeaders = new ArrayList<ReportGridHeader>();
		calculateGridHeadersByLevel(gridModel.getGridHeaderModelList(), 1, topHeaders);
		this.buildGridExcelHeader(sheet, rowMap, maxHeaderLevel, 1, starHeaderRow, 0, topHeaders, styles);
		return starHeaderRow + maxHeaderLevel;

	}

	private void buildGridExcelHeader(Sheet sheet, Map<Integer, Object> rowMap, int maxHeaderLevel, int currentLevel, int startHeaderRow, int startHeaderCol, List<ReportGridHeader> topHeaders,
			Map<String, CellStyle> styles) {
		CellStyle headerStyle = styles.get(GridStyleType.headerStyle.name());
		int currentRow = startHeaderRow + currentLevel - 1;
		Cell cell;
		Row row = (Row) rowMap.get(currentRow);
		int currentCol = startHeaderCol;
		CellRangeAddress cellRangeAddress;
		for (ReportGridHeader headerModel : topHeaders) {
			cell = row.createCell((short) currentCol);
			cell.setCellValue(headerModel.getLabel());
			int firstRow = currentRow;
			int lastRow = currentRow;
			int firstCol = currentCol;
			int lastCol = currentCol;
			int colspan = calculateGridHeaderColspan(headerModel);
			cell.setCellStyle(headerStyle);
			if (headerModel.getHeaders().size() > 0){
				cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol + colspan - 1);
				sheet.addMergedRegion(cellRangeAddress);
				this.setCellRangeAddressBorder(cellRangeAddress, sheet);
				this.buildGridExcelHeader(sheet, rowMap, maxHeaderLevel, headerModel.getLevel() + 1, startHeaderRow, firstCol, headerModel.getHeaders(), styles);
			}
			currentCol = currentCol + colspan;
		}

	}

	private int buildFormExcelData(ReportForm reportFormModel, Sheet sheet, int initDataRow) throws Exception {
		SimpleDateFormat sdf = ExportUtils.getSimpleDateFormat();
		List<ReportFormData> formExcelDataList = reportFormModel.getListReportFormDataModel();

		FormStyleBuilder formStyleBuilder = new FormStyleBuilder();
		CellStyle labelCellStyle = formStyleBuilder.builderLabelCellStyle(reportFormModel, sheet.getWorkbook());
		CellStyle dataCellStyle = formStyleBuilder.builderValueCellStyle(reportFormModel, sheet.getWorkbook());

		Map<Integer, List<ReportFormData>> group = new HashMap<Integer, List<ReportFormData>>();
		List<ReportFormData> currentReportFormDataModels = new ArrayList<ReportFormData>();
		int rowIndx = 0;
		int colCount = reportFormModel.getColumnCount() * 2;
		int currentRowColumnSize = 0;
		int i = 1;
		for (ReportFormData reportFormDataModel : formExcelDataList) {
			int colSpan = reportFormDataModel.getColSpan();
			int formDataColumnSize = colSpan * 2 - 1 + 1;
			if (currentRowColumnSize + formDataColumnSize <= colCount) {
				currentRowColumnSize = currentRowColumnSize + formDataColumnSize;
			} else {
				group.put(rowIndx, currentReportFormDataModels);
				currentRowColumnSize = formDataColumnSize;
				currentReportFormDataModels = new ArrayList<ReportFormData>();
				rowIndx++;
			}
			currentReportFormDataModels.add(reportFormDataModel);
			if (i == formExcelDataList.size()) {
				group.put(rowIndx, currentReportFormDataModels);
			}
			i++;
		}
		CellRangeAddress cellRangeAddress;
		Row row;
		Cell cellLabel;
		Cell cellData;
		for (Map.Entry<Integer, List<ReportFormData>> entry : group.entrySet()) {
			int key = entry.getKey();
			int currentRow = initDataRow + key;
			row = sheet.createRow(currentRow);
			List<ReportFormData> value = entry.getValue();
			int firstCol = 0;
			for (ReportFormData reportFormDataModel : value) {
				String label = reportFormDataModel.getLabel();
				Object data = reportFormDataModel.getData();
				int colSpan = reportFormDataModel.getColSpan();

				cellLabel = row.createCell(firstCol);
				cellLabel.setCellValue(label);
				cellLabel.setCellStyle(labelCellStyle);
				sheet.setColumnWidth(cellLabel.getColumnIndex(), 30 * 256);

				firstCol = firstCol + 1;
				cellData = row.createCell(firstCol);
				cellData.setCellStyle(dataCellStyle);
				this.fillCellValue(cellData, data, sdf);
				sheet.setColumnWidth(cellData.getColumnIndex(), 30 * 256);
								
				int lastCol = firstCol + colSpan * 2 - 2;
				if(firstCol != lastCol){
					cellRangeAddress = new CellRangeAddress(currentRow, currentRow, firstCol, lastCol);
					sheet.addMergedRegion(cellRangeAddress);
					this.setFormRegionStyle(sheet, cellRangeAddress, dataCellStyle);
				}
				firstCol = firstCol + colSpan * 2 - 1;
			}
		}
		return group.keySet().size() + initDataRow;
	}

	private int buildGridExcelData(ReportGrid gridModel, Sheet sheet, int starDataRow, Map<String, CellStyle> styles) {
		CellStyle dataAlignLeftStyle = styles.get(GridStyleType.dataAlignLeftStyle.name());
		CellStyle dataAlignCenterStyle = styles.get(GridStyleType.dataAlignCenterStyle.name());
		CellStyle dataAlignRightStyle = styles.get(GridStyleType.dataAlignRightStyle.name());
		
		SimpleDateFormat sdf = ExportUtils.getSimpleDateFormat();
		List<ReportGridHeader> bottomGridExcelHeader = new ArrayList<ReportGridHeader>();
		this.calculateBottomColumnHeader(gridModel.getGridHeaderModelList(), bottomGridExcelHeader);
		
		List<Map<String, Object>> excelDatas = gridModel.getGridDataModel().getDatas();
		String treeColumn = gridModel.getGridDataModel().getTreeColumn();
		int excelDataIndex = 0;
		int rowSize = excelDatas.size();
		Cell cell;
		Row row;
		for (int rowNum = starDataRow; rowNum <= starDataRow + rowSize - 1; rowNum++) {
			row = sheet.createRow(rowNum);
			Map<String, Object> map = excelDatas.get(excelDataIndex);
			int j = 0;
			for (ReportGridHeader header : bottomGridExcelHeader) {
				Object value = map.get(header.getColumnName());
				int dataAlign = header.getDataAlign();
				cell = row.createCell(j);
				if (dataAlign == 1) {
					cell.setCellStyle(dataAlignCenterStyle);
				} else if (dataAlign == 2) {
					cell.setCellStyle(dataAlignRightStyle);
				} else {
					cell.setCellStyle(dataAlignLeftStyle);
				}
				if (value != null) {
					if (header.getColumnName().equalsIgnoreCase(treeColumn)) {
						int level = this.calculateIndentationCount(value.toString());
						cell.setCellStyle(new GridStyleBuilder().createIndentationCellStyle(sheet.getWorkbook(), level == 0 ? 0 : level * 2));
						cell.setCellValue(value.toString());
					} else {
						this.fillCellValue(cell, value, sdf);
					}

				} else {
					cell.setCellValue("");
				}
				sheet.setColumnWidth(cell.getColumnIndex(), header.getWidth() / 6 > 255 ? 254 * 256 : header.getWidth() / 6 * 256);
				j++;
			}
			excelDataIndex++;
		}
		return starDataRow + rowSize;

	}

	private void setCellRangeAddressBorder(CellRangeAddress rangle, Sheet sheet) {
	    BorderStyle border = BorderStyle.THIN;
	    RegionUtil.setBorderBottom(border, rangle, sheet);
	    RegionUtil.setBorderLeft(border, rangle, sheet);
	    RegionUtil.setBorderRight(border, rangle, sheet);
	    RegionUtil.setBorderTop(border, rangle, sheet);
	}

	private void setFormRegionStyle(Sheet sheet, CellRangeAddress ca, CellStyle cs) {
		for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
			Row row = CellUtil.getRow(i, sheet);
			for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
				Cell cell = CellUtil.getCell(row, j);
				cell.setCellStyle(cs);
			}
		}
	}

	private int calculateIndentationCount(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			char temp = s.charAt(i);
			if (temp == '\u0009') {
				count++;
			}
		}
		return count;
	}

}
