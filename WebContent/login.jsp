<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
body {font:12px/19px Arial, Helvetica, sans-serif; color:#000000;}
input.required{border:#999 1px solid; height:32px; width:120px;}
label{text-align:left; width:60px; float:left;}
.sub{margin-left:50px;}
.int{margin:5px auto;}
</style>
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
<style>
.sidebar1 {
	float: left;
	width: 180px;
	padding-bottom: 10px;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索引擎登陆界面</title>
</head>	
<body style="background:url('image/login.jpg');">
<h1>分布式搜索引擎</h1>
<h2>海量数据的最优解决方案，信息共享的最佳平台</h2>
<div class="sidebar1">
<p>1.采用并行化结构处理索引建立，突破效率瓶颈</p>
<p>2.采用HDFS存储海量数据，突破存储瓶颈</p>
<p>3.支持对非结构化文本全文搜索，没有格式限制</p></div>
<div class="center">
<s:form action="login.action" method="post" theme="simple">
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
	<td><label>密	码	:</label><s:textfield type="password" name="password"/>
		<s:fielderror cssStyle="color: red">
		<s:param>password</s:param>
		</s:fielderror>
		</td>
	</tr>
	<tr><td></td><td></td></tr>
	<tr>
	<td><s:submit value="登陆"/></td>
	<td><input type="button" value="新用户注册" onclick="window.location.href='register.jsp'"></td>
	</tr>
	</table>
</s:form>
</div>
</body>
</html>