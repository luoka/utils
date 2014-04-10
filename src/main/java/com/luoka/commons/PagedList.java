/**
 * Title:		luoka
 */
package com.luoka.commons;

import java.io.Serializable;
import java.util.List;

/**
 * 翻页封装类
 * @since luoka @ 2014年4月10日 下午4:05:29
 *
 */
public class PagedList<T> implements Serializable{

	private static final long serialVersionUID = 4025993914083105073L;
	
	private List<T> pageItems;

	/**
	 * page Index
	 */
	private int pageIndex;

	/**
	 * number in every page
	 */
	private int pageSize;

	/**
	 * total count
	 */
	private int totalItemCount;

	/**
	 * current page total
	 */
	private int thisPageTotal;

	/**
	 * total page number
	 */
	private int pageTotal;

	/**
	 * previous page
	 */
	private int prevPage;

	/**
	 * next page
	 */
	private int nextPage;

	/**
	 * page size
	 */
	private int step;

	/**
	 * start page
	 */
	private int startPage;

	/**
	 * end page
	 */
	private int endPage;

	/**
	 * 
	 * @param pageItems
	 * @param totalItemCount
	 */
	public PagedList(List<T> pageItems, int totalItemCount) {
		this(pageItems, 0, 20, totalItemCount);
	}

	/**
	 * 
	 * 
	 * @see #PagedList(int, int, int, List, int)
	 */
	public PagedList(List<T> pageItems, int pageIndex, int pageSize,
			int totalItemCount) {
		this(pageIndex, pageSize, totalItemCount, pageItems, 10);
	}

	/**
	 * 
	 * 
	 * @param pageItems
	 *            the {@link #pageItems}.
	 */
	public PagedList(List<T> pageItems) {
		this.pageIndex = 0;
		this.pageSize = pageItems.size();
		this.totalItemCount = pageItems.size();
		this.pageItems = pageItems;
		this.thisPageTotal = pageItems.size();
		// computePageIndex(step);
	}

	/**
	 * 
	 * 
	 * @param pageIndex
	 *            the {@link #pageIndex}.
	 * @param pageSize
	 *            the {@link #pageSize}.
	 * @param totalItemCount
	 *            the {@link #totalItemCount}.
	 * @param pageItems
	 *            the {@link #pageItems}.
	 * @param step
	 *            the {@link #step}.
	 */
	public PagedList(int pageIndex, int pageSize, int totalItemCount,
			List<T> pageItems, int step) {
		this.pageIndex = (pageIndex < 0) ? 0 : pageIndex;
		this.pageSize = (pageSize <= 0) ? 5 : pageSize;
		this.totalItemCount = totalItemCount;
		this.pageItems = pageItems;
		this.thisPageTotal = (pageItems == null) ? 0 : pageItems.size();

		computePageIndex(step);
	}


	/**
	 * 
	 * 
	 * @param stepValue
	 *            
	 */
	private void computePageIndex(int stepValue) {
		if (totalItemCount <= 0) {
			pageTotal = 0;
		} else {
			pageTotal = (totalItemCount / pageSize)
					+ ((totalItemCount % pageSize == 0) ? 0 : 1);
		}
		prevPage = (pageIndex == 0) ? 0 : pageIndex - 1;
		nextPage = (pageIndex >= pageTotal - 1) ? pageTotal - 1 : pageIndex + 1;
		step = stepValue;
		startPage = (pageIndex / step) * step;
		endPage = (startPage + step >= pageTotal) ? pageTotal - 1 : startPage
				+ step;
	}

	/**
	 * 
	 */
	public T get(int index) {
		return pageItems.get(index);
	}

	/**
	 * @return the list of items for this page
	 */
	public List<T> getPageItems() {
		return pageItems;
	}

	/**
	 * @return total count of items
	 */
	public int getTotalItemCount() {
		return totalItemCount;
	}

	/**
	 * @return total count of pages
	 */
	public int getTotalPageCount() {
		return getPageTotal();
	}

	/**
	 * @return Returns the pageTotal.
	 */
	public int getPageTotal() {
		return pageTotal;
	}

	/**
	 * 
	 */
	public int getFirstPageNo() {
		return 0;
	}

	/**
	 * 
	 */
	public int getLastPageNo() {
		return pageTotal - 1;
	}

	/**
	 * @return true if this is the first page
	 */
	public boolean isFirstPage() {
		return isFirstPage(getPageIndex());
	}

	/**
	 * @return true if this is the last page
	 */
	public boolean isLastPage() {
		return isLastPage(getPageIndex());
	}

	/**
	 * @param page
	 * @return true if the page is the first page
	 */
	public boolean isFirstPage(int page) {
		return page <= 0;
	}

	/**
	 * @param page
	 * @return true if the page is the last page
	 */
	public boolean isLastPage(int page) {
		return page >= getTotalPageCount() - 1;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the thisPageTotal.
	 */
	public int getThisPageTotal() {
		return thisPageTotal;
	}

	/**
	 * @return step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * @return startPage
	 */
	public int getStartPage() {
		return startPage;
	}

	/**
	 * @return endPage
	 */
	public int getEndPage() {
		return endPage;
	}

	/**
	 * @return prevPage
	 */
	public int getPrevPage() {
		return prevPage;
	}

	/**
	 * @return nextPage
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagedList [pageIndex=").append(pageIndex);
		builder.append(", total=").append(totalItemCount);
		builder.append(", thisPageTotal=").append(thisPageTotal);
		if (pageItems != null) {
			builder.append("; pageItems=").append(pageItems);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public int size() {
		return (pageItems == null) ? 0 : pageItems.size();
	}

	/**
	 * 
	 * 
	 */
	protected int getStartIndex() {
		return ((pageIndex > 1 ? pageIndex : 1) - 1) * this.pageSize + 1;
	}

	/**
	 * 
	 */
	protected void setPageItems(List<T> pageItems) {
		this.pageItems = pageItems;
	}
	

}
