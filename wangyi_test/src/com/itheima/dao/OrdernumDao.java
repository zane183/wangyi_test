package com.itheima.dao;

import com.itheima.domain.Ordernum;

public interface OrdernumDao {

	Ordernum find(String prefix);

	void update(Ordernum ordernum);

	void save(Ordernum ordernum);

}
