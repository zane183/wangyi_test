package com.itheima.web.bean;

import java.io.Serializable;

import com.itheima.domain.Book;

public class CartItem implements Serializable {
	private Book book;//�������Ӧ���鼮
	private int quantity;//��������Ʒ����
	private float subtotal;//������С��
	
	public CartItem(Book book){
		this.book = book;
	}
	public float getSubtotal() {
		return book.getPrice()*quantity;
	}
	public void setSubtotal(float subtotal) {//����
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
