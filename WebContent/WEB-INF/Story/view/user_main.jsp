<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<title>Story Blog - User</title>
	<style type="text/css">
		/* 이미지 반응형으로 처리.. */
		.autoimg {
			display: inline-block;
			width: auto !important;
			max-width: 100%;
			height: 250px !important;
		}
	</style>
</head>
<body>
<!-- 전체 틀 div ★ -->  
<div>&nbsp;

<!-- 메인 (이페이지에서는 사진 O) -->
<div style="margin-top:54px; margin-left: 10%;"><br>
	<!-- <div class="w3-col m8 w3-row" style="margin-left: 22%; margin-top: 5%; margin-bottom: 3%;"> -->

<!-- 메인 margin -->
<div style="margin-left: 10%; margin-right: 10%;">

		<!-- 하루의 끝 - form 전송 -->
		<form action="${pageContext.request.contextPath}/story/user_write" method="post">
				
			<!-- 하루의 끝 -->
			<div class="w3-row-padding">
				<div class="w3-col m12">
					<div class="w3-card w3-round w3-white">
						<div class="w3-container w3-padding">
							<h6 class="w3-text-gray">오늘 하루를 요약해보세요.</h6>
							<input class="w3-input w3-border w3-text-grey" name="subject" type="text" style="font-size: 9pt;" value="${subject}"><br>
							<button type="submit" class="w3-button w3-theme w3-pink w3-right" 
								onclick="location.href='${pageContext.request.contextPath}/story/user_write'">글쓰기</button>
								<input type="hidden" name="diaryid" value="${diaryid}">
								<input type="hidden" name="subject" value="${subejct}">
						</div>
					</div>
				</div>
			</div>
			<!-- end. 하루의 끝 -->
				
		</form>
		<!-- end. 하루의 끝 - form 전송 -->
			
		
		<!-- 일기가 없을 때 -->	
        <c:if test="${count==0}">
        <div class="w3-container w3-card w3-white w3-round w3-margin">
       		<h3 class="w3-center">일기 수:${count}</h3>

	        <div class="w3-center w3-container">
	       		<p class="w3-pink">일기를 써주세요!!</p>
	       	</div>	
        </div>
        </c:if>
       
	    
	    <!-- 일기가 있을 때 -->
	    <c:if test="${count!=0}">
	    <div class="w3-container w3-card w3-white w3-round w3-margin">
	    	<h3 class="w3-center">일기 수:${count}</h3>
	    </div>
	    
        <!-- 일기 (메인) -->
        <c:forEach var="diary" items="${diaryList}">
		<div class="w3-container w3-card w3-white w3-round w3-margin">
			<br> 
			<img src="${pageContext.request.contextPath}/userSave/${filename}" alt="${filename}" class="w3-left w3-circle w3-margin-right"
				style="width: 60px; height: 60px"> 
			<!-- 날짜 출력 -->
			<span class="w3-right w3-text-gray">${diary.cdate}</span>
			<!-- 제목 출력 -->
			<b>제목: </b><a href="${pageContext.request.contextPath}/story/user_content?num=${diary.num}&pageNum=${currentPage}"><b>${diary.subject}</b></a>
			<!-- 수정, 삭제 -->
			<div style="margin-top: 6px;">
				<form method="post" style="display: inline-block; margin-left: 10px;" class="w3-right" action="${pageContext.request.contextPath}/story/user_deleteDPro">
					<input type="submit" class="w3-button w3-red w3-small" value="삭제">
					
					<!-- hidden으로  deleteDPro에 넘기기!!!  -->
               		<%-- <input type="hidden" name="email" value="<%=diary.getEmail() %>"> --%>
               		<input type="hidden" name="diaryid" value="${diary.diaryid}">
               		<input type="hidden" name="num" value="${diary.num}">
               		<!--  -->
				</form>
					
				<form method="post" style="display: inline-block;" class="w3-right" action="${pageContext.request.contextPath}/story/user_updateDForm">
					 <input type="submit" class="w3-button w3-blue w3-small" value="수정">
					 
					 <!-- hidden으로  updateDForm에 넘기기!!!  -->
               		<%-- <input type="hidden" name="email" value="<%=diary.getEmail() %>"> --%>
               		<input type="hidden" name="diaryid" value="${diary.diaryid}">
               		<input type="hidden" name="num" value="${diary.num}">
               		<!--  -->
				</form>
			</div>
			<!-- end. 수정, 삭제 -->
			
			<!-- 이메일 출력 -->		
			<p class="w3-text-gray" style="font-size: 9pt;">${diary.email}</p>
			
			<hr class="w3-clear">
			<!-- 내용 출력 -->
			<p>${diary.content}</p>

			<!-- 이미지 출력 -->
			<c:if test="${diary.filename0!=null}">
				<div class="w3-third w3-container w3-margin-bottom">
					<div class="w3-container w3-white w3-padding w3-center">
						<img src="${pageContext.request.contextPath}/fileSave/${diary.filename0}" alt="${diary.filename0}" class=autoimg>
					</div>
				</div>		
			</c:if>
			
			<c:if test="${diary.filename1!=null}">
				<div class="w3-third w3-container w3-margin-bottom">
					<div class="w3-container w3-white w3-padding w3-center">
						<img src="${pageContext.request.contextPath}/fileSave/${diary.filename1}" alt="${diary.filename1}" class=autoimg>
					</div>
				</div>		
			</c:if>
			
			<c:if test="${diary.filename2!=null}">
				<div class="w3-third w3-container w3-margin-bottom">
					<div class="w3-container w3-white w3-padding w3-center">
						<img src="${pageContext.request.contextPath}/fileSave/${diary.filename2}" alt="${diary.filename2}" class=autoimg>
					</div>
				</div>		
			</c:if>
			
			<c:if test="${diary.filename3!=null}">
				<div class="w3-third w3-container w3-margin-bottom">
					<div class="w3-container w3-white w3-padding w3-center">
						<img src="${pageContext.request.contextPath}/fileSave/${diary.filename3}" alt="${diary.filename3}" class=autoimg>
					</div>
				</div>		
			</c:if>
			
			<c:if test="${diary.filename4!=null}">
				<div class="w3-third w3-container w3-margin-bottom">
					<div class="w3-container w3-white w3-padding w3-center">
						<img src="${pageContext.request.contextPath}/fileSave/${diary.filename4}" alt="${diary.filename4}" class=autoimg>
					</div>
				</div>		
			</c:if>
		</div>

		</c:forEach>
		</c:if>
			
		<!-- end. 일기 (타임라인) -->	
		
		<!-- 일기 페이지 -->
		<div class = "w3-center w3-container w3-card w3-white w3-round w3-margin">
			<c:if test="${count>0}"> 
				<c:if test="${startPage > bottomLine}">
					<a href="user_main?pageNum=${startPage - bottomLine}">[이전]</a>
				</c:if>
				
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<a href="user_main?pageNum=${i}"> <c:if test="${i != currentPage}">[${i}]</c:if>
						<c:if test="${i == currentPage}">
							<font color='red'>[${i}]</font>
						</c:if>
					</a>
				</c:forEach>
				
				<c:if test="${endPage < pageCount}">
						<a href="user_main?pageNum=${startPage + bottomLine}">[다음]</a>
		
					</c:if>
				</c:if>
		</div>
<!-- end. 일기 페이지 -->
		
      	
</div>
<!-- end. 메인 margin -->
</div>
<!-- end. 메인 -->

</div>
<!-- end. 전체 틀 div ★ -->



</body>
</html>