package com.itheima.commons;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {
	private List records;//��ҳ��¼
	private int pageSize = 3;//ÿҳ��ʾ�ļ�¼����
	private int currentPageNum;//��ǰҳ��
	private int totalRecords;//�ܼ�¼����
	private int totalPageSize;//��ҳ��
	private int startIndex;//ÿҳ��ʼ��¼������
	
	private int nextPageNum;
	private int prePageNum;
	
	private String uri;//��ѯ��ҳ��Ϣ�ķ���uri
	
	public Page(int currentPageNum,int totalRecords){
		this.currentPageNum = currentPageNum;
		this.totalRecords = totalRecords;
		//������ҳ��
		totalPageSize = totalRecords%pageSize==0?totalRecords/pageSize:(totalRecords/pageSize+1);
		//���㿪ʼ��¼������
		startIndex = (currentPageNum-1)*pageSize;
		//������һҳ
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
