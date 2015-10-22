package org.peter.web.controller;

import static org.peter.util.Constants.ResponseCode_Success;
import static org.peter.util.Constants.ResponseCode_UnknownError;
import static org.peter.util.Constants.ResponseMsg_InvalidParameter;
import static org.peter.util.Constants.ResponseMsg_Success;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.peter.bean.Bean;
import org.peter.bean.BeanImplList;
import org.peter.bean.BeanListWrapper;
import org.peter.bean.Criteria;
import org.peter.bean.JsonResult;
import org.peter.poi.ExcelTemplate;
import org.peter.poi.ExcelTemplateManager;
import org.peter.poi.ExcelUtil;
import org.peter.util.Constants;
import org.peter.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

//@Controller
@RestController
@RequestMapping
// ("/query/order")
public class PracticeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PracticeController.class);

//	@Value("#{jdbc['jdbc.url']}")
	@Value("${jdbc.url}")
	private String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8";

	// http://localhost:8080/webapp/getJson?names=['1','2']&id=2
	@RequestMapping(value = "/getJson", method = { RequestMethod.POST, RequestMethod.GET }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// produces = "application/json;charset=UTF-8")
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
		String statusCode = ResponseCode_Success;

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
			statusCode = ResponseCode_UnknownError;
			result.put("response", statusCode);
		}
		result.put("names", ret);
		result.put("id", id);

		return LogUtil.buildResult(JSON.toJSONString(result), request, log);
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
		JsonResult json = new JsonResult(ResponseCode_Success, ResponseMsg_Success, bean);

		return LogUtil.buildResult(JSON.toJSONString(json), request, log);
	}

	@RequestMapping(value = "/getPathVariable/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getPathVariable(@PathVariable("id") String id, final HttpServletRequest request) {
		log.info("Enter getPathVariable(id[{}])", id);

		JsonResult json = new JsonResult(ResponseCode_Success, ResponseMsg_Success, id);

		return LogUtil.buildResult(JSON.toJSONString(json), request, log);
	}

	@RequestMapping(value = "/getBeanCondition")
	@ResponseBody
	public String getBeanCondition(@RequestParam("condition") String json,
			@RequestParam("date") String dateStr, final HttpServletRequest request) {
		log.info("Enter getBeanCondition(json[{}],dateStr[{}])", json, dateStr);

		Bean bean = JSON.parseObject(json, Bean.class);
		JsonResult jsonResult = new JsonResult(ResponseCode_Success, ResponseMsg_Success, bean);

		return LogUtil.buildResult(JSON.toJSONString(jsonResult), request, log);
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
	public String getBeanListWrapper(@Valid BeanListWrapper beans, BindingResult result,
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
			json = new JsonResult(Constants.ResponseCode_InvalidParameter,
					ResponseMsg_InvalidParameter, criteria);
		} else {
			json = new JsonResult(ResponseCode_Success, ResponseMsg_Success, criteria);
		}
		return LogUtil.buildResult(json.toJsonString(), request, log);
	}

	@RequestMapping(value = "/importFile", method = { RequestMethod.POST })
	public String importFile(MultipartFile file, HttpServletRequest request) {
		try {
			byte[] fileBytes = file.getBytes();
			log.debug("Enter fileSize[{}]", fileBytes.length);
		} catch (Exception e) {
			log.error("Exception in PracticeController.importFile()", e);
		}
		JsonResult result = new JsonResult(ResponseCode_Success, ResponseMsg_Success, null);
		return LogUtil.buildResult(result.toJsonString(), request, log);
	}

	@RequestMapping(value = "/exportFile", method = { RequestMethod.GET })
	public ResponseEntity<Object> exportFile(HttpServletRequest request) {
		try {
			ExcelTemplate template = ExcelTemplateManager.getExcelTemplate(1);
			List<Bean> beans = new ArrayList<>();
			beans.add(new Bean(1l, "name1", "value1", new Date()));
			beans.add(new Bean(2l, "name2", "value2", new Date()));
			byte[] bytes = ExcelUtil.convertDataToExcelFileBytes(template, beans);

			// 文件下载方法中用来生成ResponseEntity对象
			String fileName = "fileName.xls";
			MultiValueMap<String, String> headers = new HttpHeaders();
			headers.add("Content-Type", "application/octet-stream");
			headers.add("Content-Length", String.valueOf(bytes.length));
			headers.add("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			log.info("createResponseEntityWithFile, fileName:{}, contentLength:{}", fileName,
					bytes.length);
			ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(bytes, headers,
					HttpStatus.CREATED);

			return responseEntity;
		} catch (Exception e) {
			log.error("Exception in PracticeController.importFile()", e);
		}
		return null;
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