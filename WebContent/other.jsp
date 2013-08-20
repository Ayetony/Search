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
<title>权限修改</title>
</head>
<body>
<%@include file="menu.jsp"%>
<%boolean flag = user.admin(); 	
if(flag){%>
<h1><s:label name="back"></s:label></h1>
<h2 align="left">群组权限修改</h2>
<s:form theme="simple" action="updateRelation" method="post">
<% 
	HashMap<String, List<String>> map = (HashMap<String, List<String>>) request.getAttribute("GroupToLevel");
	HashMap groupName = (HashMap) request.getAttribute("Select");
	HashMap levelName = (HashMap) request.getAttribute("AllLevel");
	Iterator iteratorRelation = map.entrySet().iterator();
	Iterator iteratorGroup = groupName.entrySet().iterator();
	Iterator iteratorLevel;
	Map.Entry entry, entry2;
	List<String> list2, list3;
	int i = 0;
%>
<table align="left" width="100%" border="1">
	<tr>
		<td>是否修改</td>
		<td>群组名称</td>
		<td>拥有权限</td>
	</tr>
	<%while(iteratorGroup.hasNext()){
		iteratorLevel = levelName.entrySet().iterator();
		entry = (Map.Entry) iteratorGroup.next();
		list2 = map.get(entry.getKey());%>
	<tr>
		<td>
			<input type="checkbox" name="relation" value="<%= entry.getKey() + "@@" + i %>"/>
		</td>
		<td>
			<s:label><%= entry.getValue() %></s:label>
		</td>
		<td>
		<%while(iteratorLevel.hasNext()){
			entry2 = (Map.Entry) iteratorLevel.next();
			if(null == list2 || list2.size() == 0){%>
				<input type="checkbox" name="updateRelation" value="<%= entry2.getKey() %>"/><%= entry2.getValue() %>
			<%}
			else{
				if(list2.contains(entry2.getKey())){ %>
				<input type="checkbox" name="updateRelation" value="<%= entry2.getKey() %>" checked="checked"/><%= entry2.getValue() %>
				<%}
				else {%>
				<input type="checkbox" name="updateRelation" value="<%= entry2.getKey() %>"/><%= entry2.getValue() %>
				<%}
			}
		} %>
		<input type="checkbox" name="updateRelation" value="split" checked="checked" style="visibility:hidden"/>
		</td>
	</tr>
	<%i++;} %>
	<tr>
	<td><s:submit value="提交"/></td>
	</tr>
</table>
</s:form>
<br>
<h2 align="left">群组修改</h2>
<s:form theme="simple" action="updateGroup" method="post">
<table align="left" width="100%" border="1">
	<tr>
		<td>群组Id</td>
		<td>群组名称</td>
		<td>修改</td>
	</tr>
	<%
	HashMap groupName = (HashMap) request.getAttribute("Select");
	Iterator iterator = groupName.entrySet().iterator();
	Map.Entry entry;
	while(iterator.hasNext()){
		entry = (Map.Entry) iterator.next();%>
	<tr>
		<td><%= entry.getKey() %></td>
		<td><%= entry.getValue() %></td>
		<s:url id="myUrl" action="updateGroup">
        	<s:param name="deleteId"><%= entry.getKey() %></s:param>
		</s:url>
		<td><s:a href="%{myUrl}">删除群组</s:a></td>
	</tr>
	<% }
	%>
	<tr>
		<td><s:textfield type="text" name="createId"/></td>
		<td><s:textfield type="text" name="createName"/></td>
		<td><s:submit value="添加"/></td>
	</tr>
</table>
</s:form>
<br>
<h2 align="left">权限修改</h2>
<s:form theme="simple" action="updateLevel" method="post">
<table align="left" width="100%" border="1">
	<tr>
		<td>权限Id</td>
		<td>权限名称</td>
		<td>修改</td>
	</tr>
	<%
	HashMap groupName = (HashMap) request.getAttribute("AllLevel");
	Iterator iterator = groupName.entrySet().iterator();
	Map.Entry entry;
	while(iterator.hasNext()){
		entry = (Map.Entry) iterator.next();%>
	<tr>
		<td><%= entry.getKey() %></td>
		<td><%= entry.getValue() %></td>
		<s:url id="myUrl" action="updateLevel">
        	<s:param name="deleteId"><%= entry.getKey() %></s:param>
		</s:url>
		<td><s:a href="%{myUrl}">删除权限</s:a></td>
	</tr>
	<% }
	%>
	<tr>
		<td><s:textfield type="text" name="createId"/></td>
		<td><s:textfield type="text" name="createName"/></td>
		<td><s:submit value="添加"/></td>
	</tr>
</table>
</s:form>
<%} %>
</body>
</html>