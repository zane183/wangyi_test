<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
第${page.currentPageNum}页/共${page.totalPageSize}页&nbsp;&nbsp;
    	<a href="${pageContext.request.contextPath}${page.uri}&pagenum=${page.prePageNum}">上一页</a>
    	<a href="${pageContext.request.contextPath}${page.uri}&pagenum=${page.nextPageNum}">下一页</a>