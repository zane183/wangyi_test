package com.itheima.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Order;
import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;
import com.itheima.util.PaymentUtil;
//����֧�����
public class PayResultServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String p1_MerId=request.getParameter("p1_MerId");
		String r0_Cmd=request.getParameter("r0_Cmd");
		String r1_Code=request.getParameter("r1_Code");//֧�������1����ɹ�
		String r2_TrxId=request.getParameter("r2_TrxId");
		String r3_Amt=request.getParameter("r3_Amt");
		String r4_Cur=request.getParameter("r4_Cur");
		String r5_Pid=request.getParameter("r5_Pid");
		String r6_Order=request.getParameter("r6_Order");//�������
		String r7_Uid=request.getParameter("r7_Uid");
		String r8_MP=request.getParameter("r8_MP");
		String r9_BType=request.getParameter("r9_BType");//1��������� 2��Ե�ͨ��
		String hmac=request.getParameter("hmac");
		//��֤����
		boolean b = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		if(b){
			//����ok��
			if("1".equals(r1_Code)){
				//�ɹ�
				//TODO ���Ķ���״̬:ע������ظ��ύ����
				Order order = s.findOrderByNum(r6_Order);
				order.setStatus(1);
				s.updateOrder(order);
				
				//����������success
				if("2".equals(r9_BType)){
					response.getWriter().write("success");
				}
				request.setAttribute("message", "<h3>֧���ɹ�</h3>");
				request.getRequestDispatcher("/message.jsp").forward(request, response);
			}else{
				response.getWriter().write("֧��ʧ��");
			}
		}else{
			response.getWriter().write("���з��ص������п��ܱ��۸��ˣ�����ϵ���ǵĿͷ���ѯ֧��������绰��12332112344567");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
