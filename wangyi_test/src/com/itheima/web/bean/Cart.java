package com.itheima.web.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.itheima.domain.Book;
//购物车
public class Cart implements Serializable {
	//key:购物项对应书籍的id。一次购物过程中一项对应 的一本书
	private Map<Integer, CartItem> items = new HashMap<Integer, CartItem>(0);
	private int totalQuantity;//数量总计
	private float totalPrice;//金额总计。应付款
	public Map<Integer, CartItem> getItems() {
		return items;
	}
	public int getTotalQuantity() {
		totalQuantity = 0;
		for(Map.Entry<Integer, CartItem> me:items.entrySet()){
			totalQuantity+=me.getValue().getQuantity();
		}
		return totalQuantity;
	}
	public float getTotalPrice() {
		totalPrice = 0;
		for(Map.Entry<Integer, CartItem> me:items.entrySet()){
			totalPrice+=me.getValue().getSubtotal();
		}
		return totalPrice;
	}
	
	//添加书籍到购物车中
	public void addBook(Book book){
		//判断items中有没有该书对应的购物项
		//有：数量加1
		if(items.containsKey(book.getId())){
			CartItem item = items.get(book.getId());
			item.setQuantity(item.getQuantity()+1);
		}else{
		//没有：创建一个新的购物项，数量是1
			CartItem item = new CartItem(book);
			item.setQuantity(1);
			items.put(book.getId(), item);
		}
	}
	public void removeBook(Integer bookId){
		items.remove(bookId);
	}
	
	
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
