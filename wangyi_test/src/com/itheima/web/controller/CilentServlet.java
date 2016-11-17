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
	//根据订单号查询订单详情
	private void showDetails(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException{
		String ordernum = request.getParameter("ordernum");
		List<OrderItem> items = s.findOrderItemsByOrderNum(ordernum);
		request.setAttribute("items", items);
		request.setAttribute("ordernum", ordernum);//存储订单编号
		request.getRequestDispatcher("/orderDetails.jsp").forward(request, response);
	}
	//去支付:从查看自己的订单处
	private void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ordernum = request.getParameter("ordernum");
		Order order = s.findOrderByNum(ordernum);
		//页面要转向：支付页面
		request.setAttribute("order", order);
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}
	//查询客户自己的订单
	private void showOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//验证是否登陆：没有登陆去登陆
		HttpSession session = request.getSession();
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer==null){
			request.setAttribute("message", "您还没有登陆，戳后面登陆.<a href='"+request.getContextPath()+"/login.jsp'>登陆</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//查看自己的订单
		List<Order> orders = s.findOrdersByCustomer(customer);
		request.setAttribute("os", orders);
		request.getRequestDispatcher("/showOrders.jsp").forward(request, response);
	}
	//生成订单
	private void genOrder(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException {
		//验证是否登陆：没有登陆去登陆
		HttpSession session = request.getSession();
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer==null){
			request.setAttribute("message", "您还没有登陆，戳后面登陆.<a href='"+request.getContextPath()+"/login.jsp'>登陆</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		// 生成订单：把购物车和购物项--------->订单和订单项-------------->数据库中
		Cart cart = (Cart) session.getAttribute("cart");
		//把购物车和购物项--------->订单和订单项
		Order order = new Order();
		order.setNum(OrdernumGenertor.genOrdernum());
		order.setCustomer(customer);
		order.setPrice(cart.getTotalPrice());
		order.setQuantity(cart.getTotalQuantity());
		order.setStatus(0);
		//得到所有的购物项--------->订单项。和订单建立关联关系
		for(Map.Entry<Integer, CartItem> me:cart.getItems().entrySet()){
			OrderItem orderItem = new OrderItem();
			orderItem.setPrice(me.getValue().getSubtotal());
			orderItem.setQuantity(me.getValue().getQuantity());
			orderItem.setBook(me.getValue().getBook());
			order.getItems().add(orderItem);
		}
		//保存订单
		s.genOrder(order);
		//页面要转向：支付页面
		request.setAttribute("order", order);
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
		
	}
	//注销
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("customer");
		response.sendRedirect(request.getContextPath());
	}
	//用户登陆
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//验证验证码
		String scaptcha = (String) session.getAttribute("captcha");
		String fcaptcha = request.getParameter("captcha");
		if(!fcaptcha.equalsIgnoreCase(scaptcha)){
			request.setAttribute("message", "验证码无效，请猛戳后面重新登陆.<a href='"+request.getContextPath()+"/login.jsp'>重新登陆</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//验证码正确
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Customer customer = s.customerLogin(username, password);
		if(customer==null){
			request.setAttribute("message", "错误的用户名或密码，请猛戳后面重新登陆.<a href='"+request.getContextPath()+"/login.jsp'>重新登陆</a>");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//正确
		session.setAttribute("customer", customer);
		response.sendRedirect(request.getContextPath());
	}
	//生成CAPTCHA图像
	private void genCaptcha(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ValidateCode vc = new ValidateCode(100, 28, 4, 9);
		//保存到HttpSession中
		String captcha = vc.getCode();
		request.getSession().setAttribute("captcha", captcha);
		//输出到界面上
		vc.write(response.getOutputStream());
	}
	//新客户注册
	private void regist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Customer customer = FillBeanUtil.fillBean(request, Customer.class);
		s.regist(customer);
		request.setAttribute("message", "注册成功！.<a href='"+request.getContextPath()+"'>主页</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	private void delAllItem(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		request.getSession().removeAttribute("cart");
		//重定向到showCart页面
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//删除一个购物项
	private void delOneItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.getItems().remove(Integer.parseInt(bookId));
		//重定向到showCart页面
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//修改购物项的数量
	private void changeNum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		String num = request.getParameter("num");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		CartItem item = cart.getItems().get(Integer.parseInt(bookId));
		item.setQuantity(Integer.parseInt(num));
		//重定向到showCart页面
		response.sendRedirect(request.getContextPath()+"/showCart.jsp");
	}
	//把书籍加入到购物车
	private void buy(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		Book book = s.findBookById(Integer.parseInt(bookId));
		//购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");//购物车在会话范围中的标记
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		//肯定有购物车
		cart.addBook(book);
		//提示
		request.setAttribute("message", "商品已经放入您的购物车.<a href='javascript:history.back()'>继续购物</a>");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	//按照分类查询分页数据
	private void listBooksByCategoryId(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		// 查询所有分类
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		//查询分页数据
		String pagenum = request.getParameter("pagenum");
		String categoryId = request.getParameter("categoryId");
		Page page = s.findBookPageRecords(pagenum,categoryId);
		page.setUri("/client/CilentServlet?op=listBooksByCategoryId&categoryId="+categoryId);
		request.setAttribute("page", page);
		// 转发到一个页面
		request.getRequestDispatcher("/books.jsp").forward(request, response);
	}
	//显示默认主页的内容
	private void listBooks(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		// 查询所有分类
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		//查询分页数据
		String pagenum = request.getParameter("pagenum");
		Page page = s.findBookPageRecords(pagenum);
		page.setUri("/client/CilentServlet?op=listBooks");
		request.setAttribute("page", page);
		// 转发到一个页面
		request.getRequestDispatcher("/books.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
