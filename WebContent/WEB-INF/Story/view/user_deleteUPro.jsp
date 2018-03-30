<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${check==1}">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<meta http-equiv="Refresh" content="0; url=index">
	<script type="text/javascript">
		alert("탈퇴되었습니다.. 안녕히가세요.");
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