package com.bstek.bdf3.importer.exception;
/**
 * @author Kevin Yang (mailto:kevin.yang@bstek.com)
 * @since 2016年2月24日
 */
public class DataNullableException extends DataException {

	public DataNullableException(int row, int col) {
		super(row + "行" + col + "列" + "的值不能为空");

	}

	private static final long serialVersionUID = 1L;

}
