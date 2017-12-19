package org.forten.dto;

import java.util.ArrayList;
import java.util.Collection;

public class PagedRo<T> {
	private Collection<T> data;
	private PageInfo page;

	public PagedRo() {
		super();
		this.data = new ArrayList<>(0);
	}

	public PagedRo(Collection<T> data, PageInfo page) {
		super();
		this.data = data;
		this.page = page;
	}

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public PageInfo getPage() {
		return page;
	}

	public void setPage(PageInfo page) {
		this.page = page;
	}

	public boolean isEmptyData() {
		return this.data == null || this.data.size() == 0;
	}
}
