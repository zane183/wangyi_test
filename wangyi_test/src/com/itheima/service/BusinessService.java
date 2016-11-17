package com.itheima.service;

import java.util.List;

import com.itheima.commons.Page;
import com.itheima.domain.Book;
import com.itheima.domain.Category;
import com.itheima.domain.Customer;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Ordernum;

public interface BusinessService {
	/**
	 * 添加分类
	 * @param category
	 */
	void addCategory(Category category);
	/**
	 * 分类名称是否存在
	 * @param categoryName
	 * @return 存在返回true
	 */
	boolean categoryExist(String categoryName); 
	/**
	 * 查询所有分类
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * 根据id查询一个分类
	 * @param categoryId
	 * @return 没有返回null
	 */
	Category findCategoryById(Integer categoryId);
	/**
	 * 添加书籍
	 * @param book 书籍的分类不能为null
	 */
	void addBook(Book book);
	/**
	 * 根据id查询书籍，对应的分类也要查询出来
	 * @param bookId
	 * @return
	 */
	Book findBookById(Integer bookId);
	/**
	 * 查询书籍的分页信息
	 * @param pagenum 用户要看的页码，如果为null或""，默认看第1页
	 * @return
	 */
	Page findBookPageRecords(String pagenum);
	/**
	 * 根据分类查询书籍的分页信息
	 * @param pagenum 用户要看的页码，如果为null或""，默认看第1页
	 * @return
	 */
	Page findBookPageRecords(String pagenum,String categoryId);
	/**
	 * 新客户注册
	 * @param customer
	 */
	void regist(Customer customer);
	
	Customer findCustomerById(String customerId);
	/**
	 * 客户登陆
	 * @param username
	 * @param password
	 * @return 用户名或密码错误返回null
	 */
	Customer customerLogin(String username,String password);
	/**
	 * 根据前缀查找对应的count取值
	 * @param prefix
	 * @return
	 */
	Ordernum findOrdernum(String prefix);
	/**
	 * 更新订单号信息
	 * @param ordernum
	 */
	void updateOrdernum(Ordernum ordernum);
	/**
	 * 插入一新订单号信息
	 * @param ordernum
	 */
	void addOrdernum(Ordernum ordernum);
	/**
	 * 生成订单
	 * @param order
	 */
	void genOrder(Order order);
	/**
	 * 查看客户自己的订单。默认按照日期降序排列
	 * @param customer
	 * @return
	 */
	List<Order> findOrdersByCustomer(Customer customer);
	/**
	 * 根据订单号查询订单：把对应的客户也查出来
	 * @param ordernum
	 * @return
	 */
	Order findOrderByNum(String ordernum);
	/**
	 * 根据订单号查询订单详情
	 * @param ordernum
	 * @return
	 */
	List<OrderItem> findOrderItemsByOrderNum(String ordernum);
	/**
	 * 更新订单状态
	 * @param order
	 */
	void updateOrder(Order order);
	/**
	 * 查询所有订单的分页信息
	 * @param pagenum
	 * @return
	 */
	Page findPageOrder(String pagenum);
}
