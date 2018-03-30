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
	<meta http-equiv="Refresh" content="0; url=user_main?pageNum=${pageNum}"> <!-- 나중엔 각 페이지 마다 뒤로 가게 만들기. 지금은 무조건 main으로감 -->

	<script type="text/javascript">
		alert("삭제되었습니다.");
	</script>

</c:if>

<c:if test="${check!=1}"><%-- <% } else { %> --%>
	<script type="text/javascript">
		alert("삭제 불가");
		history.go(-1);
	</script>
</c:if>	
	<%-- <% } %> --%>
<title>Insert title here</title>
</head>
<body>
	
</body>
</html>