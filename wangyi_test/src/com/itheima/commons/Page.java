package com.itheima.commons;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {
	private List records;//分页记录
	private int pageSize = 3;//每页显示的记录条数
	private int currentPageNum;//当前页码
	private int totalRecords;//总记录条数
	private int totalPageSize;//总页数
	private int startIndex;//每页开始记录的索引
	
	private int nextPageNum;
	private int prePageNum;
	
	private String uri;//查询分页信息的访问uri
	
	public Page(int currentPageNum,int totalRecords){
		this.currentPageNum = currentPageNum;
		this.totalRecords = totalRecords;
		//计算总页数
		totalPageSize = totalRecords%pageSize==0?totalRecords/pageSize:(totalRecords/pageSize+1);
		//计算开始记录的索引
		startIndex = (currentPageNum-1)*pageSize;
		//计算下一页
		nextPageNum = currentPageNum+1>totalPageSize?totalPageSize:currentPageNum+1;
		prePageNum = currentPageNum-1<1?1:currentPageNum-1;
	}

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPageSize() {
		return totalPageSize;
	}

	public void setTotalPageSize(int totalPageSize) {
		this.totalPageSize = totalPageSize;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getPrePageNum() {
		return prePageNum;
	}

	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
