package com.itheima.domain;

import java.io.Serializable;
//订单编号
public class Ordernum implements Serializable {
	private String prefix;//日期格式的前缀
	private int count;//计数
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
