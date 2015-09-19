package org.peter.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.peter.bean.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
public class PractiseDateBinderController {
	private static final Logger log = LoggerFactory.getLogger(PractiseDateBinderController.class);

	private static final String STATUS_SUCCESS = "Sucess";

	@RequestMapping(value = "/getBean", method = RequestMethod.GET)
	@ResponseBody
	public String getBean(Bean bean, final HttpServletRequest request) {
		log.info("Enter getBean(bean[{}])", bean);

		JSONObject result = new JSONObject();
		String statusCode = STATUS_SUCCESS;
		result.put("response", statusCode);
		log.info("JSON.toJSONString(bean) = {}", JSON.toJSONString(bean));
		result.put("bean", bean);
		log.debug("Exit getBean() result = {}", result);
		return result.toString();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
				"yyyyMMdd HH:mm:ss"), true));
		// binder.registerCustomEditor(int.class, new
		// CustomNumberEditor(int.class, true));
		// binder.registerCustomEditor(int.class, new IntegerEditor());
		// binder.registerCustomEditor(long.class, new
		// CustomNumberEditor(long.class, true));
		// binder.registerCustomEditor(long.class, new LongEditor());
		// binder.registerCustomEditor(double.class, new DoubleEditor());
		// binder.registerCustomEditor(float.class, new FloatEditor());
	}

}