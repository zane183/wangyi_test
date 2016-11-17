package com.itheima.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.dsna.util.images.ValidateCode;

import com.itheima.commons.Page;
import com.itheima.domain.Book;
import com.itheima.domain.Category;
import com.itheima.domain.Customer;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;
import com.itheima.util.FillBeanUtil;
import com.itheima.util.OrdernumGenertor;
import com.itheima.web.bean.Cart;
import com.itheima.web.bean.CartItem;

public class CilentServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("listBooks".equals(op)){
			listBooks(request,response);
		}else if("listBooksByCategoryId".equals(op)){
			listBooksByCategoryId(request,response);
		}else if("buy".equals(op)){
			buy(request,response);
		}else if("changeNum".equals(op)){
			changeNum(request,response);
		}else if("delOneItem".equals(op)){
			delOneItem(request,response);
		}else if("delAllItem".equals(op)){
			delAllItem(request,response);
		}else if("regist".equals(op)){
			regist(request,response);
		}else if("genCaptcha".equals(op)){
			genCaptcha(request,response);
		}else if("login".equals(op)){
			login(request,response);
		}else if("logout".equals(op)){
			logout(request,response);
		}else if("genOrder".equals(op)){
			genOrder(request,response);
		}else if("showOrders".equals(op)){
			showOrders(request,response);
		}else if("pay".equals(op)){
			pay(request,response);
		}else if("showDetails".equals(op)){
			showDetails(request,response);
		}
	}
	//���ݶ����Ų�ѯ��������
	private void showDetails(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException{
		String ordernum = request.getParameter("ordernum");
		List<OrderItem> items = s.findOrderItemsByOrderNum(ordernum);
		request.setAttribute("items", items);
		request.setAttribute("ordernum", ordernum);//�洢�������
		request.getRequestDispatcher("/orderDetails.jsp").forward(request, response);
	}
	//ȥ֧��:�Ӳ鿴�Լ��Ķ�����
	private void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ordernum = request.getParameter("ordernum");
		Order order = s.findOrderByNum(ordernum);
		//ҳ��Ҫת��֧��ҳ��
		request.setAttribute("order", order);
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}
	//��ѯ�ͻ��Լ��Ķ���
	private void showOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//��֤�Ƿ��½��û�е�½ȥ��½
		HttpSession session = request.getSession();
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer==null){
			request.setAttribute("message", "����û�е�½���������½.<a href='"+request.getContextPath()+"/login.jsp'>��½</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//�鿴�Լ��Ķ���
		List<Order> orders = s.findOrdersByCustomer(customer);
		request.setAttribute("os", orders);
		request.getRequestDispatcher("/showOrders.jsp").forward(request, response);
	}
	//���ɶ���
	private void genOrder(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException {
		//��֤�Ƿ��½��û�е�½ȥ��½
		HttpSession session = request.getSession();
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer==null){
			request.setAttribute("message", "����û�е�½���������½.<a href='"+request.getContextPath()+"/login.jsp'>��½</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		// ���ɶ������ѹ��ﳵ�͹�����--------->�����Ͷ�����-------------->���ݿ���
		Cart cart = (Cart) session.getAttribute("cart");
		//�ѹ��ﳵ�͹�����--------->�����Ͷ�����
		Order order = new Order();
		order.setNum(OrdernumGenertor.genOrdernum());
		order.setCustomer(customer);
		order.setPrice(cart.getTotalPrice());
		order.setQuantity(cart.getTotalQuantity());
		order.setStatus(0);
		//�õ����еĹ�����--------->������Ͷ�������������ϵ
		for(Map.Entry<Integer, CartItem> me:cart.getItems().entrySet()){
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(me.getValue().getSubtotal());
			orderItem.setQuantity(me.getValue().getQuantity());
			orderItem.setBook(me.getValue().getBook());
			order.getItems().add(orderItem);
		}
		//���涩��
		s.genOrder(order);
		//ҳ��Ҫת��֧��ҳ��
		request.setAttribute("order", order);
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
		
	}
	//ע��
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("customer");
		response.sendRedirect(request.getContextPath());
	}
	//�û���½
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//��֤��֤��
		String scaptcha = (String) session.getAttribute("captcha");
		String fcaptcha = request.getParameter("captcha");
		if(!fcaptcha.equalsIgnoreCase(scaptcha)){
			request.setAttribute("message", "��֤����Ч�����ʹ��������µ�½.<a href='"+request.getContextPath()+"/login.jsp'>���µ�½</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//��֤����ȷ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Customer customer = s.customerLogin(username, password);
		if(customer==null){
			request.setAttribute("message", "������û��������룬���ʹ��������µ�½.<a href='"+request.getContextPath()+"/login.jsp'>���µ�½</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//��ȷ
		session.setAttribute("customer", customer);
		response.sendRedirect(request.getContextPath());
	}
	//����CAPTCHAͼ��
	private void genCaptcha(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ValidateCode vc = new ValidateCode(100, 28, 4, 9);
		//���浽HttpSession��
		String captcha = vc.getCode();
		request.getSession().setAttribute("captcha", captcha);
		//�����������
		vc.write(response.getOutputStream());
	}
	//�¿ͻ�ע��
	private void regist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Customer customer = FillBeanUtil.fillBean(request, Customer.class);
		s.regist(customer);
		request.setAttribute("message", "ע��ɹ���.<a href='"+request.getContextPath()+"'>��ҳ</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	private void delAllItem(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		request.getSession().removeAttribute("cart");
		//�ض���showCartҳ��
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//ɾ��һ��������
	private void delOneItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.getItems().remove(Integer.parseInt(bookId));
		//�ض���showCartҳ��
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//�޸Ĺ����������
	private void changeNum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		String num = request.getParameter("num");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		CartItem item = cart.getItems().get(Integer.parseInt(bookId));
		item.setQuantity(Integer.parseInt(num));
		//�ض���showCartҳ��
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//���鼮���뵽���ﳵ
	private void buy(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(Integer.parseInt(bookId));
		//���ﳵ
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");//���ﳵ�ڻỰ��Χ�еı��
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		//�϶��й��ﳵ
		cart.addBook(book);
		//��ʾ
		request.setAttribute("message", "��Ʒ�Ѿ��������Ĺ��ﳵ.<a href='javascript:history.back()'>��������</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	//���շ����ѯ��ҳ����
	private void listBooksByCategoryId(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		// ��ѯ���з���
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		//��ѯ��ҳ����
		String pagenum = request.getParameter("pagenum");
		String categoryId = request.getParameter("categoryId");
		Page page = s.findBookPageRecords(pagenum,categoryId);
		page.setUri("/client/CilentServlet?op=listBooksByCategoryId&categoryId="+categoryId);
		request.setAttribute("page", page);
		// ת����һ��ҳ��
		request.getRequestDispatcher("/books.jsp").forward(request, response);
	}
	//��ʾĬ����ҳ������
	private void listBooks(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		// ��ѯ���з���
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		//��ѯ��ҳ����
		String pagenum = request.getParameter("pagenum");
		Page page = s.findBookPageRecords(pagenum);
		page.setUri("/client/CilentServlet?op=listBooks");
		request.setAttribute("page", page);
		// ת����һ��ҳ��
		request.getRequestDispatcher("/books.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
