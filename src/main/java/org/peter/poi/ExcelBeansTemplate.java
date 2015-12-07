package org.peter.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.peter.bean.Bean;

public class ExcelBeansTemplate extends ExcelTemplate {

	public ExcelBeansTemplate() throws NoSuchMethodException, SecurityException {
		super();
	}

	public static final String sheetName = "Beans";

	public static final String[] fields = { "id", "name", "value", "creationDate" };

	public String getExcelName() {
		return sheetName;
	}

	protected void fillColumnReaders() throws NoSuchMethodException, SecurityException {
		Class<Bean> clazz = Bean.class;
		for (String field : fields) {
			columnReaders.add(clazz.getMethod(ExcelUtil.toGetter(field)));
		}
	}

	protected void fillHeaderColumns() {
		ExcelColumn merchantId = new ExcelColumn("id", 10, Cell.CELL_TYPE_STRING);
		columns.add(merchantId);
		ExcelColumn issuer = new ExcelColumn("name", 10, Cell.CELL_TYPE_STRING);
		columns.add(issuer);
		ExcelColumn cardNumber = new ExcelColumn("value", 10, Cell.CELL_TYPE_STRING);
		columns.add(cardNumber);
		ExcelColumn userName = new ExcelColumn("creationDate", 10, Cell.CELL_TYPE_STRING);
		columns.add(userName);
	}

}
