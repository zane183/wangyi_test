package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;

public interface OrderDao {

	void save(Order order);

	List<Order> findOrders(Integer customerId);

	Order find(String ordernum);

	List<OrderItem> findItems(String ordernum);

	void update(Order order);

	int getOrdersCount();

	List<Order> findPageOrders(int startIndex, int pageSize);

}
