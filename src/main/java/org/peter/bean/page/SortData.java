package org.peter.bean.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class SortData implements Iterable<SortData.OrderData>, Serializable {

	private static final long serialVersionUID = 1L;

	public static final Direction DEFAULT_DIRECTION = Direction.DESC;

	private final List<OrderData> orders;

	/**
	 * @param orders
	 *            不能是 null。
	 */
	public SortData(OrderData... orders) {
		this(Arrays.asList(orders));
	}

	/**
	 * @param orders
	 *            不能是 null，也不能包含 null。
	 */
	public SortData(List<OrderData> orders) {
		if (null == orders || orders.isEmpty()) {
			throw new IllegalArgumentException("你必须提供至少一个排序属性！");
		}

		this.orders = orders;
	}

	/**
	 * @param properties
	 *            不能是 null，也不能包含 null。
	 */
	public SortData(String... properties) {
		this(DEFAULT_DIRECTION, properties);
	}

	/**
	 * @param direction
	 *            当direction为null时，默认为DEFAULT_DIRECTION
	 * @param properties
	 *            不能是 null，也不能包含 null或空串
	 */
	public SortData(Direction direction, String... properties) {
		this(direction, properties == null ? new ArrayList<String>() : Arrays.asList(properties));
	}

	/**
	 * @param direction
	 * @param properties
	 */
	public SortData(Direction direction, List<String> properties) {
		if (properties == null || properties.isEmpty()) {
			throw new IllegalArgumentException("你必须提供至少一个排序属性！");
		}

		this.orders = new ArrayList<OrderData>(properties.size());

		for (String property : properties) {
			this.orders.add(new OrderData(direction, property));
		}
	}

	/**
	 * 返回由当前Sort与给定sort取并集后的新的Sort。
	 * 
	 * @param sort
	 *            可以为null
	 * @return
	 */
	public SortData and(SortData sort) {
		if (sort == null) {
			return this;
		}

		ArrayList<OrderData> these = new ArrayList<OrderData>(this.orders);

		for (OrderData order : sort) {
			these.add(order);
		}

		return new SortData(these);
	}

	/**
	 * 返回给定property的Order对象
	 * 
	 * @param property
	 * @return
	 */
	public OrderData getOrderFor(String property) {
		for (OrderData order : this) {
			if (order.getProperty().equals(property)) {
				return order;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<OrderData> iterator() {
		return this.orders.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SortData)) {
			return false;
		}

		SortData that = (SortData) obj;

		return this.orders.equals(that.orders);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + orders.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringUtils.collectionToCommaDelimitedString(orders);
	}

	/**
	 * 排序方向的枚举类型
	 */
	public static enum Direction {
		ASC, DESC;

		/**
		 * 根据给定值返回相应枚举实例。
		 * 
		 * @param value
		 * @return
		 */
		public static Direction fromString(String value) {
			try {
				return Direction.valueOf(value.toUpperCase(Locale.US));
			} catch (Exception e) {
				throw new IllegalArgumentException(String.format(
						"非法的值 '%s' ！ 合法的值只能是 “desc”或“asc”（大小写不敏感）。", value), e);
			}
		}
	}

	/**
	 * 排序属性
	 */
	public static class OrderData implements Serializable {
		private static final long serialVersionUID = 1L;

		private final Direction direction;
		private final String property;

		/**
		 * @param direction
		 *            当direction是null时，默认为DEFAULT_DIRECTION
		 * @param property
		 *            不能是null或空串
		 */
		public OrderData(Direction direction, String property) {
			if (!StringUtils.hasText(property)) {
				throw new IllegalArgumentException("属性不能是null或空串！");
			}

			this.direction = direction == null ? DEFAULT_DIRECTION : direction;
			this.property = property;
		}

		/**
		 * @param property
		 *            不能是null或空串
		 */
		public OrderData(String property) {
			this(DEFAULT_DIRECTION, property);
		}

		/**
		 * 获得当前属性的排序方向。
		 * 
		 * @return
		 */
		public Direction getDirection() {
			return direction;
		}

		/**
		 * 获得当前排序属性的字符串表示。
		 * 
		 * @return
		 */
		public String getProperty() {
			return property;
		}

		/**
		 * 返回当前排序属性是否是增序排序。
		 * 
		 * @return
		 */
		public boolean isAscending() {
			return this.direction.equals(Direction.ASC);
		}

		/**
		 * 根据给定的Direction返回一个新的Order.
		 * 
		 * @param order
		 * @return
		 */
		public OrderData with(Direction order) {
			return new OrderData(order, this.property);
		}

		/**
		 * 根据给定的properties返回一个新的Sort
		 * 
		 * @param properties
		 * @return
		 */
		public SortData withProperties(String... properties) {
			return new SortData(this.direction, properties);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			int result = 17;

			result = 31 * result + direction.hashCode();
			result = 31 * result + property.hashCode();

			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof OrderData)) {
				return false;
			}

			OrderData that = (OrderData) obj;

			return this.direction.equals(that.direction) && this.property.equals(that.property);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("%s: %s", property, direction);
		}
	}

	public static void main(String[] args) {
		SortData sort = new SortData("abc", "kkk", "III");
		System.out.println(sort);
	}

}
