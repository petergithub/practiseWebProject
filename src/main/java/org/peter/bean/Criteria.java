package org.peter.bean;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.peter.util.Constants;

/**
 * query criteria
 * 
 * @author Shang Pu
 * @version Date: Sep 8, 2015 11:39:39 AM
 */
public class Criteria {

	@NotNull(message = "Required id")
	@Min(3)
	private Long id; // 查询ID
	@NotBlank
	private String name;
	
	private List<String> values;
	
	@Size(min = 2, max = 4)
	private String sort; // 排序字段 默认操作时间
	@NotNull
	private String order = Constants.OrderDesc; // asc/desc 默认倒序
	private Integer pageNo = Constants.PageNoDefault;// 表示第几页，默认为1
	private Integer pageSize = Constants.PageSizeDefault;// 每页返回多少条数据，默认20

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Criteria [id=").append(id).append(", name=").append(name)
				.append(", values=").append(values).append(", sort=").append(sort)
				.append(", order=").append(order).append(", pageNo=").append(pageNo)
				.append(", pageSize=").append(pageSize).append("]");
		return builder.toString();
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

}
