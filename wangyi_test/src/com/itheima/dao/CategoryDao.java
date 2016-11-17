package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Category;

public interface CategoryDao {

	void save(Category category);
	/**
	 * ���ڷ���true
	 * @param categoryName
	 * @return
	 */
	boolean findByName(String categoryName);

	List<Category> findAll();

	Category findById(Integer categoryId);

}
