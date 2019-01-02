package com.youhe.utils.page;

public class PageParameter<T> {
	private  T data;
	private 	int pageNow;
	private 	int pageSize;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getPageNow() {
		return pageNow;
	}
	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
	
	
	
}
