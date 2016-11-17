package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Book;

public interface BookDao {

	void save(Book book);
	/**
	 * ���鼮��Ӧ�ķ���Ҳ��ѯ����
	 * @param bookId
	 * @return
	 */
	Book findBookById(Integer bookId);
	/**
	 * �鼮��������
	 * @return
	 */
	int getTotalBookRecords();
	/**
	 * ��ҳ��ѯ�鼮�����鼮��Ӧ�ķ����ѯ����
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<Book> findPageBook(int startIndex, int pageSize);
	int getTotalBookRecords(String categoryId);
	List<Book> findPageBook(int startIndex, int pageSize, String categoryId);

}
