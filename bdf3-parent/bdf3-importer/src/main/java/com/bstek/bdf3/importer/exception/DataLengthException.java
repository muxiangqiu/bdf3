package com.bstek.bdf3.importer.exception;


/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月24日
 */
public class DataLengthException extends DataException {

	private static final long serialVersionUID = 1L;
	private int length;
	
	public DataLengthException(int row, int col, String value, int length) {
		super(row + "行" + col + "列" + "的值“" + value + "”要求最大字符不能超过" + length + "个！");
		this.setLength(length);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
