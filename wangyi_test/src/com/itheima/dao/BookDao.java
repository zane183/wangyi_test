package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Book;

public interface BookDao {

	void save(Book book);
	/**
	 * 把书籍对应的分类也查询出来
	 * @param bookId
	 * @return
	 */
	Book findBookById(Integer bookId);
	/**
	 * 书籍的总数量
	 * @return
	 */
	int getTotalBookRecords();
	/**
	 * 分页查询书籍：把书籍对应的分类查询出来
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<Book> findPageBook(int startIndex, int pageSize);
	int getTotalBookRecords(String categoryId);
	List<Book> findPageBook(int startIndex, int pageSize, String categoryId);

}
