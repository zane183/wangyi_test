package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.BookDao;
import com.itheima.domain.Book;
import com.itheima.domain.Category;
import com.itheima.util.C3P0Util;

public class BookDaoImpl implements BookDao {
	private QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
	public void save(Book book) {
		try {
			qr.update("insert into books (name,author,price,path,filename,description,categoryId) values (?,?,?,?,?,?,?)", 
					book.getName(),book.getAuthor(),book.getPrice(),book.getPath(),book.getFilename(),
					book.getDescription(),book.getCategory()==null?null:book.getCategory().getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Book findBookById(Integer bookId) {
		try {
			Book book = qr.query("select * from books where id=?", new BeanHandler<Book>(Book.class), bookId);
			if(book!=null){
				Category category = qr.query("select * from categories where id=(select categoryId from books where id=?)", new BeanHandler<Category>(Category.class), bookId);
				book.setCategory(category);
			}
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int getTotalBookRecords() {
		try {
			Long count = (Long)qr.query("select count(*) from books", new ScalarHandler(1));
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Book> findPageBook(int startIndex, int pageSize) {
		try {
			List<Book> books = qr.query("select * from books limit ?,?", new BeanListHandler<Book>(Book.class), startIndex,pageSize);
			if(books!=null&&books.size()>0){
				for(Book book:books){
					Category category = qr.query("select * from categories where id=(select categoryId from books where id=?)", new BeanHandler<Category>(Category.class), book.getId());
					book.setCategory(category);
				}
			}
			return books;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int getTotalBookRecords(String categoryId) {
		try {
			Long count = (Long)qr.query("select count(*) from books where categoryId=?", new ScalarHandler(1),categoryId);
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Book> findPageBook(int startIndex, int pageSize,
			String categoryId) {
		try {
			List<Book> books = qr.query("select * from books where categoryId=? limit ?,?", new BeanListHandler<Book>(Book.class), categoryId,startIndex,pageSize);
			if(books!=null&&books.size()>0){
				for(Book book:books){
					Category category = qr.query("select * from categories where id=(select categoryId from books where id=?)", new BeanHandler<Category>(Category.class), book.getId());
					book.setCategory(category);
				}
			}
			return books;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
