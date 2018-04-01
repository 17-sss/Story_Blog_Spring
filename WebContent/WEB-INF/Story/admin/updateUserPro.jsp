<%-- <%@page import="com.db.UserDBBean"%> --%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${chk==1}">
		<input type="hidden" name="filename" value="${user.filename}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<script type="text/javascript">
			alert("수정 완료");
			location.href="${pageContext.request.contextPath}/admin/accountList?pageNum=${pageNum}";
		</script>
		<meta http-equiv="Refresh" content="0;url=updateUserForm?email=${email}&pwd=${pwd}&pageNum=${pageNum}">
	</c:if>
	
	<c:if test="${chk!=1}">
	<script type="text/javascript">
		alert("수정 불가");
		history.go(-1);
	</script>
	</c:if>
</body>
</html>