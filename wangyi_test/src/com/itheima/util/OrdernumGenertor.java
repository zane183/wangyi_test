package com.itheima.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itheima.domain.Ordernum;
import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;

public class OrdernumGenertor {
	
	public synchronized static String genOrdernum(){
		BusinessService s = new BusinessServiceImpl();
		//获取当前服务器日期
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String prefix = df.format(now);//前缀
		int count = 1;//计数
		//去服务器根据前缀查询
		Ordernum ordernum = s.findOrdernum(prefix);
			//有：取出count，加1；把加1后的数据更新数据库
		if(ordernum!=null){
			count = ordernum.getCount()+1;
			ordernum.setCount(count);
			s.updateOrdernum(ordernum);
		}else{
			//没有：count就是1；插入新纪录
			ordernum = new Ordernum();
			ordernum.setPrefix(prefix);
			ordernum.setCount(count);
			s.addOrdernum(ordernum);
		}
		//根据count的长度：日期后面保留8位长度。
		StringBuffer sb = new StringBuffer(prefix);
		for(int i=0;i<(8-(count+"").length());i++){
			sb.append("0");
		}
		sb.append(count);
		//返回生成的字符串
		return sb.toString();
	}
	public static void main(String[] args) {
		for(int i=0;i<10;i++)
		System.out.println(genOrdernum());
	}
}
