package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import com.itheima.util.C3P0Util;

public class CustomerDaoImpl implements CustomerDao {
	private QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
	public void save(Customer customer) {
		try {
			qr.update("insert into customers (username,password,phonenum,address,email) values (?,?,?,?,?)", 
					customer.getUsername(),customer.getPassword(),customer.getPhonenum(),
					customer.getAddress(),customer.getEmail());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Customer findById(String customerId) {
		try {
			return qr.query("select * from customers where id=?", new BeanHandler<Customer>(Customer.class), customerId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Customer find(String username, String password) {
		try {
			return qr.query("select * from customers where username=? and password=?", new BeanHandler<Customer>(Customer.class), username,password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
