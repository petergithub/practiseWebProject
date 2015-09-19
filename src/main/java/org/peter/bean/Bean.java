package org.peter.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class Bean {
	@NotNull
	private Long id;
	private String name;
	private String value;
	private Date creationDate;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bean [id=").append(id).append(", name=").append(name).append(", value=")
				.append(value).append(", creationDate=").append(creationDate).append("]");
		return builder.toString();
	}

	public Bean(Long id, String name, String value, Date creationDate) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.creationDate = creationDate;
	}

	public Bean(Long id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public Bean() {
		super();
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}