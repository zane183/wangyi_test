<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>默认主页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="refresh" content="0;URL=${pageContext.request.contextPath}/client/CilentServlet?op=listBooks">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<!-- /day23_bookstore/client/CilentServlet?op=listBooks -->
  	<!-- 可能有的学员不能进行转发。 -->
  	<%--
    <jsp:forward page="/client/CilentServlet">
    	<jsp:param value="listBooks" name="op"/>
    </jsp:forward>
     --%>
  </body>
</html>
