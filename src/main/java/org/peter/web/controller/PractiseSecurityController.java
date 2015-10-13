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

	/**
	 * "http://localhost:8080/webapp/getJsonHtmlTag?tag=<script>alert()</script>"
	 */
	@RequestMapping(value = "/getJsonHtmlTag")
	public JsonResult getJsonHtmlTag(String tag) {
		log.info("Enter getJsonHtmlTag(tag[{}])", tag);

		JsonResult result = new JsonResult(ResponseCode_Success, ResponseMsg_Success, tag);

		return result;
	}

	/**
	 * "http://localhost:8080/webapp/getStringHtmlTag?tag=<script>alert()</script>"
	 */
	@RequestMapping(value = "/getStringHtmlTag")
	@ResponseBody
	public String getStringHtmlTag(String tag) {
		log.info("Enter getStringHtmlTag(tag[{}])", tag);

		JsonResult result = new JsonResult(ResponseCode_Success, ResponseMsg_Success, tag);

		String jsonString = result.toJsonString();
		return jsonString;
	}

}