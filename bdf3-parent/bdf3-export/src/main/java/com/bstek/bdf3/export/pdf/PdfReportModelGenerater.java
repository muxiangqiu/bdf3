package com.bstek.bdf3.export.pdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.bdf3.export.AbstractReportModelGenerater;
import com.bstek.bdf3.export.pdf.model.ColumnHeader;
import com.bstek.bdf3.export.pdf.model.ReportData;
import com.bstek.bdf3.export.pdf.model.ReportDataModel;
import com.bstek.bdf3.export.pdf.model.TextChunk;

@Component(PdfReportModelGenerater.BEAN_ID)
public class PdfReportModelGenerater extends AbstractReportModelGenerater {
	
	public static final String BEAN_ID="bdf3.PdfReportModelGenerater";


	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings({"unchecked"})
	public ReportDataModel generateReportGridModel(Map<String, Object> map, String intercepterBean) throws Exception {
		List<Map<String, Object>> columnInfos = (List<Map<String, Object>>) map.get("columnInfos");
		Map<String, Object> gridDataStyle = (Map<String, Object>) map.get("gridDataStyle");

		Map<String, Integer> columDataAlignMap = new HashMap<String, Integer>();
		this.calculateColumnAlign(columnInfos, columDataAlignMap);

		List<ColumnHeader> topColumnHeaders = new ArrayList<ColumnHeader>();
		createGridColumnHeader(columnInfos, topColumnHeaders, null);

		List<Map<String, Object>> dataMapList = getGridModelData(map, columnInfos, intercepterBean);
		List<ReportData> reportDataList = createGridColumnData(dataMapList, topColumnHeaders, gridDataStyle, columDataAlignMap);
		ReportDataModel dataModel = new ReportDataModel(topColumnHeaders, reportDataList);
		
		return dataModel;
	}

	private List<ReportData> createGridColumnData(List<Map<String, Object>> dataList, List<ColumnHeader> parentColumnHeaders, Map<String, Object> style, Map<String, Integer> dataColumnAlign) {
		List<ReportData> result = new ArrayList<ReportData>();
		List<String> dataColumnNameList = new ArrayList<String>();
		calculateBottomColumnNames(parentColumnHeaders, dataColumnNameList);
		for (Map<String, Object> dataMap : dataList) {
			for (String name : dataColumnNameList) {
				Object data = dataMap.get(name);
				TextChunk textChunk = new TextChunk();
				String value = "";
				if (data != null && data instanceof Date) {
					value = simpleDateFormat.format((Date) data);
					if (value.endsWith("00:00:00")) {
						value = value.substring(0, 11);
					}
				} else {
					if (data != null) {
						value = data.toString();
					}
				}
				int dataAlign = (Integer) dataColumnAlign.get(name);
				textChunk.setFontSize((Integer) style.get("fontSize"));
				textChunk.setFontColor(this.createRGB((String) style.get("fontColor")));
				textChunk.setAlign((Integer) style.get("fontAlign"));
				textChunk.setText(value);
				ReportData columnData = new ReportData(textChunk);
				columnData.setAlign(dataAlign);
				columnData.setBgColor(this.createRGB((String) style.get("bgColor")));
				result.add(columnData);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private void createGridColumnHeader(List<Map<String, Object>> columnsInfo, List<ColumnHeader> topColumnHeaders, ColumnHeader parentHeader) throws Exception {
		for (Map<String, Object> column : columnsInfo) {
			String columnName = (String) column.get("columnName");
			int level = (Integer) column.get("level");
			String label = (String) column.get("label");
			int width = (Integer) column.get("width");
			String bgColor = (String) column.get("bgColor");
			String fontColor = (String) column.get("fontColor");
			int align = (Integer) column.get("align");
			int fontSize = (Integer) column.get("fontSize");

			ColumnHeader header = new ColumnHeader(level);
			header.setAlign(align);
			header.setBgColor(this.createRGB(bgColor));
			header.setFontColor(this.createRGB(fontColor));
			header.setName(columnName);
			header.setText(label);
			header.setFontSize(fontSize);
			header.setWidth(width);
			if (parentHeader != null) {
				parentHeader.addColumnHeader(header);
			} else {
				topColumnHeaders.add(header);
			}
			List<Map<String, Object>> children = (List<Map<String, Object>>) column.get("children");
			if (children != null) {
				createGridColumnHeader(children, topColumnHeaders, header);
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void calculateColumnAlign(List<Map<String, Object>> columnsInfo, Map<String, Integer> result) {
		for (Map<String, Object> column : columnsInfo) {
			String columnName = (String) column.get("columnName");
			int dataAlign = 1;
			if (column.get("dataAlign") != null) {
				dataAlign = (Integer) column.get("dataAlign");
			}
			result.put(columnName, dataAlign);
			List<Map<String, Object>> children = (List<Map<String, Object>>) column.get("children");
			if (children != null) {
				calculateColumnAlign(children, result);
			}
		}
	}
	private void calculateBottomColumnNames(List<ColumnHeader> parentColumnHeaders, List<String> result) {
		for (ColumnHeader header : parentColumnHeaders) {
			if (header.getColumnHeaders().size() == 0) {
				result.add(header.getName());
			} else {
				calculateBottomColumnNames(header.getColumnHeaders(), result);
			}
		}
	}
}
