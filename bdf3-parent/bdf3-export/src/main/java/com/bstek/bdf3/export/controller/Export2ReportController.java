package com.bstek.bdf3.export.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bstek.bdf3.export.csv.CvsReportBuilder;
import com.bstek.bdf3.export.excel.ExcelReportBuilder;
import com.bstek.bdf3.export.excel.ExcelReportModelGenerater;
import com.bstek.bdf3.export.extension.ReportBuilder;
import com.bstek.bdf3.export.extension.ReportGenerater;
import com.bstek.bdf3.export.model.FileExtension;
import com.bstek.bdf3.export.model.ReportForm;
import com.bstek.bdf3.export.model.ReportGrid;
import com.bstek.bdf3.export.model.ReportGridHeader;
import com.bstek.bdf3.export.model.ReportTitle;
import com.bstek.bdf3.export.pdf.PdfReportBuilder;
import com.bstek.bdf3.export.pdf.PdfReportModelGenerater;
import com.bstek.bdf3.export.utils.ExportUtils;
import com.bstek.bdf3.export.view.SupportWidget;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.core.Configure;
import com.itextpdf.text.Document;

@Component(Export2ReportController.BEAN_ID)
public class Export2ReportController implements InitializingBean, ApplicationContextAware {

	public static final String BEAN_ID = "bdf3.Export2ReportController";

	@Autowired
	@Qualifier(ExcelReportModelGenerater.BEAN_ID)
	public ExcelReportModelGenerater excelReportModelGenerater;

	@Autowired
	@Qualifier(ExcelReportBuilder.BEAN_ID)
	public ExcelReportBuilder excelReportBuilder;

	@Autowired
	@Qualifier(PdfReportModelGenerater.BEAN_ID)
	public PdfReportModelGenerater pdfReportModelGenerater;

	@Autowired
	@Qualifier(PdfReportBuilder.BEAN_ID)
	public PdfReportBuilder pdfReportBuilder;

	@Autowired
	@Qualifier(ReportGenerater.BEAN_ID)
	public ReportGenerater commonReportGenerater;

	@Autowired
	@Qualifier(CvsReportBuilder.BEAN_ID)
	public CvsReportBuilder cvsReportBuilder;

	public int rowAccessWindowSize = 500;

	@SuppressWarnings("unchecked")
	@Expose
	public Map<String, String> generateReportFile(Map<String, Object> parameter) throws Exception {
		String fileName = (String) parameter.get("fileName");
		String extension = (String) parameter.get("extension");
		int rowSpace = (Integer) parameter.get("rowSpace");
		String interceptorName = null;
		if (parameter.get("interceptorName") != null) {
			interceptorName = (String) parameter.get("interceptorName");
		}
		String id = UUID.randomUUID().toString();
		String location = ExportUtils.getFileStorePath() + id + "_" + fileName + "." + extension;
		Map<String, Object> titleInfos = (Map<String, Object>) parameter.get("titleInfos");
		List<Map<String, Object>> reportInfos = (List<Map<String, Object>>) parameter.get("reportInfos");
		ReportTitle reportTitle = excelReportModelGenerater.generateReportTitleModel(titleInfos);
		if (FileExtension.xls.equals(extension) || FileExtension.xlsx.equals(extension)) {
			this.generateExcelFile(reportTitle, reportInfos, rowSpace, fileName, location, interceptorName);
		} else if (FileExtension.pdf.equals(extension)) {
			this.generatePdfFile(reportTitle, reportInfos, rowSpace, location, interceptorName);
		} else {
			this.generateOtherFile(extension, reportTitle, reportInfos, rowSpace, location, interceptorName);
		}
		Map<String, String> outParameter = new HashMap<String, String>();
		outParameter.put("id", id);
		outParameter.put("name", fileName + "." + extension);
		return outParameter;
	}

	@SuppressWarnings("unchecked")
	private void generatePdfFile(ReportTitle reportTitle, List<Map<String, Object>> reportInfos, int rowSpace, String fileName, String interceptorName) throws Exception {
		FileOutputStream out = new FileOutputStream(fileName);
		Document doc = pdfReportBuilder.createDocument(reportTitle, out);
		try {
			for (Map<String, Object> map : reportInfos) {
				String type = (String) map.get("type");
				if (SupportWidget.grid.name().equals(type)) {
					Map<String, Object> grid = (Map<String, Object>) map.get("grid");
					pdfReportBuilder.addGridToDocument(doc, reportTitle, pdfReportModelGenerater.generateReportGridModel(grid, interceptorName));
					pdfReportBuilder.addNewline(doc, rowSpace);
				} else if (SupportWidget.form.name().equals(type)) {
					Map<String, Object> form = (Map<String, Object>) map.get("form");
					ReportForm reportFormModel = pdfReportModelGenerater.generateReportFormModel(form, interceptorName);
					pdfReportBuilder.addFormToDocument(doc, reportTitle, reportFormModel);
					pdfReportBuilder.addNewline(doc, rowSpace);
				}
			}
		} finally {
			doc.close();
			out.close();
		}
	}

