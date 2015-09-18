package org.peter.bean;

import java.util.List;

/**
 * @author Shang Pu
 * @version Date: Sep 17, 2015 11:13:55 AM
 */
public class BeanList {
	private List<Bean> beans;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeanList [beans=").append(beans).append("]");
		return builder.toString();
	}

	public BeanList() {
		super();
	}

	public BeanList(List<Bean> beans) {
		super();
		this.beans = beans;
	}

	public List<Bean> getBeans() {
		return beans;
	}

	public void setBeans(List<Bean> beans) {
		this.beans = beans;
	}
}
