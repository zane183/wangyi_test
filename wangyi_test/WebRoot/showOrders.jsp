<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
   	<h2>您的订单如下：</h2>
   	<table border="1" width="738">
   		<tr>
   			<th>订单号</th>
   			<th>下单时间</th>
   			<th>数量</th>
   			<th>金额</th>
   			<th>订单状态</th>
   			<th>操作</th>
   		</tr>
   		<c:forEach items="${os}" var="o" varStatus="vs">
   			<tr class="${vs.index%2==0?'odd':'even' }">
	   			<td>${o.num }</td>
	   			<td>${o.gentime }</td>
	   			<td>${o.quantity }</td>
	   			<td>${o.price }</td>
	   			<td>
	   				<c:choose>
	   					<c:when test="${o.status==0}">
	   						未付款<a href="${pageContext.request.contextPath}/client/CilentServlet?op=pay&ordernum=${o.num}">去付款</a>
	   					</c:when>
	   					<c:when test="${o.status==1}">
	   						已付款
	   					</c:when>
	   					<c:when test="${o.status==2}">
	   						已发货
	   					</c:when>
	   				</c:choose>
	   			</td>
	   			<td>
	   				<a href="${pageContext.request.contextPath}/client/CilentServlet?op=showDetails&ordernum=${o.num}">查看明细</a>
	   				<a href="javascript:alert('略')">取消订单</a>
	   			</td>
	   		</tr>
   		</c:forEach>
   	</table>
  </body>
</html>
