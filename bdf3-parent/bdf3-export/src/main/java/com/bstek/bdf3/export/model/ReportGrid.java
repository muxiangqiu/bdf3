package com.bstek.bdf3.export.model;

import java.util.ArrayList;
import java.util.List;

public class ReportGrid {

	private List<ReportGridHeader> gridHeaderModelList;
	private ReportGridData gridDataModel;
	private String fileName;
	private String extension;
	private int columnCount;
	private int maxHeaderLevel;
	private boolean showBorder = true;
	private boolean showHeader = true;

	public List<ReportGridHeader> getGridHeaderModelList() {
		return gridHeaderModelList;
	}

	public ReportGridData getGridDataModel() {
		return gridDataModel;
	}

	public List<ReportGridHeader> getColumnHeaders() {
		List<ReportGridHeader> result = new ArrayList<ReportGridHeader>();
		this.calculateBottomColumnHeader(this.getGridHeaderModelList(), result);
		return result;
	}

	private void calculateBottomColumnHeader(List<ReportGridHeader> gridHeader, List<ReportGridHeader> result) {
		for (ReportGridHeader header : gridHeader) {
			if (header.getHeaders().size() == 0) {
				result.add(header);
			} else {
				this.calculateBottomColumnHeader(header.getHeaders(), result);
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMaxHeaderLevel() {
		return maxHeaderLevel;
	}

	public boolean isShowBorder() {
		return showBorder;
	}

	public boolean isShowHeader() {
		return showHeader;
	}

	public void setGridHeaderModelList(List<ReportGridHeader> gridHeaderModelList) {
		this.gridHeaderModelList = gridHeaderModelList;
	}

	public void setGridDataModel(ReportGridData gridDataModel) {
		this.gridDataModel = gridDataModel;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public void setMaxHeaderLevel(int maxHeaderLevel) {
		this.maxHeaderLevel = maxHeaderLevel;
	}

	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
