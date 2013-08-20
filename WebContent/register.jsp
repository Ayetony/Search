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
	left: 50%;
	top: 50%;
	margin: -120px 0px 0px -120px;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
</head>
<body style="background:url('image/login.jpg');">
<div class="center">
<s:form action="register.action" method="post" theme="simple">
	<table align="center" width="50%">
	<tr>
	<td><label>用	户	名	:</label><s:textfield type="text" name="id"/>
		<s:fielderror cssStyle="color: red">
		<s:param>id</s:param>
		</s:fielderror>
	</td>
	</tr>
	<tr><td></td><td></td></tr>
	<tr>
	<td><label>姓	名	:</label><s:textfield type="text" name="name"/>
		<s:fielderror cssStyle="color: red">
		<s:param>name</s:param>
		</s:fielderror>
	</td>
	</tr>
	<tr><td></td><td></td></tr>
	<tr>
	<td><label>密	码	:</label><s:textfield type="password" name="password"/>
		<s:fielderror cssStyle="color: red">
		<s:param>password</s:param>
		</s:fielderror>
		</td>
	</tr>
	<tr><td></td><td></td></tr>
	<tr>
	<td><label>重复输入密	码	:</label><s:textfield type="password" name="password_val"/>
		<s:fielderror cssStyle="color: red">
		<s:param>password</s:param>
		</s:fielderror>
		</td>
	</tr>
	<tr><td></td><td></td></tr>
	<tr>
	<td><s:submit value="注册"/></td>
	</tr>
	</table>
</s:form>
</div>
</body>
</html>