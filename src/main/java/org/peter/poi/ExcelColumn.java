package org.peter.poi;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Excel模版的列定义
 * @author yangchen
 *
 */
public class ExcelColumn {
   /**
    * CellType常量见下面的类
    * @see org.apache.poi.ss.usermodel.Cell 
    **/
	private int columnType = Cell.CELL_TYPE_STRING;//默认为String类型
	private String columnName;//列名
	private int columnWidth;//在生成的表格中，本列的宽度
	
	public ExcelColumn(String columnName, int columnWidth){
		this.columnName = columnName;
		this.columnWidth = columnWidth;
	}
	public ExcelColumn(String columnName, int columnWidth, int columnType){
		this.columnName = columnName;
		this.columnWidth = columnWidth;
		this.columnType = columnType;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}
	@Override
	public String toString() {
		return "ExcelColumn [columnType=" + columnType + ", columnName=" + columnName + ", columnWidth=" + columnWidth
				+ "]";
	}
}
