package com.xu.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {

	/**
	 * 根据每页长度分页
	 * 
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public static <E> List<List<E>> subListBySize(List<E> list, int pageSize) {
		List<List<E>> subListPage = new ArrayList<List<E>>();
		int totalSize = list.size();
		int pages = totalSize % pageSize == 0 ? (totalSize / pageSize) : ((totalSize / pageSize) + 1);
		for (int i = 0; i < pages; i++) {
			int fromIndex = i * pageSize;
			int toIndex = i == (pages - 1) ? totalSize : (i + 1) * pageSize;
			List subList = list.subList(fromIndex, toIndex);
			subListPage.add(subList);
		}

		return subListPage;
	}

	/**
	 * 根据总页数分页
	 * 
	 * @param list
	 * @param pages
	 * @return
	 */
	public static <E> List<List<E>> subListByPages(List<E> list, int pages) {
		List<List<E>> subListPage = new ArrayList<List<E>>();

		int totalSize = list.size();
		int pageSize = totalSize % pages == 0 ? totalSize / pages : (totalSize / pages + 1);

		for (int i = 0; i < pages; i++) {
			int fromIndex = i * pageSize;
			int toIndex = i == (pages - 1) ? totalSize : (i + 1) * pageSize;
            if (fromIndex < totalSize) {
                toIndex = toIndex > totalSize ? totalSize : toIndex;
                List subList = list.subList(fromIndex, toIndex);
                subListPage.add(subList);
            }
		}

		return subListPage;
	}
}
