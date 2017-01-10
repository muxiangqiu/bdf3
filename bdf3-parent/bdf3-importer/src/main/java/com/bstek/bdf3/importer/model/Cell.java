package com.bstek.bdf3.importer.model;
/**
 *@author Kevin.yang
 *@since 2015年8月23日
 */
public class Cell {

	private int row;
	private int col;
	private String value;
	
	public Cell() {
		
	}
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
