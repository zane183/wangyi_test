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
		//��ȡ��ǰ����������
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String prefix = df.format(now);//ǰ׺
		int count = 1;//����
		//ȥ����������ǰ׺��ѯ
		Ordernum ordernum = s.findOrdernum(prefix);
			//�У�ȡ��count����1���Ѽ�1������ݸ������ݿ�
		if(ordernum!=null){
			count = ordernum.getCount()+1;
			ordernum.setCount(count);
			s.updateOrdernum(ordernum);
		}else{
			//û�У�count����1�������¼�¼
			ordernum = new Ordernum();
			ordernum.setPrefix(prefix);
			ordernum.setCount(count);
			s.addOrdernum(ordernum);
		}
		//����count�ĳ��ȣ����ں��汣��8λ���ȡ�
		StringBuffer sb = new StringBuffer(prefix);
		for(int i=0;i<(8-(count+"").length());i++){
			sb.append("0");
		}
		sb.append(count);
		//�������ɵ��ַ���
		return sb.toString();
	}
	public static void main(String[] args) {
		for(int i=0;i<10;i++)
		System.out.println(genOrdernum());
	}
}
