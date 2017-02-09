package com.bstek.bdf3.export.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.export.AbstractReportModelGenerater;
import com.bstek.bdf3.export.model.ReportGrid;
import com.bstek.bdf3.export.model.ReportGridData;
import com.bstek.bdf3.export.model.ReportGridHeader;

@Component(ExcelReportModelGenerater.BEAN_ID)
public class ExcelReportModelGenerater extends AbstractReportModelGenerater {
	
	public static final String BEAN_ID="bdf3.ExcelReportModelGenerater";

	@SuppressWarnings("unchecked")
	public ReportGrid generateReportGridModel(Map<String, Object> map, String intercepterBean) throws Exception {
		ReportGrid gridModel = new ReportGrid();
		List<Map<String, Object>> columnInfos = (List<Map<String, Object>>) map.get("columnInfos");
		List<ReportGridHeader> gridHeaders = new ArrayList<ReportGridHeader>();
		this.createGridColumnHeader(columnInfos, gridHeaders, null);
		gridModel.setGridHeaderModelList(gridHeaders);
		gridModel.setGridDataModel(this.createGridColumnData(map, columnInfos, intercepterBean));
		return gridModel;
	}

	@SuppressWarnings("unchecked")
	private void createGridColumnHeader(List<Map<String, Object>> columnInfos, List<ReportGridHeader> headerList, ReportGridHeader parent) {
		if (columnInfos == null)
			return;
		for (Map<String, Object> column : columnInfos) {
			String columnName = (String) column.get("columnName");
			int level = (Integer) column.get("level");
			String label = (String) column.get("label");
			int width = (Integer) column.get("width");
			String bgColor = (String) column.get("bgColor");
			String fontColor = (String) column.get("fontColor");
			int fontSize = 10;
			if (column.get("fontSize") != null) {
				fontSize = (Integer) column.get("fontSize");
			}
			int align = 1;
			if (column.get("align") != null) {
				align = (Integer) column.get("align");
			}
			int dataAlign = 1;
			if (column.get("dataAlign") != null) {
				dataAlign = (Integer) column.get("dataAlign");
			}
			ReportGridHeader header = new ReportGridHeader();
			header.setLevel(level);
			header.setAlign(align);
			header.setDataAlign(dataAlign);
			if (StringUtils.isNotEmpty(bgColor)) {
				header.setBgColor(this.createRGB(bgColor));
			}
			if (StringUtils.isNotEmpty(fontColor)) {
				header.setFontColor(this.createRGB(fontColor));
			}
			header.setColumnName(columnName);
			header.setLabel(label);
			header.setFontSize(fontSize);
			header.setWidth(width);
			if (parent != null) {
				parent.addGridHeader(header);
				header.setParent(parent);
			} else {
				headerList.add(header);
			}
			List<Map<String, Object>> children = (List<Map<String, Object>>) column.get("children");
			if (children != null) {
				this.createGridColumnHeader(children, headerList, header);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private ReportGridData createGridColumnData(Map<String, Object> map, List<Map<String, Object>> columnInfos, String intercepterBean) throws Exception {
		Map<String, Object> style = (Map<String, Object>) map.get("gridDataStyle");
		String treeColumn = (String) map.get("treeColumn");
		List<Map<String, Object>> dataList = getGridModelData(map, columnInfos, intercepterBean);
		ReportGridData gridData = new ReportGridData();
		gridData.setDatas(dataList);
		gridData.setTreeColumn(treeColumn);
		if (style != null) {
			gridData.setContentFontSize((Integer) style.get("fontSize"));
			String contentFontColor = (String) style.get("fontColor");
			String contentBgColor = (String) style.get("bgColor");
			gridData.setContentFontColor(this.createRGB(contentFontColor));
			gridData.setContentFontAlign((Integer) style.get("fontAlign"));
			gridData.setContentBgColor(this.createRGB(contentBgColor));
		}
		return gridData;
	}

}
