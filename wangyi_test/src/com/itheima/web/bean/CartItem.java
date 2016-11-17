package com.itheima.web.bean;

import java.io.Serializable;

import com.itheima.domain.Book;

public class CartItem implements Serializable {
	private Book book;//购物项对应的书籍
	private int quantity;//该项购买的商品数量
	private float subtotal;//该项金额小计
	
	public CartItem(Book book){
		this.book = book;
	}
	public float getSubtotal() {
		return book.getPrice()*quantity;
	}
	public void setSubtotal(float subtotal) {//打折
		this.subtotal = subtotal;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
