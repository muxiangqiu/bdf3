package com.bstek.bdf3.importer.policy;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;

import com.bstek.bdf3.importer.handler.XSSFDataType;


/**
 *@author Kevin.yang
 *@since 2015年8月22日
 */
public class XSSFContext extends Context {
	
	private ReadOnlySharedStringsTable strings;
	
    private StylesTable styles;
    
    private boolean vIsOpen;

    private XSSFDataType nextDataType = XSSFDataType.NUMBER;
    
    private short formatIndex;
    
    private String formatString;
    
    private final DataFormatter formatter = new DataFormatter();

    private int thisColumn = -1;
    
    private int lastColumnNumber = -1;

    private StringBuffer contents = new StringBuffer();

	public ReadOnlySharedStringsTable getStrings() {
		return strings;
	}

	public void setStrings(ReadOnlySharedStringsTable strings) {
		this.strings = strings;
	}

	public StylesTable getStyles() {
		return styles;
	}

	public void setStyles(StylesTable styles) {
		this.styles = styles;
	}

	public boolean isvIsOpen() {
		return vIsOpen;
	}

	public void setvIsOpen(boolean vIsOpen) {
		this.vIsOpen = vIsOpen;
	}

	public XSSFDataType getNextDataType() {
		return nextDataType;
	}

	public void setNextDataType(XSSFDataType nextDataType) {
		this.nextDataType = nextDataType;
	}

	public short getFormatIndex() {
		return formatIndex;
	}

	public void setFormatIndex(short formatIndex) {
		this.formatIndex = formatIndex;
	}

	public String getFormatString() {
		return formatString;
	}

	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	public int getThisColumn() {
		return thisColumn;
	}

	public void setThisColumn(int thisColumn) {
		this.thisColumn = thisColumn;
	}

	public int getLastColumnNumber() {
		return lastColumnNumber;
	}

	public void setLastColumnNumber(int lastColumnNumber) {
		this.lastColumnNumber = lastColumnNumber;
	}

	public StringBuffer getContents() {
		return contents;
	}

	public void setContents(StringBuffer contents) {
		this.contents = contents;
	}

	public DataFormatter getFormatter() {
		return formatter;
	}
	
	public void clearContents() {
		contents.setLength(0);
	}

	
    
	
}
