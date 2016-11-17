package com.itheima.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.itheima.commons.Page;
import com.itheima.domain.Book;
import com.itheima.domain.Category;
import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;
import com.itheima.util.FillBeanUtil;

public class ManageServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");//用户提交什么样的操作
		if("checkCategoryName".equals(op)){
			checkCategoryName(request,response);
		}else if("addCategory".equals(op)){
			addCategory(request,response);
		}else if("listCategories".equals(op)){
			listCategories(request,response);
		}else if("addBookUI".equals(op)){
			addBookUI(request,response);
		}else if("addBook".equals(op)){
			addBook(request,response);
		}else if("listBooks".equals(op)){
			listBooks(request,response);
		}else if("listOrders".equals(op)){
			listOrders(request,response);
		}
	}
	//查询所有订单
	private void listOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String pagenum = request.getParameter("pagenum");
		Page page = s.findPageOrder(pagenum);
		page.setUri("/manage/ManageServlet?op=listOrders");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listOrders.jsp").forward(request, response);
	}
	//罗列书籍：分页
	private void listBooks(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");//用户要查看的页码
		Page page = s.findBookPageRecords(pagenum);
		page.setUri("/manage/ManageServlet?op=listBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
	}
	//添加书籍：文件上传
	private void addBook(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			throw new RuntimeException("你的表单的enctype属性取值必须是multipart/form-data");
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items = new ArrayList<FileItem>(0);
		try {
			items = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			throw new RuntimeException(e);
		}
		Book book = new Book();
		for(FileItem item:items){
			if(item.isFormField()){
				//普通字段
				processFormField(item,book);
			}else{
				//上传字段
				processUploadField(item,book);
			}
		}
		//保存书籍
		s.addBook(book);
		request.setAttribute("message", "添加书籍成功");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}
	private void processUploadField(FileItem item, Book book) {
		//得到存放文件的根目录：/images
		String storeDirectory = getServletContext().getRealPath("/images");
		String childDirectory = makeChildDirectory(storeDirectory);
		
		book.setPath(childDirectory);//设置路径
		
		//去掉原来的主文件名，设置成一个uuid的文件名
		String filename = UUID.randomUUID()+"."+FilenameUtils.getExtension(item.getName());
		book.setFilename(filename);
		
		try {
			item.write(new File(storeDirectory+File.separator+childDirectory, filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String makeChildDirectory(String storeDirectory) {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String childDir = df.format(now);
		File f = new File(storeDirectory,childDir);
		if(!f.exists())
			f.mkdirs();
		return childDir;
	}
	private void processFormField(FileItem item, Book book) {
		try {
			String fieldName = item.getFieldName();//表单的输入域name和book的属性名保持一致：name author price description
			String fieldValue = item.getString("UTF-8");
			//单独关联分类
			if("categoryId".equals(fieldName)){
				//分类：关联分类信息
				Category category = s.findCategoryById(Integer.parseInt(fieldValue));
				book.setCategory(category);
			}else{
				//其他属性
				BeanUtils.setProperty(book, fieldName, fieldValue);//相当于调用book对象的setXXX(fieldvalue)
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//添加书籍页面，查询所有分类
	private void addBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAllCategories();
		if(cs==null||cs.size()==0){
			//没有分类，提示添加分类
			request.setAttribute("message", "请先添加分类，<a href='"+request.getContextPath()+"/manage/addCategory.jsp'>添加分类</a>");
			request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
			return;
		}
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}
	//所有分类列表
	private void listCategories(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/listCategories.jsp").forward(request, response);
	}
	//添加分类
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//封装数据
		Category c = FillBeanUtil.fillBean(request, Category.class);
		s.addCategory(c);
		
		request.setAttribute("message", "添加分类成功");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}
	//检查分类名称是否可用
	private void checkCategoryName(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		String name = request.getParameter("name");
		boolean exist = s.categoryExist(name);
//		if(exist){
//			//存在
//			response.getWriter().write("<font color='red'>该名称已经存在了</font>");
//		}else{
//			response.getWriter().write("<font color='green'>该名称可以使用</font>");
//		}
		response.getWriter().write(exist+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
