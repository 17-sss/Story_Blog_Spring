<!-- UserDBBean Delete User Pro -->
<%-- <%@page import="com.db.UserDBBean"%> --%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- <% request.setCharacterEncoding("EUC-KR"); %>

<%
	String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
%>
<%
	String email = request.getParameter("email");
	String pwd = request.getParameter("pwd");
	UserDBBean dbPro = UserDBBean.getInstance();
	int check = dbPro.deleteUser(email, pwd); --%>

<c:if test="${check==1}">

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<meta http-equiv="Refresh" content="0; url=accountList?pageNum=${pageNum}">
	<script type="text/javascript">
		alert("삭제되었습니다.");
	</script>
</c:if>	

<c:if test="${check!=1}">
	<script type="text/javascript">
		alert("삭제 불가");
		history.go(-1);
	</script>
</c:if>
<title>Insert title here</title>
</head>
<body>
	
</body>
</html>