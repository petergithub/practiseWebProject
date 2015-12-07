package org.peter.bean;

import java.util.List;

/**
 * @author Shang Pu
 * @version Date: Sep 17, 2015 11:13:55 AM
 */
public class BeanListWrapper {
	private List<Bean> beans;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BeanListWrapper [beans=").append(beans).append("]");
		return builder.toString();
	}

	public BeanListWrapper() {
		super();
	}

	public BeanListWrapper(List<Bean> beans) {
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
