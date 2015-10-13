package org.peter.bean;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;

public class JsonResult {
	private static final Logger log = LoggerFactory.getLogger(JsonResult.class);

	private String code;
	private String msg;
	private Object data;

	public static void main(String[] args) {
		JsonResult json = new JsonResult("code", "msg<alert>", new Bean(1l, "name<a>", "value"));
		json.toJsonString();
		json.toJsonStringFilter();
	}

	public JsonResult(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public JsonResult() {
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsonResult [code=").append(code).append(", msg=").append(msg)
				.append(", data=").append(data).append("]");
		return builder.toString();
	}

	/**
	 * com.alibaba.fastjson.serializer.SerializerFeature
	 * DisableCheckSpecialChar：一个对象的字符串属性中如果有特殊字符如双引号，
	 * 将会在转成json时带有反斜杠转移符。如果不需要转义 ，可以使用这个属性。默认为false
	 * QuoteFieldNames———-输出key时是否使用双引号,默认为true
	 * WriteMapNullValue——–是否输出值为null的字段,默认为false
	 * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
	 * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
	 * WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
	 * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
	 * 
	 * @return
	 */
	public String toJsonString() {
		String jsonString = JSON.toJSONString(this, FastJsonSerializeConfig.getInstance());
		log.info("jsonString = {}", jsonString);
		// String result = StringEscapeUtils.escapeHtml4(jsonString);
		String result = escape(jsonString);
		log.info("result = {}", result);
		return result;
	}

	public String toJsonStringFilter() {
		String jsonString = JSON.toJSONString(this, FastJsonSerializeConfig.getInstance(), filter);
		log.info("jsonString = {}", jsonString);
		return jsonString;
	}

	ValueFilter filter = new ValueFilter() {
		public String process(Object source, String name, Object value) {
			log.debug("Enter. value[{}]", value);
			if (value instanceof String) {
				value = StringEscapeUtils.escapeHtml4((String) value);
				log.debug("Exit. value[{}]", value);
				return (String) value;
			}
			return JSON.toJSONString(value, FastJsonSerializeConfig.getInstance(), filter);
		}
	};

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	static class FastJsonSerializeConfig {

		private static SerializeConfig mapping = new SerializeConfig();
		private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
		static {
			mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer(dateFormat));
			mapping.put(java.util.Date.class, new SimpleDateFormatSerializer(dateFormat));
		}

		public static SerializeConfig getInstance() {
			return mapping;
		}
	}

	/**
	 * escape blow character
	 * <pre>
	 * {"&", "&amp;"},   // & - ampersand
	 * {"<", "&lt;"},    // < - less-than
	 * {">", "&gt;"},    // > - greater-than
	 * </pre>
	 */
	public static String escape(String aText) {
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			} else if (character == '&') {
				result.append("&amp;");
			}
			// else if (character == '\"') {
			// result.append("&quot;");
			// }
			else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}
}
