package com.bstek.bdf3.importer.exception;
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月24日
 */
public class DataFormatException extends DataException {

	private static final long serialVersionUID = 1L;
	
	public DataFormatException(int row, int col, String value) {
		
		super(row + "行" + col + "列" + "的值“" + value + "”格式错误！");
	}

}
