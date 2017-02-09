
package com.bstek.bdf3.export.model;

import java.io.Serializable;
import java.util.List;

public class ReportForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private int columnCount;
	private boolean showBorder;
	private List<ReportFormData> listReportFormData;
	public int getColumnCount() {
		return columnCount;
	}
	public boolean isShowBorder() {
		return showBorder;
	}
	public List<ReportFormData> getListReportFormDataModel() {
		return listReportFormData;
	}
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}
	public void setListReportFormDataModel(List<ReportFormData> listReportFormData) {
		this.listReportFormData = listReportFormData;
	}
	
}
