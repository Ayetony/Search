<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.yzc.bean.*"%>    
    <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">
.sidebar1 {
	float: left;
	width: 600px;
	padding-bottom: 1px;
}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索结果</title>
</head>
<body style="background:url('image/login.jpg') fixed no-repeat;">
<%@include file="menu.jsp"%>
<%
	Result res = (Result)request.getAttribute("Result");
	String title = res.getMessage();
	String[] filename = res.getFilename();
	String[] filenameGo = res.getFilenameGo();
	String[] detail = res.getDetail();
	String[] userPrint = res.getUser();
	String[] uploadTime = res.getUploadTime();
	long time = res.getTime();
	int totalhit = res.getTotalHit();
	int tmpint = totalhit % 10;
	int allsplit;
	if(tmpint == 0)
		allsplit = totalhit / 10;
	else
		allsplit = (totalhit - tmpint) / 10 + 1;
	if(res != null){
		out.println("<h2>对\"" + title + "\"的搜索结果共有" + totalhit + "条，耗时：" + time + "毫秒</h2>");
		%>
<s:form action="doSearch" method="post" theme="simple">
<table>
<tr>
	<td>
	<s:textfield name="input" size="80" value="%{#request.Result.Message}"/>
	<s:submit value="搜索"/>
	<s:textfield name="splitstring" value="0" style="visibility:hidden"/>
	</td>
</tr>
</table>
</s:form>
		<div class="sidebar1"><%
		if(filename != null)
			for(int i = 0; i < detail.length; i++){
				String tmp = detail[i].replaceAll("/n", "");
				if(tmp.length() > 200)
					if(tmp.indexOf("</font>") > 200)
						tmp = "..." + tmp.substring(tmp.indexOf("</font>") - 100, 200) + "..." + "</font>";
					else
						tmp = "..." + tmp.substring(0, 200) + "..." + "</font>";
				if(tmp.length() == 0)
					tmp = "该文件格式暂时不支持全文搜索...";
				%>
				<h3><a href="DATA/<%=filenameGo[i]%>"><%=filename[i]%></a></h3>
				<label>上传者：<%=userPrint[i] %>	</label><label>上传时间：<%=uploadTime[i].replace('-', ':') %></label>
				<br><%=tmp %><br>
				<%
			}%><h3><%
		for(int i = 0; i < allsplit; i++){ %>
		<a href="doSearch?input=<%=title %>&splitstring=<%=i %>" >[<%=i+1%>]</a><%} %><a href="doSearch.jsp"> 返回继续搜索</a></h3>
		</div><%
	}
%>
</body>
</html>