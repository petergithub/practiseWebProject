package org.peter.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class ExcelReportController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(ExcelReportController.class);

	@Override
	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String output = ServletRequestUtils.getStringParameter(request, "output");
		log.info("Enter handleRequestInternal(output[{}])", output);

		// dummy data
		Map<String, String> revenueData = new HashMap<String, String>();
		revenueData.put("Jan-2010", "$100,000,000");
		revenueData.put("Feb-2010", "$110,000,000");
		revenueData.put("Mar-2010", "$130,000,000");
		revenueData.put("Apr-2010", "$140,000,000");
		revenueData.put("May-2010", "$200,000,000");

		if (output == null || "".equals(output)) {
			// return normal view
			return new ModelAndView("excelReportJsp", "revenueData", revenueData);

		} else if ("EXCEL".equals(output.toUpperCase())) {
			// return excel view
			String title = "revenueData";
			return new ModelAndView("ExcelReport", title, revenueData);

		} else {
			// return normal view
			return new ModelAndView("excelReportJsp", "revenueData", revenueData);

		}
	}
}