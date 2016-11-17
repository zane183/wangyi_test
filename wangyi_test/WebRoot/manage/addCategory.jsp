<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<h3>添加分类</h3>
    <form  action="${pageContext.request.contextPath}/manage/ManageServlet?op=addCategory" method="post">
    	<table border="1" width="438">
    		<tr>
    			<td>分类名称:</td>
    			<td>
    				<input type="text" id="name" name="name"/>
    				<font id="exist" style="display: none;" color='red'>该名称已经存在了</font>
    				<font id="notexist" style="display: none;" color='green'>该名称可以使用</font></span>
    			</td>
    		</tr>
    		<tr>
    			<td>分类描述:</td>
    			<td>
    				<textarea name="description" rows="3" cols="38"></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" id="bt1" value="保存"/>
    			</td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	//当名称失去焦点时，检查名称是否可用
    	document.getElementById("name").onblur=function(){
    		//获取用户输入的值
    		var name = this.value;
    		//判断是否为空
    		if(name==""){
    			alert("请输入分类名称");
    			this.focus();
    			return;
    		}
    		//异步检查是否可用
    		var xhr = getXhr();
    		xhr.onreadystatechange=function(){
    			if(xhr.readyState==4){
    				if(xhr.status==200){
    					var rtValue=xhr.responseText;
    					if(rtValue=="true"){
    						document.getElementById("exist").style.display="block";
    						document.getElementById("notexist").style.display="none";
    						document.getElementById("bt1").disabled=true;
    					}else{
    						document.getElementById("exist").style.display="none";
    						document.getElementById("notexist").style.display="block";
    						document.getElementById("bt1").disabled=false;
    					}
    				}
    			}
    		}
    		xhr.open("POST","${pageContext.request.contextPath}/manage/ManageServlet?time="+new Date().getTime()+"&op=checkCategoryName");
    		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");//告诉服务器请求正文的mime类型
    		xhr.send("name="+name);
    	}
    </script>
  </body>
</html>
