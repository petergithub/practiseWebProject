package org.peter.poi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class ExcelTemplate {

	public List<ExcelColumn> columns = new ArrayList<ExcelColumn>();
	public List<Method> columnReaders = new ArrayList<Method>();

	public ExcelTemplate() throws NoSuchMethodException, SecurityException {
		init();
	}

	public abstract String getExcelName();

	public List<ExcelColumn> getExcelColumns() {
		return columns;
	}

	public List<Method> getExcelcolumnReaders() {
		return columnReaders;
	}

	protected void init() throws NoSuchMethodException, SecurityException {
		if (columns == null || columns.size() == 0) {
			synchronized (columns) {
				if (columns == null) {
					columns = new ArrayList<ExcelColumn>();
				}
				if (columns.size() == 0) {
					fillHeaderColumns();

				}
			}
		}
		if (columnReaders == null || columnReaders.size() == 0) {
			synchronized (columnReaders) {
				if (columnReaders == null) {
					columnReaders = new ArrayList<Method>();
				}
				if (columnReaders.size() == 0) {
					fillColumnReaders();
				}
			}
		}
	}

	protected abstract void fillColumnReaders() throws NoSuchMethodException, SecurityException;

	protected abstract void fillHeaderColumns();
}
