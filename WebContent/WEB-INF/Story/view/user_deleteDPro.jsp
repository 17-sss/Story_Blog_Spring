<!-- DiaryDBBean Delete User Pro -->
<%@page import="com.db.DiaryDBBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- <% request.setCharacterEncoding("EUC-KR"); %>

<%
	String diaryid = request.getParameter("diaryid");
	String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
%> --%>
<%-- <%
	int num = Integer.parseInt(request.getParameter("num"));
	/* String email = request.getParameter("email"); */
	
	/* DiaryDBBean dbPro = DiaryDBBean.getInstance();
	int check = dbPro.deleteDiary(num, (String)session.getAttribute("sessionID"), diaryid); */
	
	
	if (check == 1) {
	%> --%>
	
<c:if test="${check==1}">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<meta http-equiv="Refresh" content="0; url=user_main?pageNum=${pageNum}"> <!-- ���߿� �� ������ ���� �ڷ� ���� �����. ������ ������ main���ΰ� -->

	<script type="text/javascript">
		alert("�����Ǿ����ϴ�.");
	</script>

</c:if>

<c:if test="${check!=1}"><%-- <% } else { %> --%>
	<script type="text/javascript">
		alert("���� �Ұ�");
		history.go(-1);
	</script>
</c:if>	
	<%-- <% } %> --%>
<title>Insert title here</title>
</head>
<body>
	
</body>
</html>