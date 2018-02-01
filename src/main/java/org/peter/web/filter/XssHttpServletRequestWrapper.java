package org.peter.web.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private static final Logger log = LoggerFactory
			.getLogger(XssHttpServletRequestWrapper.class);
	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";

	private String[] filterChars;
	private String[] replaceChars;

	public XssHttpServletRequestWrapper(HttpServletRequest request, String filterChar,
			String replaceChar, String splitChar) {
		super(request);
		if (filterChar != null && filterChar.length() > 0) {
			filterChars = filterChar.split(splitChar);
		}
		if (replaceChar != null && replaceChar.length() > 0) {
			replaceChars = replaceChar.split(splitChar);
		}
	}

	public String getQueryString() {
		String value = super.getQueryString();
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	public String[] getParameterValues(String name) {
		String[] parameters = super.getParameterValues(name);
		if (parameters == null || parameters.length == 0) {
			return null;
		}
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = xssEncode(parameters[i]);
		}
		return parameters;
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	public String getHeader(String name) {

		String value = super.getHeader(xssEncode(name));
		if (value != null) {
			value = xssEncode(value);
		}
		return value;
	}

	/**
	 * 将容易引起xss漏洞的半角字符直接替换成全角字符
	 * 
	 * @param s
	 * @return
	 */
	private String xssEncode(String s) {
		if (s == null || s.equals("")) {
			return s;
		}
		// %作为特殊解码字符，get方式提交的汉字+%会解码不了
		try {
			s = URLDecoder.decode(s, UTF8);
		} catch (UnsupportedEncodingException e) {
			log.error("Exception in XssHttpServletRequestWrapper.xssEncode()", e);
		} catch (Exception e) {
			log.error("Exception in XssHttpServletRequestWrapper.xssEncode()", e);
		}
		for (int i = 0; i < filterChars.length; i++) {
			if (s.contains(filterChars[i])) {
				s = s.replace(filterChars[i], replaceChars[i]);
			}
		}
		return s;
	}
}