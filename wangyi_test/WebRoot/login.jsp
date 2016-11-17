<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
    <form action="${pageContext.request.contextPath}/client/CilentServlet?op=login" method="post">
    	<table border="1" width="438">
    		<tr>
    			<td>用户名：</td>
    			<td>
    				<input type="text" name="username"/>
    			</td>
    		</tr>
    		<tr>
    			<td>密码：</td>
    			<td>
    				<input type="password" name="password"/>
    			</td>
    		</tr>
    		<tr>
    			<td>验证码：</td>
    			<td>
    				<input type="text" name="captcha" size="3"/>
    				<img id="img1" src="${pageContext.request.contextPath}/client/CilentServlet?op=genCaptcha"/>
    				<a href="javascript:changeCaptcha()">看不清</a>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="登陆"/>
    			</td>
    		</tr>
    	</table>
    	<script type="text/javascript">
    		function changeCaptcha(){
    			var imgObj = document.getElementById("img1");
    			imgObj.src = "${pageContext.request.contextPath}/client/CilentServlet?op=genCaptcha&time="+new Date().getTime();
    		}
    	</script>
    </form>
  </body>
</html>
