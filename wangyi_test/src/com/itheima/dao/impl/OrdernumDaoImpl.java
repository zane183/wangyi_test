package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.OrdernumDao;
import com.itheima.domain.Ordernum;
import com.itheima.util.C3P0Util;

public class OrdernumDaoImpl implements OrdernumDao {
	private QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
	public Ordernum find(String prefix) {
		try {
			return qr.query("select * from ordernum where prefix=?", new BeanHandler<Ordernum>(Ordernum.class), prefix);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Ordernum ordernum) {
		try {
			qr.update("update ordernum set count=? where prefix=?", ordernum.getCount(),ordernum.getPrefix());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void save(Ordernum ordernum) {
		try {
			qr.update("insert into ordernum values (?,?)",ordernum.getPrefix(), ordernum.getCount());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
