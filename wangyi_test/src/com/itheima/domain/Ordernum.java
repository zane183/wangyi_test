package com.itheima.domain;

import java.io.Serializable;
//�������
public class Ordernum implements Serializable {
	private String prefix;//���ڸ�ʽ��ǰ׺
	private int count;//����
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
