package org.peter.util;

import static org.peter.util.RetConstants.KEY_RESPONSE;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.peter.bean.HeaderBean;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class LogUtil {

	public static void info(Logger log, String ip, String params, String response) {
		log.info("{},{},{},{}", new Object[] { ip, params, response });
	}

	public static void info(Logger log, String uri, String ip, String params, String response) {
		log.info("{},{},{},{}", new Object[] { uri, ip, params, response });
	}

	public static void info(Logger log, String uri, String ip, String params, String header,
			String response) {
		log.info("{},{},{},{},{}", new Object[] { uri, ip, header, params, response });
	}

	public static void info(Logger log, String uri, String ip, String params, String header,
			String response, String msg) {
		log.info("{},{},{},{},{},{}", new Object[] { uri, ip, header, params, response, msg });
	}

	public static void error(Logger log, String ip, String params, Object e) {
		log.info("{},{},{}", new Object[] { ip, params, e });
	}

	public static void error(Logger log, String uri, String ip, String params, String header,
			String response, Object e) {
		log.info("{},{},{},{},{},{}", new Object[] { uri, ip, header, params, response, e });
	}

	public static String buildResult(String result, HttpServletRequest request, Logger log) {
		// LogUtil.info(log, request.getRequestURI(), IpUtils.getIp(request),
		// getParameters(request), hb.toString(), ret.toString(), msg);
		log.info("Exit result = {}", result);
		return result;
	}
	
	public static String returnJson(Logger log, JSONObject ret, HttpServletRequest req, HeaderBean hb,
			int respCode, String msg) {
		try {
			ret.put(KEY_RESPONSE, respCode);
		} catch (JSONException e) {
		}
//		LogUtil.info(log, req.getRequestURI(), IpUtils.getIp(req), getParameters(req),
//				String.valueOf(hb), ret.toString(), msg);
		LogUtil.info(log, req.getRequestURI(), IpUtils.getIp(req), getParameters(req),
				"header", ret.toString(), msg);
		return ret.toString();
	}

	/**
	 * @param request
	 * @return paramName1=value1&paramName2=[value1,value2]
	 */
	public static String getParameters(HttpServletRequest request) {
		String method = request.getMethod();
		if (method.equalsIgnoreCase(HttpRequestUtil.METHOD_GET)) {
			return request.getQueryString();
		} else if (method.equalsIgnoreCase(HttpRequestUtil.METHOD_POST)) {
			Map<String, String[]> map = request.getParameterMap();
			StringBuilder builder = new StringBuilder();
			for (String paramName : map.keySet()) {
				builder.append(paramName);
				builder.append("=");
				String[] paramValues = request.getParameterValues(paramName);
				builder.append("[").append(arrayToString(paramValues)).append("]&");
			}
			String paramPost = builder.toString();
			if (paramPost.endsWith("&")) {
				paramPost = paramPost.substring(0, paramPost.length() - 1);
			}
			return paramPost;
		}
		return "Not support method: " + method; 
	}

	private static <E> String arrayToString(E[] array) {
		if (array == null || array.length == 0) return "";
		int length = array.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == length - 1) {
				sb.append(array[i]);
			} else {
				sb.append(array[i]).append(", ");
			}
		}
		return sb.toString();
	}
}
