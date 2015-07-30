package org.peter.web.controller;

import static org.peter.util.RetConstants.KEY_RESPONSE;
import static org.peter.util.LogUtil.returnJson;

import javax.servlet.http.HttpServletRequest;

import org.peter.bean.HeaderBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
//@RestController
@RequestMapping //("/query/order")
public class ControllerExample {
	private static final Logger log = LoggerFactory.getLogger(ControllerExample.class);

	private static final String STATUS_SUCCESS = "Sucess";
	private static final String STATUS_UNKOWN_ERROR = "UnknowError";
	private static final int STATUS_CODE_SUCCESS = 0;
	private static final int STATUS_CODE_UNKOWN_ERROR = 4;

	//http://localhost:8080/webapp/getJson?names=['1','2']&id=2
	@RequestMapping(value = "/getJson", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody // @ResponseBody is the default value if @RestController is set for the controller class
	public String getJson(@RequestParam(required = true) String names, 
			@RequestParam(defaultValue = "1") Integer id,
			final HttpServletRequest request) {
//		Assert.hasText(names, "names must contain at least one non-whitespace character.");
		HeaderBean hb = new HeaderBean();
		
		//1. id has default value 1
		//2. id maybe null with URL: webapp/getJson?id=&names=['1','2']
		Assert.notNull(id, "id cannot be null");

		JSONObject result = new JSONObject();
		JSONArray ret = new JSONArray();
		int statusCode = STATUS_CODE_SUCCESS;
		String msg = STATUS_SUCCESS;

		//try catch all Exceptions
		try {
			// default status code
			JSONArray orderArray = JSONArray.parseArray(names);
			Object[] nameArray = orderArray.toArray();
			for (Object name : nameArray) {
				JSONObject o = new JSONObject();
				o.put("name", name);
				ret.add(o);
			}
		} catch (Exception e) {
//			log.error("Exception in OrderStatusApi.getStatus()", e);
			statusCode = STATUS_CODE_UNKOWN_ERROR;
			msg = STATUS_UNKOWN_ERROR;
		}
		result.put("names", ret);
		result.put("id", id);

		return returnJson(log, result, request, hb, statusCode, msg);
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
		result.put(KEY_RESPONSE, statusCode);
		result.put("bean", bean.toJson());
		log.debug("Exit getBean() result = {}", result);
		return result.toString();
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
	
	//http://localhost:8080/webapp/forwardUnderWebInf
	@RequestMapping(value = "/forwardUnderWebInf")
	public String forwardUnderWebInf(final HttpServletRequest request) {
		log.info("Enter forward(forwardUnderWebInf)");
		//spring-mvc.xml
		/*<!-- 定义跳转的文件的前后缀 ，视图模式配置 -->
		<bean
			class="org.springframework.web.servlet.view.InternalResourceViewResolver">
			<!-- 这里的配置我的理解是自动给后面action的方法return的字符串加上前缀和后缀，变成一个 可用的url地址 -->
			<property name="prefix" value="/WEB-INF/jsp/" />
			<property name="suffix" value=".jsp" />
		</bean>*/
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

class Bean {
	private Long id;
	private String name;
	private String value;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bean [id=").append(id).append(", name=").append(name).append(", value=").append(value)
				.append("]");
		return builder.toString();
	}
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("name", name);
		json.put("value", value);
		return json;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
