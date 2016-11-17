<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
   <c:if test="${empty sessionScope.cart.items }"> 
   	<h1>您还没有购买任何商品</h1>
   	<a href="${pageContext.request.contextPath}">去购买</a>
   </c:if>
   <c:if test="${!empty sessionScope.cart.items }"> 
   	<h2>您购买的商品如下</h2>
   	<a href="javascript:delAllItem()">清空购物车</a>
   	<table border="1" width="538">
   		<tr>
   			<th>商品名称</th>
   			<th>单价</th>
   			<th>数量</th>
   			<th>小计</th>
   			<th>操作</th>
   		</tr>
   		<c:forEach items="${sessionScope.cart.items}" var="me" varStatus="vs">
	   		<tr class="${vs.index%2==0?'odd':'even' }">
	   			<td>
	   				<img src="${pageContext.request.contextPath}/images/${me.value.book.path}/${me.value.book.filename}" width="40" height="40"/>
	   				${me.value.book.name}
	   			</td>
	   			<td>${me.value.book.price}</td>
	   			<td><input type="text" id="quantity" value="${me.value.quantity}" size="3" onchange="changeNum(this,${me.key},${me.value.quantity})"/></td>
	   			<td>${me.value.subtotal}</td>
	   			<td>
	   				<a href="javascript:delOneItem('${me.key}')">删除</a>
	   			</td>
	   		</tr>
   		</c:forEach>
   		<tr>
   			<td align="right" colspan="5">
   				总数量：${sessionScope.cart.totalQuantity }&nbsp;&nbsp;
   				总金额：${sessionScope.cart.totalPrice }&nbsp;&nbsp;
   				<a href="${pageContext.request.contextPath}/client/CilentServlet?op=genOrder">去结算</a>
   			</td>
   		</tr>
   	</table>
   </c:if>
   <script type="text/javascript">
   		function delAllItem(){
   			var sure = window.confirm("确定要清空购物车吗?");
   			if(sure){
   				window.location.href="${pageContext.request.contextPath}/client/CilentServlet?op=delAllItem";
   			}
   		}
   		function delOneItem(bookId){
   			var sure = window.confirm("确定要删除该项吗?");
   			if(sure){
   				window.location.href="${pageContext.request.contextPath}/client/CilentServlet?op=delOneItem&bookId="+bookId;
   			}
   		}
   
		function changeNum(quantityInputObj,bookId,oldNum){
			var num = quantityInputObj.value;
			//验证：数量必须是整数
			if(!/^[1-9][0-9]*$/.test(num)){
				alert("请输入正确的数量");
				return;
			}
			var sure = window.confirm("确定要修改为："+num+"吗？");
			if(sure){
				//提交到服务器：修改数量
				window.location.href="${pageContext.request.contextPath}/client/CilentServlet?op=changeNum&bookId="+bookId+"&num="+num;
			}else{
				//恢复原来的数量
				quantityInputObj.value = oldNum;
			}
			
		}
	</script>
  </body>
</html>
