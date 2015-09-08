package org.peter.bean;

import com.alibaba.fastjson.JSONObject;

public class Bean {
	private Long id;
	private String name;
	private String value;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bean [id=").append(id).append(", name=").append(name).append(", value=")
				.append(value).append("]");
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