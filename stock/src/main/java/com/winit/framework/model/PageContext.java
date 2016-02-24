package com.winit.framework.model;

/**
 * 分页辅助信息，在线程内保存当前线程分页的基本设置
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * lunan.xu   1.0           2012-12-8  Created
 * </pre>
 * 
 * @since 1.
 */
public class PageContext {
	public final static int DEFAULT_PAGE_SIZE = 20;

	/** 每页记录数 */
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
	/** 开始记录 */
	private static ThreadLocal<Integer> startIndex = new ThreadLocal<Integer>();
	/** 根据那个字段排序 */
	private static ThreadLocal<String> sortField = new ThreadLocal<String>();
	/** 升序还是降序 */
	private static ThreadLocal<String> sortType = new ThreadLocal<String>();

	public static String getSortType() {
		return sortType.get();
	}

	public static void setSortType(String _order) {
		sortType.set(_order);
	}

	public static void removeSortType() {
		sortType.remove();
	}

	public static String getSortField() {
		return sortField.get();
	}

	public static void setSortField(String _sort) {
		sortField.set(_sort);
	}

	public static void removeSortField() {
		sortField.remove();
	}

	public static int getStartIndex() {
		return startIndex.get();
	}

	public static void setStartIndex(int _pageOffset) {
		startIndex.set(_pageOffset);
	}

	public static void removeStartIndex() {
		startIndex.remove();
	}

	public static void setPageSize(int _pageSize) {
		pageSize.set(_pageSize);
	}

	public static int getPageSize() {
		return pageSize.get();
	}

	public static void removePageSize() {
		pageSize.remove();
	}

}
