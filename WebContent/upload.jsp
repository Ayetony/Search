<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
</head>
<body style="background:url('image/login.jpg') no-repeat;">
<%@include file="menu.jsp"%>
<script type="text/javascript">

<!--addMore函数可以提供上传多个文件上传-->

function addMore()
{

	var td = document.getElementById("more");
	
	var br = document.createElement("br");
	var input = document.createElement("input");
	var button = document.createElement("input");
	
	input.type = "file";
	input.name = "upload";
	
	button.type = "button";
	button.value = "   删除    ";
	
	button.onclick = function()
	{
		td.removeChild(br);
		td.removeChild(input);
		td.removeChild(button);
	}
	td.appendChild(br);
	td.appendChild(input);
	td.appendChild(button);
}
	function validate_required() {
		
	}
</script>

		<!--<h3><font color="red">上传文件类型后缀为doc,ppt,xls,pdf,txt,java，每个文件大小不能大于50M</font></h3>-->

		<table align="center" width="50%">
			<tr>
				<td>
					<s:fielderror cssStyle="color: red">
					<s:param>fieldupload</s:param>
					</s:fielderror>
					<br>
					<s:fielderror cssStyle="color: red">
					<s:param>fieldlevelSelection</s:param>
					</s:fielderror>
					<br>
					<!--		<s:actionerror cssStyle="color:red"/>-->
				</td>
			</tr>
		</table>
		
		<s:form action="upload.action" theme="simple" method="post"
			enctype="multipart/form-data">
			<s:textfield name="userName" value="%{#session.name}" style="visibility:hidden"/>
			<table align="center" width="50%" border="1">
				<tr>
					<td>
						上传文件
					</td>
					<td id="more" >
						<s:file name="upload">
						</s:file>
						<input type="button" value="上传更多..." onclick="addMore()">
						<s:select list="#session.levelSelection" name="levelSelection" label="Level选择" headerKey="-1" headerValue="-----请选择上传文件的权限-----"/>
					</td>
				</tr>
				<tr>
					<td>
						<s:submit value=" 确认 "></s:submit>
					</td>
					<td>
						<s:reset value=" 重置 "></s:reset>
					</td>
				</tr>
			</table>

		</s:form>
</body>
</html>