package com.itheima.web.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.itheima.domain.Book;
//���ﳵ
public class Cart implements Serializable {
	//key:�������Ӧ�鼮��id��һ�ι��������һ���Ӧ ��һ����
	private Map<Integer, CartItem> items = new HashMap<Integer, CartItem>(0);
	private int totalQuantity;//�����ܼ�
	private float totalPrice;//����ܼơ�Ӧ����
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
	
	//����鼮�����ﳵ��
	public void addBook(Book book){
		//�ж�items����û�и����Ӧ�Ĺ�����
		//�У�������1
		if(items.containsKey(book.getId())){
			CartItem item = items.get(book.getId());
			item.setQuantity(item.getQuantity()+1);
		}else{
		//û�У�����һ���µĹ����������1
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
