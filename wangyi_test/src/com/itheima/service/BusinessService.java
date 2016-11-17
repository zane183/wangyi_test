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
	 * ��ӷ���
	 * @param category
	 */
	void addCategory(Category category);
	/**
	 * ���������Ƿ����
	 * @param categoryName
	 * @return ���ڷ���true
	 */
	boolean categoryExist(String categoryName); 
	/**
	 * ��ѯ���з���
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * ����id��ѯһ������
	 * @param categoryId
	 * @return û�з���null
	 */
	Category findCategoryById(Integer categoryId);
	/**
	 * ����鼮
	 * @param book �鼮�ķ��಻��Ϊnull
	 */
	void addBook(Book book);
	/**
	 * ����id��ѯ�鼮����Ӧ�ķ���ҲҪ��ѯ����
	 * @param bookId
	 * @return
	 */
	Book findBookById(Integer bookId);
	/**
	 * ��ѯ�鼮�ķ�ҳ��Ϣ
	 * @param pagenum �û�Ҫ����ҳ�룬���Ϊnull��""��Ĭ�Ͽ���1ҳ
	 * @return
	 */
	Page findBookPageRecords(String pagenum);
	/**
	 * ���ݷ����ѯ�鼮�ķ�ҳ��Ϣ
	 * @param pagenum �û�Ҫ����ҳ�룬���Ϊnull��""��Ĭ�Ͽ���1ҳ
	 * @return
	 */
	Page findBookPageRecords(String pagenum,String categoryId);
	/**
	 * �¿ͻ�ע��
	 * @param customer
	 */
	void regist(Customer customer);
	
	Customer findCustomerById(String customerId);
	/**
	 * �ͻ���½
	 * @param username
	 * @param password
	 * @return �û�����������󷵻�null
	 */
	Customer customerLogin(String username,String password);
	/**
	 * ����ǰ׺���Ҷ�Ӧ��countȡֵ
	 * @param prefix
	 * @return
	 */
	Ordernum findOrdernum(String prefix);
	/**
	 * ���¶�������Ϣ
	 * @param ordernum
	 */
	void updateOrdernum(Ordernum ordernum);
	/**
	 * ����һ�¶�������Ϣ
	 * @param ordernum
	 */
	void addOrdernum(Ordernum ordernum);
	/**
	 * ���ɶ���
	 * @param order
	 */
	void genOrder(Order order);
	/**
	 * �鿴�ͻ��Լ��Ķ�����Ĭ�ϰ������ڽ�������
	 * @param customer
	 * @return
	 */
	List<Order> findOrdersByCustomer(Customer customer);
	/**
	 * ���ݶ����Ų�ѯ�������Ѷ�Ӧ�Ŀͻ�Ҳ�����
	 * @param ordernum
	 * @return
	 */
	Order findOrderByNum(String ordernum);
	/**
	 * ���ݶ����Ų�ѯ��������
	 * @param ordernum
	 * @return
	 */
	List<OrderItem> findOrderItemsByOrderNum(String ordernum);
	/**
	 * ���¶���״̬
	 * @param order
	 */
	void updateOrder(Order order);
	/**
	 * ��ѯ���ж����ķ�ҳ��Ϣ
	 * @param pagenum
	 * @return
	 */
	Page findPageOrder(String pagenum);
}
