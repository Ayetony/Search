<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.yzc.bean.*,java.util.*"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<table width="100%" height="30" border="0" bgcolor="#CCCCCC">
	<tbody>
		<tr>
			<td valign="top">当前用户:<%
				User user = (User) session.getAttribute("user");
				String[] levelname = user.getLevelinfor();
				String allIndex = session.getAttribute("allIndex").toString();
				String addIndex = session.getAttribute("addIndex").toString();
				if (user != null) {
					out.println(user.getName());%>
					<td><a href="welcome.jsp">个人中心	</a></td>
					<td valign="top">组别:<%
					out.println(user.getGroupname());
					out.println("<a href='login.jsp'>注销</a>");
				}
				%>
		</tr>
	</tbody>
</table>