<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>XX网店</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/util.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">

  </head>
  
  <body>
    <h1>欢迎光临本小店</h1>
    <br/><br/>
    <a href="${pageContext.request.contextPath}">首页</a>&nbsp;&nbsp;
    <c:if test="${sessionScope.customer==null }">
   	 <a href="${pageContext.request.contextPath}/regist.jsp">注册</a>&nbsp;&nbsp;
   	 <a href="${pageContext.request.contextPath}/login.jsp">登陆</a>&nbsp;&nbsp;
    </c:if>
     <c:if test="${sessionScope.customer!=null }">
     	欢迎您：${sessionScope.customer.username }
     	<a href="${pageContext.request.contextPath}/client/CilentServlet?op=logout">注销</a>&nbsp;&nbsp;
     </c:if>
    <a href="${pageContext.request.contextPath}/client/CilentServlet?op=showOrders">我的订单</a>&nbsp;&nbsp;
    <a href="${pageContext.request.contextPath}/showCart.jsp">我的购物车</a>&nbsp;&nbsp;
	<hr/>