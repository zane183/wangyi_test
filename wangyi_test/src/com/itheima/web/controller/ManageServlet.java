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
		String op = request.getParameter("op");//�û��ύʲô���Ĳ���
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
	//��ѯ���ж���
	private void listOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String pagenum = request.getParameter("pagenum");
		Page page = s.findPageOrder(pagenum);
		page.setUri("/manage/ManageServlet?op=listOrders");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listOrders.jsp").forward(request, response);
	}
	//�����鼮����ҳ
	private void listBooks(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");//�û�Ҫ�鿴��ҳ��
		Page page = s.findBookPageRecords(pagenum);
		page.setUri("/manage/ManageServlet?op=listBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
	}
	//����鼮���ļ��ϴ�
	private void addBook(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			throw new RuntimeException("��ı���enctype����ȡֵ������multipart/form-data");
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
				//��ͨ�ֶ�
				processFormField(item,book);
			}else{
				//�ϴ��ֶ�
				processUploadField(item,book);
			}
		}
		//�����鼮
		s.addBook(book);
		request.setAttribute("message", "����鼮�ɹ�");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}
	private void processUploadField(FileItem item, Book book) {
		//�õ�����ļ��ĸ�Ŀ¼��/images
		String storeDirectory = getServletContext().getRealPath("/images");
		String childDirectory = makeChildDirectory(storeDirectory);
		
		book.setPath(childDirectory);//����·��
		
		//ȥ��ԭ�������ļ��������ó�һ��uuid���ļ���
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
			String fieldName = item.getFieldName();//����������name��book������������һ�£�name author price description
			String fieldValue = item.getString("UTF-8");
			//������������
			if("categoryId".equals(fieldName)){
				//���ࣺ����������Ϣ
				Category category = s.findCategoryById(Integer.parseInt(fieldValue));
				book.setCategory(category);
			}else{
				//��������
				BeanUtils.setProperty(book, fieldName, fieldValue);//�൱�ڵ���book�����setXXX(fieldvalue)
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//����鼮ҳ�棬��ѯ���з���
	private void addBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAllCategories();
		if(cs==null||cs.size()==0){
			//û�з��࣬��ʾ��ӷ���
			request.setAttribute("message", "������ӷ��࣬<a href='"+request.getContextPath()+"/manage/addCategory.jsp'>��ӷ���</a>");
			request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
			return;
		}
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}
	//���з����б�
	private void listCategories(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/listCategories.jsp").forward(request, response);
	}
	//��ӷ���
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//��װ����
		Category c = FillBeanUtil.fillBean(request, Category.class);
		s.addCategory(c);
		
		request.setAttribute("message", "��ӷ���ɹ�");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}
	//�����������Ƿ����
	private void checkCategoryName(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		String name = request.getParameter("name");
		boolean exist = s.categoryExist(name);
//		if(exist){
//			//����
//			response.getWriter().write("<font color='red'>�������Ѿ�������</font>");
//		}else{
//			response.getWriter().write("<font color='green'>�����ƿ���ʹ��</font>");
//		}
		response.getWriter().write(exist+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
