package org.peter.web.controller;

import static org.peter.util.Constants.ResponseCode_Success;
import static org.peter.util.Constants.ResponseMsg_Success;

import org.peter.bean.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PractiseSecurityController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(PractiseSecurityController.class);

	@RequestMapping(value = "/getHtmlTag")
	@ResponseBody
	public JsonResult getHtmlTag(String tag) {
		log.info("Enter getBean(tag[{}])", tag);

		JsonResult result = new JsonResult(ResponseCode_Success, ResponseMsg_Success, tag);
		
		return result;
	}

}