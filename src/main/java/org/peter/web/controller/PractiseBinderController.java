package org.peter.web.controller;

import static org.peter.util.Constants.ResponseCode_Success;
import static org.peter.util.Constants.ResponseMsg_Success;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.peter.bean.Bean;
import org.peter.bean.Criteria;
import org.peter.bean.JsonResult;
import org.peter.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PractiseBinderController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(PractiseBinderController.class);

	@RequestMapping(value = "/getDate")
	@ResponseBody
	public JsonResult getDate(Bean bean) {
		log.info("Enter getBean(bean[{}])", bean);

		JsonResult result = new JsonResult(ResponseCode_Success, ResponseMsg_Success, null);
		return result;
	}

	@ResponseBody
	@RequestMapping("/sameName")
	public JsonResult sameName(Bean bean, Criteria criteria) {
		log.debug("Enter bean[{}],criteria[{}]", bean, criteria);
		return new JsonResult();
	}

	@RequestMapping("/sameNameBinder")
	public JsonResult sameNameBinder(@ModelAttribute("criteria") Criteria criteria,
			@ModelAttribute("bean") Bean bean) {
		log.debug("Enter bean[{}],criteria[{}]", bean, criteria);
		return new JsonResult();
	}

	@InitBinder("bean")
	public void initBinderBean(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("bean.");
	}

	@InitBinder("criteria")
	public void initBinderCriteria(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("c.");
	}

	public void initBinderValidator(WebDataBinder binder) {
		Validator validator = new Validator() {
			@Override
			public boolean supports(Class<?> clazz) {
				return false;
			}

			@Override
			public void validate(Object target, Errors errors) {
			}
		};
		binder.setValidator(validator);
	}

	@InitBinder
	protected void initBinderFormat(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
				Constants.dateFormat), true));
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