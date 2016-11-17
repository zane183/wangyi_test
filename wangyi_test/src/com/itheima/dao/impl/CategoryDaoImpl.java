package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.util.C3P0Util;

public class CategoryDaoImpl implements CategoryDao {
	private QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
	public void save(Category category) {
		try {
			qr.update("insert into categories (name,description) values (?,?)", category.getName(),category.getDescription());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean findByName(String categoryName) {
		try {
			Category c = qr.query("select * from categories where name=?", new BeanHandler<Category>(Category.class), categoryName);
			return c==null?false:true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Category> findAll() {
		try {
			return qr.query("select * from categories", new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Category findById(Integer categoryId) {
		try {
			return qr.query("select * from categories where id=?", new BeanHandler<Category>(Category.class),categoryId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
