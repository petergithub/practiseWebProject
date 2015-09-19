package org.peter.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.peter.bean.Bean;
import org.peter.bean.BeanImplList;
import org.peter.bean.BeanList;
import org.peter.bean.Criteria;
import org.peter.bean.JsonResult;
import org.peter.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

//@Controller
@RestController
@RequestMapping
// ("/query/order")
public class PractiseController {
	private static final Logger log = LoggerFactory.getLogger(PractiseController.class);

	private static final String STATUS_SUCCESS = "Sucess";
	private static final String STATUS_UNKOWN_ERROR = "UnknowError";

	// http://localhost:8080/webapp/getJson?names=['1','2']&id=2
	@RequestMapping(value = "/getJson", method = { RequestMethod.POST, RequestMethod.GET }, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// @ResponseBody is the default value if @RestController is set for the
	// controller class
	public String getJson(@RequestParam(required = true) String names,
			@RequestParam(defaultValue = "1") Integer id,
			@RequestParam(defaultValue = "20") int pageSize, final HttpServletRequest request) {
		log.info("Enter getJson(names[{}])", names);
		Assert.hasText(names, "names must contain at least one non-whitespace character.");

		// 1. id has default value 1
		// 2. Integer id maybe null with URL: webapp/getJson?id=&names=['1','2']
		Assert.notNull(id, "id cannot be null");

		JSONObject result = new JSONObject();
		JSONArray ret = new JSONArray();
		String statusCode = STATUS_SUCCESS;

		// try catch all Exceptions
		try {
			// default status code
			result.put("response", statusCode);
			JSONArray orderArray = JSONArray.parseArray(names);
			Object[] nameArray = orderArray.toArray();
			for (Object name : nameArray) {
				log.debug("name = {}", name);
				JSONObject o = new JSONObject();
				o.put("name", name);
				ret.add(o);
			}
		} catch (Exception e) {
			log.error("Exception in OrderStatusApi.getStatus()", e);
			statusCode = STATUS_UNKOWN_ERROR;
			result.put("response", statusCode);
		}
		result.put("names", ret);
		result.put("id", id);

		log.debug("Exit getJson() result = {}", result);
		return result.toString();
	}

	// http://localhost:8080/webapp/getBean?name=n1&id=1
	// bean[Bean [id=1, name=n1]]
	// http://localhost:8080/webapp/getBean?name=n1&id=1&name=n2&id=2
	// http://localhost:8080/webapp/getBean?name=n1&id=1&name=n2&id=2&value=v1&value=v2
	// bean[Bean [id=1, name=n1,n2, value=v1,v2]]
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

	@RequestMapping(value = "/getBeanList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getBeanList(@Valid @RequestBody List<Bean> beans, BindingResult result,
			HttpServletRequest request) {
		log.info("Enter getBeanList(beans[{}])", beans);

		String json = JSONArray.toJSONString(beans);
		return LogUtil.buildResult(json, request, log);
	}

	@RequestMapping(value = "/getBeanListWrapper", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getBeanListWrapper(@Valid @RequestBody BeanList beans, BindingResult result,
			HttpServletRequest request) {
		log.info("Enter getBeanList(beans[{}])", beans);
		List<Bean> beanList = beans.getBeans();
		log.info("beanList = {}", beanList);

		String json = JSONArray.toJSONString(beans);
		return LogUtil.buildResult(json, request, log);
	}

	@RequestMapping(value = "/getBeanImplList", method = { RequestMethod.GET, RequestMethod.POST }, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String getBeanImplList(@RequestBody BeanImplList beans, HttpServletRequest request) {
		log.info("Enter getBeanImplList(beans[{}])", beans);

		String json = JSONArray.toJSONString(beans);
		return LogUtil.buildResult(json, request, log);
	}

	@RequestMapping(value = "/getBeanStr", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getBeanStr(String test, String beans, HttpServletRequest request) {
		log.info("Enter getBeanStr(beans[{}])", beans);
		log.info("test = {}", test);

		String json = JSONArray.toJSONString(beans);
		return LogUtil.buildResult(json, request, log);
	}

	@RequestMapping(value = "/getBeanArray", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getBeanArray(@RequestBody Bean[] beans, BindingResult result,
			HttpServletRequest request) {
		log.info("Enter getBeanArray(beans[{}])", beans[0]);

		String json = JSONArray.toJSONString(beans);
		return LogUtil.buildResult(json, request, log);
	}

	@RequestMapping(value = "/getBeanCriteria", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getBeanCriteria(Criteria criteria, Bean bean, HttpServletRequest request) {
		log.info("Enter getBeanCriteria(bean[{}])", bean);
		log.info("criteria = {}", criteria);

		String json = JSONArray.toJSONString(bean);
		return LogUtil.buildResult(json, request, log);
	}

	// http://localhost:8080/webapp/getCriteria?id=3&sort=sort&order=&pageNo=1&name=name&values=v1,v2
	// no validation error
	// http://localhost:8080/webapp/getCriteria?id=&sort=s&order=&pageNo=&values=v1,v2
	// get validation error: Long id is null,
	// int pageNo is not null but with convert exception
	// sort is short than 2
	@RequestMapping(value = "/getCriteria", method = RequestMethod.GET)
	@ResponseBody
	public String getCriteria(@Valid Criteria criteria, BindingResult result,
			HttpServletRequest request) {
		log.info("Enter getCriteria(criteria[{}])", criteria);
		Bean bean = new Bean();
		BeanUtils.copyProperties(criteria, bean);
		log.info("bean = {}", bean);

		log.info("criteria.getValues() = {}", criteria.getValues());

		JsonResult json = new JsonResult();
		if (result.hasErrors()) {
			log.error("result.getFieldErrors() = {}", result.getFieldErrors());
			// Field error in object 'criteria' on field 'id': rejected value
			// [null]; codes
			// [NotNull.criteria.id,NotNull.id,NotNull.java.lang.Long,NotNull];
			// arguments
			// [org.springframework.context.support.DefaultMessageSourceResolvable:
			// codes [criteria.id,id]; arguments []; default message [id]];
			// default message [Required id]
			json = new JsonResult("1", "Invalid parameter", criteria);
		} else {
			json = new JsonResult("0", "Success", criteria);
		}
		return LogUtil.buildResult(json.toJsonString(), request, log);
	}

	@RequestMapping(value = "forward")
	public String forward(final HttpServletRequest request, Model model) {
		log.info("Enter forward(forward)");
		model.addAttribute("value", "value");
		// start without /
		return "index.jsp";
	}

	@RequestMapping(value = "/forward/second")
	public String forwardSecond(final HttpServletRequest request) {
		log.info("Enter forward(/forward/second)");
		// start with /
		return "/index.jsp";
	}

	// http://localhost:8080/webapp/forwardUnderWebInf
	@RequestMapping(value = "/forwardUnderWebInf")
	public String forwardUnderWebInf(final HttpServletRequest request) {
		log.info("Enter forward(forwardUnderWebInf)");
		// spring-mvc.xml
		/*
		 * <!-- 定义跳转的文件的前后缀 ，视图模式配置 --> <bean
		 * class="org.springframework.web.servlet.view.InternalResourceViewResolver"
		 * > <!-- 这里的配置我的理解是自动给后面action的方法return的字符串加上前缀和后缀，变成一个 可用的url地址 -->
		 * <property name="prefix" value="/WEB-INF/jsp/" /> <property
		 * name="suffix" value=".jsp" /> </bean>
		 */
		return "indexUnderWebInf";
	}

	@RequestMapping(value = "/redirect")
	public String redirect(final HttpServletRequest request) {
		log.info("Enter redirect(/redirect)");
		return createRedirectViewPath("redirectPath");
	}

	private String createRedirectViewPath(String requestMapping) {
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(requestMapping);
		log.info("redirectViewPath = {}", redirectViewPath);
		return redirectViewPath.toString();
	}
}