package com.itheima.service.impl;

import java.util.List;

import com.itheima.commons.Page;
import com.itheima.dao.BookDao;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.CustomerDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrdernumDao;
import com.itheima.dao.impl.BookDaoImpl;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.dao.impl.CustomerDaoImpl;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.dao.impl.OrdernumDaoImpl;
import com.itheima.domain.Book;
import com.itheima.domain.Category;
import com.itheima.domain.Customer;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Ordernum;
import com.itheima.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
	private CategoryDao categoryDao = new CategoryDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	private CustomerDao customerDao = new CustomerDaoImpl();
	private OrdernumDao ordernumDao = new OrdernumDaoImpl();
	private OrderDao orderDao = new OrderDaoImpl();
	public void addCategory(Category category) {
		if(category==null)
			throw new IllegalArgumentException("category can not be null");
		categoryDao.save(category);
	}

	public boolean categoryExist(String categoryName) {
		return categoryDao.findByName(categoryName);
	}

	public List<Category> findAllCategories() {
		return categoryDao.findAll();
	}

	public Category findCategoryById(Integer categoryId) {
		return categoryDao.findById(categoryId);
	}

	public void addBook(Book book) {
		if(book==null)
			throw new IllegalArgumentException("book can not be null");
		if(book.getCategory()==null)
			throw new IllegalArgumentException("添加的书籍必须指定分类");
		bookDao.save(book);
	}

	public Book findBookById(Integer bookId) {
		return bookDao.findBookById(bookId);
	}

	public Page findBookPageRecords(String pagenum) {
		int currentPageNum = 1;
		if(pagenum!=null&&!pagenum.trim().equals("")){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.getTotalBookRecords();
		Page page = new Page(currentPageNum, totalRecords);
		List<Book> records = bookDao.findPageBook(page.getStartIndex(),page.getPageSize());
		page.setRecords(records);
		return page;
	}

	public Page findBookPageRecords(String pagenum, String categoryId) {
		int currentPageNum = 1;
		if(pagenum!=null&&!pagenum.trim().equals("")){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.getTotalBookRecords(categoryId);
		Page page = new Page(currentPageNum, totalRecords);
		List<Book> records = bookDao.findPageBook(page.getStartIndex(),page.getPageSize(),categoryId);
		page.setRecords(records);
		return page;
	}

	public void regist(Customer customer) {
		if(customer==null)
			throw new IllegalArgumentException("数据不能为null");
		customerDao.save(customer);
	}

	public Customer findCustomerById(String customerId) {
		return customerDao.findById(customerId);
	}

	public Customer customerLogin(String username, String password) {
		return customerDao.find(username,password);
	}

	public Ordernum findOrdernum(String prefix) {
		return ordernumDao.find(prefix);
	}

	public void updateOrdernum(Ordernum ordernum) {
		if(ordernum==null)
			throw new IllegalArgumentException("数据不能为null");
		ordernumDao.update(ordernum);
	}

	public void addOrdernum(Ordernum ordernum) {
		ordernumDao.save(ordernum);
	}

	public void genOrder(Order order) {
		if(order==null)
			throw new IllegalArgumentException("数据不能为null");
		orderDao.save(order);
	}

	public List<Order> findOrdersByCustomer(Customer customer) {
		if(customer==null)
			throw new IllegalArgumentException("数据不能为null");
		List<Order> orders = orderDao.findOrders(customer.getId());
		//关联客户
		for(Order o:orders){
			o.setCustomer(customer);
		}
		return orders;
	}

	public Order findOrderByNum(String ordernum) {
		return orderDao.find(ordernum);
	}

	public List<OrderItem> findOrderItemsByOrderNum(String ordernum) {
		return orderDao.findItems(ordernum);
	}

	public void updateOrder(Order order) {
		orderDao.update(order);
	}

	public Page findPageOrder(String pagenum) {
		int currentPageNum = 1;
		if(pagenum!=null&&!"".equals(pagenum)){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = orderDao.getOrdersCount();
		Page page = new Page(currentPageNum, totalRecords);
		List<Order> orders = orderDao.findPageOrders(page.getStartIndex(),page.getPageSize());
		page.setRecords(orders);
		return page;
	}

}
