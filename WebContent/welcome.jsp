<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>非结构文本搜索引擎</title>
</head>
<body style="background:url('image/login.jpg');">
<%@include file="menu.jsp"%>
<ul>
<h1>欢迎使用分布式搜索引擎</h1>
<%
	String setDateFormat = new SimpleDateFormat("HH").format(new Date());
	int i = Integer.parseInt(setDateFormat);
	if(i > 4 && i < 12)
		out.println("早安，" + user.getName() + "!\r\n目前共有记录" + allIndex + "条。");
	else if(i > 11 && i < 14)
		out.println("午安，" + user.getName() + "!\r\n目前共有记录" + allIndex + "条。");
	else if(i > 13 && i < 18)
		out.println("下午好，" + user.getName() + "!\r\n目前共有记录" + allIndex + "条。");
	else
		out.println("晚上好，" + user.getName() + "!\r\n目前共有记录" + allIndex + "条。");
%>
<%boolean flag = user.admin(); 	
if(flag){
	out.println("共有未更新记录" + addIndex + "条。");	%>
	<li>
	  	<s:url id="url" action="updateIndex">
	  	</s:url>
	  	<h3><s:a href="%{url}">更新索引</s:a></h3>
  	</li>
	<li>
	<s:url id="url" action="doIndex">
		</s:url>
		<h3><s:a href="%{url}">新建索引</s:a></h3>
	</li>
	<li>
		<h3><s:a href="control.jsp">管理用户</s:a></h3>
	</li><%} %>
	<li>
		<h3><s:a href="upload.jsp">上传文件</s:a></h3>
	</li>
	<li>
		<h3><s:a href="doSearch.jsp">进入搜索引擎</s:a></h3>
	</li>
</ul>
</body>
</html>