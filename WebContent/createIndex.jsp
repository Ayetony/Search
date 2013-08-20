<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成功建立索引</title>
</head>
<body style="background:url('image/login.jpg') no-repeat;">
<%@include file="menu.jsp"%>
成功：<s:property value="Time"/><br>
<a href="login.jsp">重新登陆</a>
</body>
</html>