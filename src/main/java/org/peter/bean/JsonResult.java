package org.peter.bean;

import com.alibaba.fastjson.JSON;


public class JsonResult {

	private String code;
	private String msg;
	private Object data;

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

	public String toJsonString() {
		return JSON.toJSONString(this);
	}

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

}
