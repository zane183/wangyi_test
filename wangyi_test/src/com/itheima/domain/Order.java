package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order implements Serializable {
	private String num;//订单号
	private int quantity;
	private float price;
	private Integer status;//0未付款 1已付款 2已发货
	private Date gentime;//生成日期
	//多对一的关系
	private Customer customer;
	private Set<OrderItem> items = new HashSet<OrderItem>(0);
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<OrderItem> getItems() {
		return items;
	}
	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}
	public Date getGentime() {
		return gentime;
	}
	public void setGentime(Date gentime) {
		this.gentime = gentime;
	}
	
}
