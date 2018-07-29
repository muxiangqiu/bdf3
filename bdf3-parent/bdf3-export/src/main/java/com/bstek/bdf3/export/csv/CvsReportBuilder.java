package com.bstek.bdf3.export.csv;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.export.extension.ReportBuilder;
import com.bstek.bdf3.export.model.FileExtension;
import com.bstek.bdf3.export.model.ReportGrid;
import com.bstek.bdf3.export.model.ReportGridHeader;
import com.bstek.dorado.core.Configure;

/**
 * 导出CSV格式文件默认实现
 * 
 */
@Component(CvsReportBuilder.BEAN_ID)
public class CvsReportBuilder implements ReportBuilder {

	public static final String BEAN_ID = "bdf3.CvsReportBuilder";

	/**
	 * 分隔符
	 */
	public String delimiter = Configure.getString("bdf3.export.csv.delimiter");
	/**
	 * 单元格包裹符号
	 */
	public String cellWrapSymbol = Configure.getString("bdf3.export.csv.cellWrapSymbol");
	/**
	 * 字符集
	 */
	public String charset = Configure.getString("bdf3.export.csv.charset");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bstek.bdf2.export.extension.ReportBuilder#execute(java.io.OutputStream
	 * , com.bstek.bdf2.export.excel.model.ReportGridModel)
	 */
	public void execute(OutputStream out, ReportGrid reportGrid) throws Exception {
		this.fillData(out, reportGrid);
	}
	
	private String getLineSeparator(){
		return System.getProperty("line.separator");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bstek.bdf2.export.extension.ReportBuilder#support(java.lang.String)
	 */
	public boolean support(String extension) {
		return extension.equals(FileExtension.csv) ? true : false;
	}

	public void fillData(OutputStream out, ReportGrid gridModel) throws Exception {
		List<ReportGridHeader> headers = gridModel.getColumnHeaders();
		List<Map<String, Object>> datas = gridModel.getGridDataModel().getDatas();
		StringBuffer rowStr = null;
		//output header
		if (gridModel.isShowHeader()){
			rowStr = new StringBuffer();
			int cellIndex = -1;
			for (ReportGridHeader header : headers) {
				cellIndex++;
				String value = header.getLabel();
				if (StringUtils.isEmpty(value)){
					value = header.getColumnName();
				}
				if (value.indexOf(delimiter)>-1){
					value = StringUtils.replace(value, delimiter, " ");
				}
				rowStr.append(cellWrapSymbol).append(value).append(cellWrapSymbol);
				if (cellIndex == headers.size() - 1) {
					rowStr.append(getLineSeparator());
				} else {
					rowStr.append(delimiter);
				}
			}
			out.write(rowStr.toString().getBytes(charset));
		}
		//output data
		for (Map<String, Object> currentRowData : datas) {
			rowStr = new StringBuffer();
			int cellIndex = -1;
			for (ReportGridHeader header : headers) {
				cellIndex++;
				Object value = currentRowData.get(header.getColumnName());
				rowStr.append(cellWrapSymbol).append(value == null ? "" : value.toString()).append(cellWrapSymbol);
				if (cellIndex == headers.size() - 1) {
					rowStr.append(getLineSeparator());
				} else {
					rowStr.append(delimiter);
				}
			}
			out.write(rowStr.toString().getBytes(charset));
		}
	}

}
