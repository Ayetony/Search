<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
body {font:24px/38px Arial, Helvetica, sans-serif; color:#000000;}
input.required{border:#999 1px solid; height:32px; width:120px;}
label{text-align:center; width:60px; float:center;}
.sub{margin-left:50px;}
.int{margin:5px auto;}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>这是一个神奇的页面</title>
</head>
<body style="background:url('image/login.jpg') no-repeat;">
<table align="center" width="50%">
			<tr>
				<td>
					<!--		<s:actionerror cssStyle="color:red"/>-->
					<label>当您看见这个页面的时候，只会有如下三种可能：</label><img src="image/exception2.png"><br>
					<label>1.有人把服务器玩坏了。。。</label><img src="image/exception4.png"><br>
					<label>2.长时间没有进行操作需要重新登陆</label><img src="image/exception1.png"><br>
					<label>3.用户名或者密码输入错误</label><img src="image/exception3.png"><br>
					<label>无论哪种，都推荐你戳一下下面的链接重试一次</label><br>
				</td>
			</tr>
		</table>
<table align="center" width="50%">
			<tr>
				<td>
					<a href="login.jsp">点此重新登陆</a><img src="image/exception5.png">
				</td>
			</tr>
		</table>
</body>
</html>