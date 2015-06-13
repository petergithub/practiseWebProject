package org.peter.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@Controller
//@RestController
@RequestMapping //("/query/order")
public class ControllerExample {
	private static final Logger log = LoggerFactory.getLogger(ControllerExample.class);

	private static final String STATUS_SUCCESS = "statusSucess";
	private static final String STATUS_UNKOWN_ERROR = "statusUnknowError";

	//http://localhost:8080/webapp/getJson?orderNums=['1','2']
	@RequestMapping(value = "/getJson")
	@ResponseBody // @ResponseBody is the default value if @RestController is set for the controller class
	public String getJson(@RequestParam(required = true) String orderNums, final HttpServletRequest request) {
		log.info("Enter getJson(orderNums[{}])", orderNums);
		Assert.notNull(orderNums, "orderNumbs cannot be null");
		Assert.hasText(orderNums, "orderNumbs must contain at least one non-whitespace character.");

		JSONObject result = new JSONObject();
		JSONArray ret = new JSONArray();
		String statusCode = STATUS_SUCCESS;
		// default status code
		result.put("response", statusCode);

		try {
			JSONArray orderArray = JSONArray.parseArray(orderNums);
			Object[] orderNumArray = orderArray.toArray();
			for (Object orderNum : orderNumArray) {
				log.debug("orderNum = {}", orderNum);
				JSONObject o = new JSONObject();
				try {
					o.put("orderNums", orderNum);
					o.put("status", "status" + orderNum);
					ret.add(o);
				} catch (JSONException e) {
					log.error("JSONException in OrderStatusApi.getStatus()", e);
					statusCode = STATUS_UNKOWN_ERROR;
				}
			}
		} catch (Exception e) {
			log.error("Exception in OrderStatusApi.getStatus()", e);
			statusCode = STATUS_UNKOWN_ERROR;
			result.put("response", statusCode);
		}
		result.put("order", ret);

		log.debug("Exit getStatus() result = {}", result);
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