	@SuppressWarnings("unchecked")
	private void generateExcelFile(ReportTitle reportTitle, List<Map<String, Object>> reportInfos, int rowSpace, String fileName, String location, String interceptorName) throws Exception {
		Workbook workbook = null;
		if (location.endsWith(FileExtension.xls)) {
			workbook = excelReportBuilder.createWorkBook2003();
		} else if (location.endsWith(FileExtension.xlsx)) {
			workbook = excelReportBuilder.createWorkBook2007(rowAccessWindowSize);
		}
		Assert.notNull(workbook, "the workbook must not be null");
		Sheet sheet = excelReportBuilder.createSheet(workbook, fileName);
		int index = 0;
		int nextRow = 0;
		for (Map<String, Object> map : reportInfos) {
			String type = (String) map.get("type");
			if (SupportWidget.grid.name().equals(type)) {
				Map<String, Object> grid = (Map<String, Object>) map.get("grid");
				ReportGrid reportGridModel = excelReportModelGenerater.generateReportGridModel(grid, interceptorName);
				if (index == 0) {
					List<ReportGridHeader> bottomColumnHeaderModelList = new ArrayList<ReportGridHeader>();
					excelReportBuilder.calculateBottomColumnHeader(reportGridModel.getGridHeaderModelList(), bottomColumnHeaderModelList);
					int bottomColumnHeaderCount = bottomColumnHeaderModelList.size();
					reportGridModel.setColumnCount(bottomColumnHeaderCount);
					nextRow = excelReportBuilder.addTitleToSheet(reportTitle, sheet, bottomColumnHeaderCount - 1);
				}
				nextRow = excelReportBuilder.addGridToSheet(reportGridModel, sheet, nextRow);
				nextRow = nextRow + rowSpace;

			} else if (SupportWidget.form.name().equals(type)) {
				Map<String, Object> form = (Map<String, Object>) map.get("form");
				ReportForm reportFormModel = excelReportModelGenerater.generateReportFormModel(form, interceptorName);
				if (index == 0) {
					int lastColumn = reportFormModel.getColumnCount() * 2 - 1;
					nextRow = excelReportBuilder.addTitleToSheet(reportTitle, sheet, lastColumn);
				}
				nextRow = excelReportBuilder.addFormToSheet(reportFormModel, sheet, nextRow);
				nextRow = nextRow + rowSpace;
			}
			index++;
		}
		excelReportBuilder.writeFile(workbook, location);
	}

	@SuppressWarnings("unchecked")
	private void generateOtherFile(String extension, ReportTitle reportTitle, List<Map<String, Object>> reportInfos, int rowSpace, String fileName, String interceptorName) throws Exception {
		FileOutputStream out = new FileOutputStream(fileName);
		try {
			for (Map<String, Object> map : reportInfos) {
				String type = (String) map.get("type");
				ReportBuilder builder = this.getReportBuilder(extension);
				Assert.notNull(builder, "ReportBuilder is null.");
				if (SupportWidget.grid.name().equals(type)) {
					Map<String, Object> grid = (Map<String, Object>) map.get("grid");
					ReportGrid report = commonReportGenerater.generateReportGridModel(grid, interceptorName);
					builder.execute(out, report);
				}
			}
		} finally {
			out.close();
		}
	}

	public void afterPropertiesSet() throws Exception {
		String size = Configure.getString("bdf3.export.cacheSize");
		if (StringUtils.isNotEmpty(size)) {
			rowAccessWindowSize = Integer.parseInt(size);
		}
		String fileLocation = ExportUtils.getFileStorePath();
		File f = new File(fileLocation);
		if (!f.exists()) {
			f.mkdirs();
		}
		builders = applicationContext.getBeansOfType(ReportBuilder.class).values();
	}

	private Collection<ReportBuilder> builders;

	private ApplicationContext applicationContext;

	public Collection<ReportBuilder> getBuilders() {
		return builders;
	}

	public ReportBuilder getReportBuilder(String extension) {
		for (ReportBuilder builder : getBuilders()) {
			if (builder.support(extension)) {
				return builder;
			}
		}
		return null;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
