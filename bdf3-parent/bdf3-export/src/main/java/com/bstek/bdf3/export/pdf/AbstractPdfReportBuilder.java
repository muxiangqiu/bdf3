package com.bstek.bdf3.export.pdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bstek.bdf3.export.model.ReportFormData;
import com.bstek.bdf3.export.model.ReportTitle;
import com.bstek.bdf3.export.pdf.model.ColumnHeader;
import com.bstek.bdf3.export.pdf.model.LabelData;
import com.bstek.bdf3.export.pdf.model.ReportData;
import com.bstek.bdf3.export.pdf.model.ReportDataModel;
import com.bstek.bdf3.export.pdf.model.TextChunk;
import com.bstek.bdf3.export.utils.ExportUtils;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public abstract class AbstractPdfReportBuilder {
	protected BaseFont chineseFont;
	protected AbstractPdfReportBuilder() throws Exception {
		chineseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	}
	protected Chunk createChunk(TextChunk textChunk) {
		Chunk chunk = new Chunk(textChunk.getText());
		chunk.setFont(createFont(textChunk));
		return chunk;
	}
	protected Font createFont(TextChunk textChunk) {
		Font font = new Font(chineseFont);
		if (textChunk.getFontColor() != null) {
			int[] colors = textChunk.getFontColor();
			font.setColor(new BaseColor(colors[0], colors[1], colors[2]));
		}
		if (textChunk.getFontSize() > 0) {
			font.setSize(textChunk.getFontSize());
		}
		font.setStyle(textChunk.getFontStyle());
		return font;
	}
	protected Paragraph createParagraph(TextChunk textChunk) {
		Paragraph paragraph = new Paragraph(textChunk.getText(), createFont(textChunk));
		paragraph.setAlignment(textChunk.getAlign());
		return paragraph;
	}

	protected Paragraph createReportTitle(ReportTitle reportTitle) {
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_CENTER);
		if (reportTitle != null && reportTitle.isShowTitle()) {
			TextChunk titleChunk = new TextChunk();
			titleChunk.setText(reportTitle.getTitle());
			titleChunk.setFontSize(reportTitle.getStyle().getFontSize());
			titleChunk.setFontColor(reportTitle.getStyle().getFontColor());
			paragraph.add(createChunk(titleChunk));
			paragraph.add(Chunk.NEWLINE);
			paragraph.add(Chunk.NEWLINE);
		}
		return paragraph;
	}

	protected PdfPTable createFormTable(Collection<ReportFormData> datas, int columnCount, boolean showBorder) throws Exception {
		PdfPTable table = new PdfPTable(columnCount * 2);
		table.setWidthPercentage(100);
		Map<Integer, List<ReportFormData>> group = new HashMap<Integer, List<ReportFormData>>();
		List<ReportFormData> currentReportFormDataModels = new ArrayList<ReportFormData>();
		int rowIndx = 0;
		int colCount = columnCount * 2;
		int currentRowColumnSize = 0;
		int i = 1;
		for (ReportFormData reportFormDataModel : datas) {
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
			if (i == datas.size()) {
				group.put(rowIndx, currentReportFormDataModels);
			}
			i++;
		}
		int colSize = 0;
		SimpleDateFormat sdf = ExportUtils.getSimpleDateFormat();
		for (Map.Entry<Integer, List<ReportFormData>> entry : group.entrySet()) {
			List<ReportFormData> list = entry.getValue();
			int j = 1;
			colSize = 0;
			for (ReportFormData data : list) {
				Object obj = data.getData();
				String result = "";
				if (obj instanceof Date) {
					result = sdf.format((Date) obj);
					if (result.endsWith("00:00:00")) {
						result = result.substring(0, 11);
					}
				} else if (obj != null) {
					result = obj.toString();
				}
				TextChunk text = new TextChunk();
				text.setText(result);
				text.setAlign(data.getDataAlign());
				text.setFontStyle(data.getDataStyle());

				LabelData labelData = new LabelData();
				labelData.setAlign(data.getLabelAlign());
				labelData.setText(data.getLabel());

				PdfPCell labelCell = new PdfPCell();
				labelCell.addElement(this.createParagraph(labelData));
				labelCell.setHorizontalAlignment(labelData.getAlign());
				labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				if (!showBorder) {
					labelCell.setBorderWidth(0);
				}
				table.addCell(labelCell);
				colSize = colSize + 1;
				int colSpan = data.getColSpan() * 2 - 1;
				PdfPCell valueCell = new PdfPCell();
				if (j == list.size()) {
					valueCell.setColspan(colCount - colSize);
				} else {
					colSize = colSize + colSpan;
					valueCell.setColspan(colSpan);
				}
				valueCell.setHorizontalAlignment(data.getDataAlign());
				valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				valueCell.addElement(this.createParagraph(text));
				if (!showBorder) {
					valueCell.setBorderWidth(0);
				}
				table.addCell(valueCell);
				j++;
			}
		}
		return table;
	}

	protected PdfPTable createGridTable(ReportDataModel dataModel, boolean isRepeatHeader) throws Exception {
		PdfPTable table = new PdfPTable(calculateGridColumnCount(dataModel.getTopColumnHeaders()));
		table.setWidthPercentage(100);
		Collection<ColumnHeader> topHeaders = dataModel.getTopColumnHeaders();
		List<Integer> widths = new ArrayList<Integer>();
		generateGridColumnWidths(topHeaders, widths);
		int[] values = new int[widths.size()];
		for (int i = 0; i < widths.size(); i++) {
			values[i] = widths.get(i);
		}
		table.setWidths(values);
		int maxHeaderLevel = getGridMaxColumngroup(topHeaders);
		createGridColumnHeader(table, topHeaders, maxHeaderLevel);
		createGridTableDatas(table, dataModel.getReportData());
		if (isRepeatHeader) {
			table.setHeaderRows(maxHeaderLevel);
		}
		return table;
	}

	private void createGridColumnHeader(PdfPTable table, Collection<ColumnHeader> topHeaders, int maxHeaderLevel) throws Exception {
		for (int i = 1; i < 50; i++) {
			List<ColumnHeader> result = new ArrayList<ColumnHeader>();
			generateGridHeadersByLevel(topHeaders, i, result);
			for (ColumnHeader header : result) {
				PdfPCell cell = new PdfPCell(createParagraph(header));
				if (header.getBgColor() != null) {
					int[] colors = header.getBgColor();
					cell.setBackgroundColor(new BaseColor(colors[0], colors[1], colors[2]));
				}
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(header.getAlign());
				cell.setColspan(header.getColspan());
				if (header.getColumnHeaders().size() == 0) {
					int rowspan = maxHeaderLevel - (header.getLevel() - 1);
					if (rowspan > 0) {
						cell.setRowspan(rowspan);
					}
				}
				table.addCell(cell);
			}
		}
	}

	private void generateGridHeadersByLevel(Collection<ColumnHeader> topHeaders, int level, List<ColumnHeader> result) {
		for (ColumnHeader header : topHeaders) {
			if (header.getLevel() == level) {
				result.add(header);
			}
			generateGridHeadersByLevel(header.getColumnHeaders(), level, result);
		}
	}

	private void generateGridColumnWidths(Collection<ColumnHeader> topHeaders, List<Integer> widths) {
		for (ColumnHeader header : topHeaders) {
			Collection<ColumnHeader> children = header.getColumnHeaders();
			if (children.size() == 0) {
				widths.add(header.getWidth());
			}
			generateGridColumnWidths(children, widths);
		}
	}

	private void createGridTableDatas(PdfPTable table, Collection<ReportData> datas) {
		for (ReportData data : datas) {
			PdfPCell cell = new PdfPCell(createParagraph(data.getTextChunk()));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			int level = this.calculateIndentationCount(data.getTextChunk().getText());
			if (data.getBgColor() != null) {
				int[] colors = data.getBgColor();
				cell.setBackgroundColor(new BaseColor(colors[0], colors[1], colors[2]));
			}
			if (level == 0) {
				cell.setHorizontalAlignment(data.getAlign());
			} else {
				cell.setIndent(20 * level);
			}
			table.addCell(cell);
		}
	}

	private int getGridMaxColumngroup(Collection<ColumnHeader> topHeaders) {
		int max = 1;
		for (int i = 1; i < 50; i++) {
			List<ColumnHeader> result = new ArrayList<ColumnHeader>();
			generateGridHeadersByLevel(topHeaders, i, result);
			if (result.size() == 0) {
				max = i - 1;
				break;
			}
		}
		return max;
	}

	private int calculateGridColumnCount(Collection<ColumnHeader> topHeaders) {
		int i = 0;
		for (ColumnHeader header : topHeaders) {
			i++;
			i += (header.getColspan() - 1);
		}
		return i;
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
