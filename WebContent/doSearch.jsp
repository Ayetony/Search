<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css" media="screen">
.center{
	position:absolute;
	width: 120px;
	height: 120px;
	left: 39%;
	top: 55%;
	margin: -120px 0px 0px -120px;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索页面</title>
</head>
<body style="background:url('image/login.jpg') no-repeat;">
<%@include file="menu.jsp"%>
<h1>欢迎使用分布式搜索引擎</h1>
<div class="center">
<s:form action="doSearch" method="post">
<tr>
	<td>
	<s:textfield name="input" size="80"/>
	<s:submit value="搜索"/>
	</td>
</tr>
<s:textfield name="splitstring" value="0" style="visibility:hidden"/>
</s:form>
</div>
</body>
</html>