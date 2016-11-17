<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/manage/header.jsp"%>
<h3>添加书籍</h3>
    <form  action="${pageContext.request.contextPath}/manage/ManageServlet?op=addBook" enctype="multipart/form-data" method="post">
    	<table border="1" width="438">
    		<tr>
    			<td>书名:</td>
    			<td>
    				<input type="text" id="name" name="name"/>
    			</td>
    		</tr>
    		<tr>
    			<td>作者:</td>
    			<td>
    				<input type="text" id="author" name="author"/>
    			</td>
    		</tr>
    		<tr>
    			<td>单价:</td>
    			<td>
    				<input type="text"  name="price"/>
    			</td>
    		</tr>
    		<tr>
    			<td>图片:</td>
    			<td>
    				<input type="file"  name="image"/>
    			</td>
    		</tr>
    		<tr>
    			<td>分类描述:</td>
    			<td>
    				<textarea name="description" rows="3" cols="38"></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td>所属分类:</td>
    			<td>
    				<select name="categoryId">
    					<c:forEach items="${cs}" var="c">
    						<option value="${c.id}">${c.name}</option>
    					</c:forEach>
    				</select>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" id="bt1" value="保存"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
