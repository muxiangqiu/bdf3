package com.bstek.bdf3.export.extension;

import org.springframework.stereotype.Component;

import com.bstek.bdf3.export.excel.ExcelReportModelGenerater;

@Component(ReportGenerater.BEAN_ID)
public class ReportGenerater extends ExcelReportModelGenerater {

	public static final String BEAN_ID = "bdf3.ReportGenerater";

}
