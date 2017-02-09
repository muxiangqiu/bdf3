
package com.bstek.bdf3.export.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfReportPageNumber extends PdfPageEventHelper{
	private BaseFont chineseFont;
	/** The template with the total number of pages. */
	PdfTemplate total;
	public PdfReportPageNumber(BaseFont font){
		this.chineseFont=font;
	}
	
	/**
	 * Creates the PdfTemplate that will hold the total number of pages.
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(22,16);
		total.setColorFill(new BaseColor(55,55,55));
	}
	
	/**
	 * Adds a header to every page
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		PdfPTable table = new PdfPTable(3);
		try {
			table.setWidths(new int[]{40,5,10});
			table.setTotalWidth(100);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			Font font=new Font(chineseFont,8);
			font.setColor(new BaseColor(55,55,55));
			Paragraph paragraph=new Paragraph("第   "+writer.getPageNumber()+" 页   共",font);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			table.addCell(paragraph);
			Image img=Image.getInstance(total);
			img.scaleAbsolute(28, 28);
			PdfPCell cell = new PdfPCell(img);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);
			PdfPCell c = new PdfPCell(new Paragraph("页",font));
			c.setHorizontalAlignment(Element.ALIGN_LEFT);
			c.setBorder(Rectangle.NO_BORDER);
			table.addCell(c);
			float center=(document.getPageSize().getWidth())/2-120/2;
			table.writeSelectedRows(0, -1,center,30, writer.getDirectContent());
		}
		catch(DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}
	
	/**
	 * Fills out the total number of pages before the document is closed.
	 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
	 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onCloseDocument(PdfWriter writer, Document document) {
		ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
				new Phrase(String.valueOf(writer.getPageNumber() - 1)),2,2, 0);
	}
}