package com.bstek.bdf3.export.excel.style;

import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.bstek.bdf3.export.model.ReportForm;
import com.bstek.bdf3.export.model.ReportFormData;

public class FormStyleBuilder extends AbstractStyleBuilder {

	public CellStyle builderLabelCellStyle(ReportForm reportFormModel, Workbook workbook) {
		List<ReportFormData> list = reportFormModel.getListReportFormDataModel();
		ReportFormData reportFormDataModel;
		if (list.size() > 0) {
			reportFormDataModel = list.get(0);
			int labelAlign = reportFormDataModel.getLabelAlign();
			CellStyle labelStyle = createBorderCellStyle(workbook, reportFormModel.isShowBorder());
			setCellStyleAligment(labelStyle, labelAlign);
			labelStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			return labelStyle;
		}
		return null;

	}

	public CellStyle builderValueCellStyle(ReportForm reportFormModel, Workbook workbook) {
		List<ReportFormData> list = reportFormModel.getListReportFormDataModel();
		ReportFormData reportFormDataModel;
		if (list.size() > 0) {
			reportFormDataModel = list.get(0);
			int dataAlign = reportFormDataModel.getDataAlign();
			int dataStyle = reportFormDataModel.getDataStyle();
			CellStyle valueStyle = createBorderCellStyle(workbook, reportFormModel.isShowBorder());
			setCellStyleAligment(valueStyle, dataAlign);
			valueStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			this.setCellStyleFont(workbook, valueStyle, dataStyle);
			return valueStyle;
		}
		return null;

	}
}
