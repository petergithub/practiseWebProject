package org.peter.poi;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * excel相关工具类
 * 
 * @author yangchen
 *
 */
public class ExcelUtil {

	private static final transient Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	private static XSSFColor color;
	static {
		Color c = new Color(170, 198, 244);
		color = new XSSFColor(c);
	}

	private static byte[] toByteArray(XSSFWorkbook wb) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		return out.toByteArray();
	}

	private static XSSFSheet createSheetWidthHeaders(XSSFWorkbook wb, ExcelTemplate template) {
		XSSFSheet sheet = wb.createSheet(template.getExcelName());
		XSSFRow header = sheet.createRow(0);
		int index = 0;

		XSSFCellStyle headerCellStyle = wb.createCellStyle();
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyle.setFillForegroundColor(color);
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		for (ExcelColumn column : template.getExcelColumns()) {
			// 设置列宽
			sheet.setColumnWidth(index, column.getColumnWidth() * 256);
			// 填写表头
			XSSFCell cell = header.createCell(index, Cell.CELL_TYPE_STRING);
			cell.setCellValue(column.getColumnName());
			cell.setCellStyle(headerCellStyle);
			index++;
		}
		return sheet;
	}

	private static void fillCell(XSSFCell cell, int columnType, XSSFCellStyle rowCellStyle,
			Method method, Object record) throws Exception {
		Object value = method.invoke(record);
		setValue(value, columnType, cell);
		cell.setCellStyle(rowCellStyle);
	}

	private static void setValue(Object value, int columnType, XSSFCell cell) {
		switch (columnType) {
		case Cell.CELL_TYPE_BOOLEAN:
			cell.setCellValue((boolean) value);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellValue(Double.valueOf(String.valueOf(value)));
			break;
		case Cell.CELL_TYPE_STRING:
			cell.setCellValue(String.valueOf(value));
			break;
		case Cell.CELL_TYPE_ERROR:
		case Cell.CELL_TYPE_BLANK:
		default:
			break;
		}

	}

	public static <T> byte[] convertDataToExcelFileBytes(ExcelTemplate template, List<T> records)
			throws Exception {
		logger.info("convertDataToExcelFileBytes template:{}", template.getExcelName());
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFCellStyle rowCellStyle = wb.createCellStyle();
		rowCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		rowCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		rowCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		rowCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		XSSFSheet sheet = ExcelUtil.createSheetWidthHeaders(wb, template);
		List<Method> readers = template.getExcelcolumnReaders();
		List<ExcelColumn> columns = template.getExcelColumns();
		for (int i = 1; i < records.size() + 1; i++) {
			XSSFRow row = sheet.createRow(i);
			Object record = records.get(i - 1);
			for (int j = 0; j < readers.size(); j++) {
				int columnType = columns.get(j).getColumnType();
				XSSFCell cell = row.createCell(j, columnType);
				fillCell(cell, columnType, rowCellStyle, readers.get(j), record);
			}
		}
		return toByteArray(wb);
	}

	public static String toGetter(String fieldName) {
		if (fieldName == null || fieldName.length() == 0) {
			return null;
		}

		/*
		 * If the second char is upper, make 'get' + field name as getter name.
		 * For example, eBlog -> geteBlog
		 */
		if (fieldName.length() > 2) {
			String second = fieldName.substring(1, 2);
			if (second.equals(second.toUpperCase())) {
				return new StringBuffer("get").append(fieldName).toString();
			}
		}

		/* Common situation */
		fieldName = new StringBuffer("get").append(fieldName.substring(0, 1).toUpperCase())
				.append(fieldName.substring(1)).toString();

		return fieldName;
	}
}
