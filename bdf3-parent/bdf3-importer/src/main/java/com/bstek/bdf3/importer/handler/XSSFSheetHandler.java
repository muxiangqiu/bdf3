package com.bstek.bdf3.importer.handler;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bstek.bdf3.importer.model.Cell;
import com.bstek.bdf3.importer.policy.XSSFContext;

public class XSSFSheetHandler extends DefaultHandler {
	private XSSFContext context;
       
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {

        if ("inlineStr".equals(name) || "v".equals(name)) {
        	context.setvIsOpen(true);
            context.clearContents();
        } else if ("c".equals(name)) {
            String r = attributes.getValue("r");
            int firstDigit = -1;
            for (int c = 0; c < r.length(); ++c) {
                if (Character.isDigit(r.charAt(c))) {
                    firstDigit = c;
                    break;
                }
            }
            int col = nameToColumn(r.substring(0, firstDigit));
            int row = Integer.parseInt(r.substring(firstDigit));
            context.setCurrentCell(new Cell(row, col));
            context.setNextDataType(XSSFDataType.NUMBER);
            context.setFormatIndex((short) -1);
            context.setFormatString(null);

            String cellType = attributes.getValue("t");
            String cellStyleStr = attributes.getValue("s");
            if ("b".equals(cellType))
                context.setNextDataType(XSSFDataType.BOOL);
            else if ("e".equals(cellType))
                context.setNextDataType(XSSFDataType.ERROR);
            else if ("inlineStr".equals(cellType))
            	context.setNextDataType(XSSFDataType.INLINESTR);
            else if ("s".equals(cellType))
            	context.setNextDataType(XSSFDataType.SSTINDEX);
            else if ("str".equals(cellType))
            	context.setNextDataType(XSSFDataType.FORMULA);
            else if (cellStyleStr != null) {
                int styleIndex = Integer.parseInt(cellStyleStr);
                XSSFCellStyle style = context.getStyles().getStyleAt(styleIndex);
                context.setFormatIndex(style.getDataFormat());
                String formatString = style.getDataFormatString();
                if (formatString == null) {
                	formatString = BuiltinFormats.getBuiltinFormat(context.getFormatIndex());
                }
                context.setFormatString(formatString);
            }
        }

    }

    public void endElement(String uri, String localName, String name)
            throws SAXException {

        String thisStr = null;
        StringBuffer contents = context.getContents();

        if ("v".equals(name)) {
            switch (context.getNextDataType()) {

            case BOOL:
                char first = contents.charAt(0);
                thisStr = first == '0' ? "FALSE" : "TRUE";
                break;

            case ERROR:
                thisStr = "\"ERROR:" + contents.toString() + '"';
                break;

            case FORMULA:
                thisStr = contents.toString();
                break;

            case INLINESTR:
                XSSFRichTextString rtsi = new XSSFRichTextString(contents
                        .toString());
                thisStr = rtsi.toString();
                break;

            case SSTINDEX:
                String sstIndex = contents.toString();
                try {
                    int idx = Integer.parseInt(sstIndex);
                    XSSFRichTextString rtss = new XSSFRichTextString(
                            context.getStrings().getEntryAt(idx));
                    thisStr = rtss.toString() ;
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("Failed to parse SST index '" + sstIndex
                            + "': " + ex.toString());
                }
                break;

            case NUMBER:
                String n = contents.toString();
                thisStr = n;
                if (context.getFormatString() != null)
                    thisStr = context.getFormatter().formatRawCellContents(Double
                            .parseDouble(n), context.getFormatIndex(),
                            context.getFormatString());
                else
                    thisStr = n;
                break;

            default:
            	throw new RuntimeException("Unexpected type: " + context.getNextDataType() + "");
            }
            
            context.getCurrentCell().setValue(thisStr);
            

        }

    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if (context.isvIsOpen())
            context.getContents().append(ch, start, length);
    }

    private int nameToColumn(String name) {
        int column = -1;
        for (int i = 0; i < name.length(); ++i) {
            int c = name.charAt(i);
            column = (column + 1) * 26 + c - 'A';
        }
        return column;
    }
    
    public void setContext(XSSFContext context) {
    	this.context = context;
    }

}