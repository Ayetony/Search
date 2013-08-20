<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<title>上传成功</title>
<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
		type="text/css" />
</head>
<body style="background:url('image/login.jpg') no-repeat;">
<%@include file="menu.jsp"%>
		<table align="center" width="50%" border="1">
			<s:property value="errorMsg"/><br>
			<a href="upload.jsp">继续上传</a>
		</table>
</body>
</html>