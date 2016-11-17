<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
   	<h2>订单编号：${ordernum}</h2>
   	订单明细： <br/>
   	<table border="1" width="738">
   		<tr>
   			<th>商品名称</th>
   			<th>数量</th>
   			<th>单价</th>
   			<th>金额</th>
   		</tr>
   		<c:forEach items="${items}" var="i" varStatus="vs">
   			<tr class="${vs.index%2==0?'odd':'even' }">
	   			<td><img src="${pageContext.request.contextPath}/images/${i.book.path}/${i.book.filename}"/>${i.book.name}</td>
	   			<td>${i.quantity }</td>
	   			<td>${i.book.price }</td>
	   			<td>${i.price }</td>
	   			
	   		</tr>
   		</c:forEach>
   	</table>
  </body>
</html>
