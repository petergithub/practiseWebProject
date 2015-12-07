package org.peter.poi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelTemplateManager {
	public static Map<Integer, ExcelTemplate> map = new ConcurrentHashMap<Integer, ExcelTemplate>();

	public static ExcelTemplate getExcelTemplate(int templateType) throws NoSuchMethodException,
			SecurityException {
		if (map.containsKey(templateType)) {
			return map.get(templateType);
		}
		switch (templateType) {
		case 1: {
			ExcelTemplate template = new ExcelBeansTemplate();
			map.put(templateType, template);
			return template;
		}
		default: {
			return null;
		}
		}
	}
}
