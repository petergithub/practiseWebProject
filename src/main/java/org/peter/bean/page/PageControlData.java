package org.peter.bean.page;

import java.io.Serializable;
import java.util.List;

public class PageControlData<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 默认页面尺寸
	private static final int DEFULT_PAGE_SIZE = 10;

	/**
	 * 原始的结果的列表
	 */
	private List<T> resultList;

	/**
	 * 页面总数
	 */
	private int pageCount;

	/**
	 * 当前页面序号
	 */
	private int currentPage;

	/**
	 * 总记录条数
	 */
	private int resultCount;

	/**
	 * 页面尺寸
	 */
	private int pageSize = DEFULT_PAGE_SIZE;

	/**
	 * 跳转到的页面序号
	 */
	private int changePageNumber;

	/**
	 * 起始的记录序号
	 */
	private int startRowNum;

	/**
	 * 终了记录序号
	 */
	private int endRowNum;

	/**
	 * 描述：用于排序的对象
	 */
	private SortData sort;

	public PageControlData() {

	}

	/**
	 * 方法名称: init 内容摘要: 初始化分页对象
	 */
	public void init() {
		// 初始化页面总数
		pageCount = 0;
		// 初始化当前页面序号
		currentPage = 0;
		// 初始化总记录条数
		resultCount = 0;
		// 初始化页面尺寸
		pageSize = DEFULT_PAGE_SIZE;
	}

	/**
	 * Access method for the resultList property.
	 *
	 * @return the current value of the resultList property
	 */
	public List<T> getResultList() {
		return resultList;
	}

	/**
	 * Sets the value of the resultList property.
	 *
	 * @param aResultList
	 *            the new value of the resultList property
	 */
	public void setResultList(List<T> aResultList) {
		resultList = aResultList;
	}

	/**
	 * Access method for the pageCount property.
	 *
	 * @return the current value of the pageCount property
	 */
	public int getPageCount() {
		// 判断记录总数是否能整除页尺寸
		if (resultCount % pageSize == 0) {
			// 整除则直接取整相除
			pageCount = (resultCount / pageSize);
		} else {
			// 否则取整相除后加一
			pageCount = (resultCount / pageSize) + 1;
		}
		return pageCount;
	}

	/**
	 * Sets the value of the pageCount property.
	 *
	 * @param aPageCount
	 *            the new value of the pageCount property
	 */
	public void setPageCount(int aPageCount) {
		pageCount = aPageCount;
	}

	/**
	 * Access method for the currentPage property.
	 *
	 * @return the current value of the currentPage property
	 */
	public int getCurrentPage() {
		// 判断总记录数大于零且当前也是小于一的情况
		if (currentPage < 1 && resultCount > 0) {
			currentPage = 1;
		}
		// 判断当前页序号是否溢出
		if (currentPage > getPageCount()) {
			currentPage = pageCount;
		}
		return currentPage;
	}

	/**
	 * Sets the value of the currentPage property.
	 *
	 * @param aCurrentPage
	 *            the new value of the currentPage property
	 */
	public void setCurrentPage(int aCurrentPage) {
		// 设置当前页序号、小于零的情况忽略
		if (aCurrentPage >= 0) {
			currentPage = aCurrentPage;
		}
	}

	/**
	 * Access method for the resultCount property.
	 *
	 * @return the current value of the resultCount property
	 */
	public int getResultCount() {
		return resultCount;
	}

	/**
	 * Sets the value of the resultCount property.
	 *
	 * @param aResultCount
	 *            the new value of the resultCount property
	 */
	public void setResultCount(int aResultCount) {
		// 设置总记录条数
		resultCount = aResultCount;
	}

	/**
	 * Access method for the pageSize property.
	 *
	 * @return the current value of the pageSize property
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the value of the pageSize property.
	 *
	 * @param aPageSize
	 *            the new value of the pageSize property
	 */
	public void setPageSize(int aPageSize) {
		pageSize = aPageSize;

	}

	/**
	 * Access method for the changePageNumber property.
	 *
	 * @return the current value of the changePageNumber property
	 */
	public int getChangePageNumber() {
		return changePageNumber;
	}

	/**
	 * Sets the value of the changePageNumber property.
	 *
	 * @param aChangePageNumber
	 *            the new value of the changePageNumber property
	 */
	public void setChangePageNumber(int aChangePageNumber) {
		// 设置跳转到的页面序号
		changePageNumber = aChangePageNumber;
		// 设置当前页序号
		setCurrentPage(changePageNumber);
	}

	/**
	 * Determines if the isFirstPage property is true.
	 *
	 * @return <code>true<code> if the isFirstPage property is true
	 */
	public boolean getIsFirstPage() {
		return currentPage <= 1 ? true : false;
	}

	/**
	 * Determines if the isLastPage property is true.
	 *
	 * @return <code>true<code> if the isLastPage property is true
	 */
	public boolean getIsLastPage() {
		return pageCount <= currentPage ? true : false;
	}

	/**
	 * Access method for the startRowNum property.
	 *
	 * @return the current value of the startRowNum property
	 */
	public int getStartRowNum() {
		// 判断记录总数是否能整除页尺寸
		if (currentPage > getPageCount()) {
			currentPage = pageCount;
		}
		startRowNum = (currentPage - 1) * pageSize > 0 ? (currentPage - 1) * pageSize : 0;
		return startRowNum;
	}

	/**
	 * Access method for the endRowNum property.
	 *
	 * @return the current value of the endRowNum property
	 */
	public int getEndRowNum() {
		// 判断记录总数是否能整除页尺寸
		if (currentPage > getPageCount()) {
			currentPage = pageCount;
		}
		// 如果当前页小于一则结束序号为页面大小，否则按公式计算
		endRowNum = (currentPage - 1) > 0 ? (currentPage - 1) * pageSize + pageSize : pageSize;
		return endRowNum;
	}

	/**
	 * Sets the value of the startRowNum property.
	 *
	 * @param aStartRowNum
	 *            the new value of the startRowNum property
	 */
	public void setStartRowNum(int aStartRowNum) {
		startRowNum = aStartRowNum;
	}

	/**
	 * Sets the value of the endRowNum property.
	 *
	 * @param aEndRowNum
	 *            the new value of the endRowNum property
	 */
	public void setEndRowNum(int aEndRowNum) {
		endRowNum = aEndRowNum;
	}

	// 获取当前页面记录数
	public int getPageDataCount() {
		if (resultList != null)
			return resultList.size();
		else
			return 0;
	}

	public SortData getSort() {
		return sort;
	}

	public void setSort(SortData sort) {
		this.sort = sort;
	}
	
	
}