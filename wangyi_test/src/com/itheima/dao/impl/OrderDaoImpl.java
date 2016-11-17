package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Book;
import com.itheima.domain.Customer;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.util.C3P0Util;

public class OrderDaoImpl implements OrderDao {
	private QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
	public void save(Order order) {
		try {
			//保存订单的基本信息
			qr.update("insert into orders (num,quantity,price,status,customerId,gentime) values (?,?,?,?,?,?)", 
					order.getNum(),order.getQuantity(),order.getPrice(),order.getStatus(),order.getCustomer()==null?null:order.getCustomer().getId(),new Date());
			//判断有无关联的订单项信息
			List<OrderItem> items = new ArrayList<OrderItem>(order.getItems());
			if(items.size()>0){
				Object params[][] = new Object[items.size()][];
				for(int i=0;i<params.length;i++){
					OrderItem item = items.get(i);
					params[i] = new Object[]{item.getQuantity(),item.getPrice(),item.getBook()==null?null:item.getBook().getId(),order.getNum()};
				}
				qr.batch("insert into orderitems (quantity,price,bookId,ordernum) values (?,?,?,?)", params);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Order> findOrders(Integer customerId) {
		try {
			return qr.query("select * from orders where customerId=? order by gentime desc", new BeanListHandler<Order>(Order.class), customerId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Order find(String ordernum) {
		try {
			Order order = qr.query("select * from orders where num=?", new BeanHandler<Order>(Order.class), ordernum);
			if(order!=null){
				//查客户
				Customer customer = qr.query("select * from customers where id=(select customerId from orders where num=?)", new BeanHandler<Customer>(Customer.class), ordernum);
				order.setCustomer(customer);
			}
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<OrderItem> findItems(String ordernum) {
		try {
			List<OrderItem> items = qr.query("select * from orderitems where ordernum=?", new BeanListHandler<OrderItem>(OrderItem.class), ordernum);
			//订单查出来
			Order order = qr.query("select * from orders where num=?", new BeanHandler<Order>(Order.class), ordernum);
			//订单项对应的书也要查询出来
			for(OrderItem item:items){
				item.setOrder(order);//建立和订单的多对一的关联关系
				Book book = qr.query("select * from books where id=(select bookId from orderitems where id=?)", new BeanHandler<Book>(Book.class), item.getId());
				item.setBook(book);//建立和书的多对一的关联关系
			}
			return items;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void update(Order order) {
		try {
			qr.update("update orders set status=?,quantity=?,price=? where num=?", order.getStatus(),order.getQuantity(),order.getPrice(),order.getNum());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public int getOrdersCount() {
		try {
			Long count = (Long)qr.query("select count(*) from orders", new ScalarHandler(1));
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Order> findPageOrders(int startIndex, int pageSize) {
		try {
			List<Order> orders = qr.query("select * from orders limit ?,?", new BeanListHandler<Order>(Order.class), startIndex,pageSize);
			if(orders!=null&&orders.size()>0){
				for(Order o:orders){
					//查客户
					Customer customer = qr.query("select * from customers where id=(select customerId from orders where num=?)", new BeanHandler<Customer>(Customer.class), o.getNum());
					o.setCustomer(customer);
				}
			}
			return orders;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
