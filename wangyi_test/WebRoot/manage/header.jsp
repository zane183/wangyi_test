<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>后台管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">

  </head>
  
  <body>
    <h1>书店后台管理</h1>
    <br/><br/>
    <a href="${pageContext.request.contextPath}/manage/addCategory.jsp">添加分类</a>&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/manage/ManageServlet?op=listCategories">查询分类</a>&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/manage/ManageServlet?op=addBookUI">添加书籍</a>&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/manage/ManageServlet?op=listBooks">查询书籍</a>&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/manage/ManageServlet?op=listOrders">订单管理</a>&nbsp;&nbsp;
	<hr/>