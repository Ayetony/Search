<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,com.yzc.bean.*"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs
/jquery/1.4.0/jquery.min.js">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理用户</title>
</head>
<body>
<%@include file="menu.jsp"%>
<%boolean flag = user.admin(); 	
if(flag){%>
<h1><s:label name="back"></s:label></h1>
<h2 align="left">用户修改</h2>
<s:form theme="simple" action="updateUser" method="post">
<table align="left" width="100%" border="1">
<tr>
	<td>
		是否修改
	</td>
	<td>
		用户Id
	</td>
	<td>
		用户姓名
	</td>
	<td>
		用户组别
	</td>
	<td>
		删除用户
	</td>
</tr>
<br>
<% 
	List list = (ArrayList) request.getAttribute("List");
	Iterator iterator = list.iterator();
	int i = 0;
	Manager manager = new Manager(); 
%>
<s:iterator value="list">
<%
	manager = (Manager) iterator.next();%>
<tr>
	<td>
		<input type="checkbox" name="check" value="<%= manager.getId() + "@@" + i %>"/>
	</td>
	<td>
		<s:label><%= manager.getId()%></s:label>
	</td>
	<td>
		<s:label><%= manager.getName()%></s:label>
	</td>
	<td>
		<s:select 
			name="groupId"
			headerKey="groupId%>"
			value="groupId"
			list="select"/>
	</td>
	<td>
		<input type="checkbox" name="delete" value="<%= manager.getId() %>"/>
	</td>
</tr>
<%i++; %>
</s:iterator>
<tr>
	<td><s:submit value="提交"/></td>
</tr>
</table>
</s:form>
<s:form theme="simple" action="register" method="post">
<table align="left" width="100%" border="1">
	<tr>
		<td>用户ID</td>
		<td>用户密码</td>
		<td>用户姓名</td>
		<td>用户群组</td>
		<td>添加新用户</td>
	</tr>
	<tr>
		<td>
			<s:textfield type="text" name="id"/>
		</td>
		<td>
			<s:textfield type="password" name="password"/>
		</td>
		<td>
			<s:textfield type="text" name="name"/>
		</td>
		<td>
			<s:select name="group" list="select" value="group"/>
		</td>
		<td>
			<s:submit value="添加"/>
		</td>
	</tr>
</table>
</s:form>
<%} %>
</body>
</html>