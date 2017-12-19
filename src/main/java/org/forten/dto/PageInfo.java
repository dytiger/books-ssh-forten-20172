package org.forten.dto;

public class PageInfo {
	private int pageNo;// 页码
	private int pageSize;// 页容量
	private int amount;// 符合条件的数据总量
	private int first;// 当前页第一条数据的序号（从0开始）
	private int last;// 当前页最后一条数据的序号
	private int pageAmount;// 总页数
	private boolean firstPage;// 是否为第一页
	private boolean lastPage;// 是否为最后一页

	private PageInfo(int pageNo, int pageSize, int amount) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.amount = amount;
		this.firstPage = false;
		this.lastPage = false;
	}

	public static PageInfo getInstance(int pageNo, int pageSize, int amount) {
		PageInfo page = new PageInfo(pageNo, pageSize, amount);

		// 计算总页数
		if ((amount % pageSize) == 0) {
			page.pageAmount = (amount / pageSize);
		} else {
			page.pageAmount = (amount / pageSize) + 1;
		}

		// 如果用户输入的页码大于总页数，要在分页对象中将其值设置为与总页数一致
		if (pageNo > page.pageAmount) {
			page.pageNo = page.pageAmount;
		}
		// 如果用户输入的页码小于1，要在分页对象中将其值设置为1
		if (pageNo < 1) {
			page.pageNo = 1;
		}

		// 判断当前页是否是第一页或最后一页
		if (page.pageNo == 1) {
			page.firstPage = true;
		}
		if (page.pageNo == page.pageAmount) {
			page.lastPage = true;
		}

		// 计算当前页第一条数据的序号
		page.first = page.pageSize * (page.pageNo - 1);

		// 计算当前页最后一条数据的序号
		if (page.lastPage) {
			page.last = page.amount;
		} else {
			page.last = page.first + page.pageSize;
		}

		return page;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getAmount() {
		return amount;
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}

	public int getPageAmount() {
		return pageAmount;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	@Override
	public String toString() {
		return "PageInfo [pageNo=" + pageNo + ", pageSize=" + pageSize + ", amount=" + amount + ", first=" + first
				+ ", last=" + last + ", pageAmount=" + pageAmount + ", firstPage=" + firstPage + ", lastPage="
				+ lastPage + "]";
	}
}
