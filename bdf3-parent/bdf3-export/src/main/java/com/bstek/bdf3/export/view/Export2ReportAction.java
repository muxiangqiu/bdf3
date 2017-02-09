package com.bstek.bdf3.export.view;

import com.bstek.bdf3.export.model.FileExtension;
import com.bstek.dorado.annotation.ClientEvent;
import com.bstek.dorado.annotation.ClientEvents;
import com.bstek.dorado.annotation.ClientObject;
import com.bstek.dorado.annotation.ClientProperty;
import com.bstek.dorado.annotation.IdeProperty;
import com.bstek.dorado.view.annotation.Widget;
import com.bstek.dorado.view.widget.action.AjaxAction;

@Widget(name = "Export2ReportAction", category = "BDF3", dependsPackage = "export2report", autoGenerateId = true)
@ClientObject(prototype = "dorado.widget.Export2ReportAction", shortTypeName = "Export2ReportAction")
@ClientEvents({ @ClientEvent(name = "onGetExportData"), @ClientEvent(name = "onGetExportElement") })
public class Export2ReportAction extends AjaxAction {

	private long timeout;
	private boolean batchable;

	private String template;

	private String interceptorName;

	private int rowSpace;

	private boolean autoDownload;

	private boolean showTitle;
	private String titleName;
	private String titleBgColor;
	private String titleFontColor;
	private int titleFontSize;

	private boolean showPageNumber;

	private String headerBgColor;
	private String headerFontColor;
	private int headerFontSize;

	private String dataBgColor;
	private String dataFontColor;
	private int dataFontSize;

	private DataScope dataScope = DataScope.currentPage;

	private int maxSize;

	private String fileName;

	private String extension;

	public Export2ReportAction() {
		this.setAsync(true);
		this.setBatchable(true);

		this.setAutoDownload(true);
		this.setShowTitle(false);

		this.setShowPageNumber(true);

		this.setRowSpace(1);

		this.setTitleBgColor("#FFFFFF");
		this.setTitleFontColor("#000000");
		this.setTitleFontSize(18);

		this.setHeaderBgColor("#D8D8D8");
		this.setHeaderFontColor("#000000");
		this.setHeaderFontSize(10);

		this.setDataBgColor("#FFFFFF");
		this.setDataFontColor("#000000");
		this.setDataFontSize(10);

		this.setMaxSize(1000);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	@ClientProperty(escapeValue = "true")
	public boolean isBatchable() {
		return batchable;
	}

	public void setBatchable(boolean batchable) {
		this.batchable = batchable;
	}

	@IdeProperty(highlight = 1)
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@ClientProperty(escapeValue = "1")
	public int getRowSpace() {
		return rowSpace;
	}

	public void setRowSpace(int rowSpace) {
		this.rowSpace = rowSpace;
	}

	public String getInterceptorName() {
		return interceptorName;
	}

	public void setInterceptorName(String interceptorName) {
		this.interceptorName = interceptorName;
	}

	@ClientProperty(escapeValue = "true")
	public boolean isAutoDownload() {
		return autoDownload;
	}

	public void setAutoDownload(boolean autoDownload) {
		this.autoDownload = autoDownload;
	}

	@ClientProperty(escapeValue = "false")
	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	@ClientProperty(escapeValue = "#FFFFFF")
	public String getTitleBgColor() {
		return titleBgColor;
	}

	public void setTitleBgColor(String titleBgColor) {
		this.titleBgColor = titleBgColor;
	}

	@ClientProperty(escapeValue = "#000000")
	public String getTitleFontColor() {
		return titleFontColor;
	}

	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}

	@ClientProperty(escapeValue = "18")
	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	@ClientProperty(escapeValue = "#D8D8D8")
	public String getHeaderBgColor() {
		return headerBgColor;
	}

	public void setHeaderBgColor(String headerBgColor) {
		this.headerBgColor = headerBgColor;
	}

	@ClientProperty(escapeValue = "#000000")
	public String getHeaderFontColor() {
		return headerFontColor;
	}

	@ClientProperty(escapeValue = "true")
	public boolean isShowPageNumber() {
		return showPageNumber;
	}

	public void setShowPageNumber(boolean showPageNumber) {
		this.showPageNumber = showPageNumber;
	}

	public void setHeaderFontColor(String headerFontColor) {
		this.headerFontColor = headerFontColor;
	}

	@ClientProperty(escapeValue = "10")
	public int getHeaderFontSize() {
		return headerFontSize;
	}

	public void setHeaderFontSize(int headerFontSize) {
		this.headerFontSize = headerFontSize;
	}

	@ClientProperty(escapeValue = "#FFFFFF")
	public String getDataBgColor() {
		return dataBgColor;
	}

	public void setDataBgColor(String dataBgColor) {
		this.dataBgColor = dataBgColor;
	}

	@ClientProperty(escapeValue = "#000000")
	public String getDataFontColor() {
		return dataFontColor;
	}

	public void setDataFontColor(String dataFontColor) {
		this.dataFontColor = dataFontColor;
	}

	@ClientProperty(escapeValue = "10")
	public int getDataFontSize() {
		return dataFontSize;
	}

	public void setDataFontSize(int dataFontSize) {
		this.dataFontSize = dataFontSize;
	}

	@ClientProperty(escapeValue = "currentPage")
	public DataScope getDataScope() {
		return dataScope;
	}

	public void setDataScope(DataScope dataScope) {
		this.dataScope = dataScope;
	}

	@ClientProperty(escapeValue = "1000")
	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@ClientProperty(escapeValue = FileExtension.xls)
	@IdeProperty(enumValues = FileExtension.xls + "," + FileExtension.xlsx + "," + FileExtension.pdf + "," + FileExtension.csv)
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	@IdeProperty(visible = false)
	public Object getParameter() {
		return super.getParameter();
	}

}