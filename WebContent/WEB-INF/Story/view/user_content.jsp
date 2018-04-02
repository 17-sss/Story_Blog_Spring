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
	<script type="text/javascript">
		function onClick(element) {
		  document.getElementById("img01").src = element.src;
		  document.getElementById("modal01").style.display = "block";
		  var captionText = document.getElementById("caption");
		  captionText.innerHTML = element.alt;
		}
	</script>
</head>
<body>
<!-- 전체 틀 div ★ -->  
<div>&nbsp;

<!-- 메인 (이페이지에서는 사진 O) -->
<div style="margin-top:54px; margin-left: 10%;"><br>
	<!-- <div class="w3-col m8 w3-row" style="margin-left: 22%; margin-top: 5%; margin-bottom: 3%;"> -->

<!-- 메인 margin -->
<div style="margin-left: 10%; margin-right: 10%;">
	    
        <!-- 일기 (메인) -->
		<div class="w3-container w3-card w3-white w3-round w3-margin">
			<br> 
			<img src="${pageContext.request.contextPath}/userSave/${filename}" alt="${filename}" class="w3-left w3-circle w3-margin-right"
				style="width: 60px; height: 60px"> 
			<!-- 날짜 출력 -->
			<span class="w3-right w3-text-gray">${diary.cdate}</span>
			<!-- 제목 출력 -->
			<b>제목:</b> ${diary.subject}
			<!-- 수정, 목록, 삭제 -->
			<div style="margin-top: 6px;">
				<form method="post" style="display: inline-block; margin-left: 10px;" class="w3-right" action="${pageContext.request.contextPath}/story/user_deleteDPro">
					<input type="submit" class="w3-button w3-red w3-small" value="삭제">
					
					<!-- hidden으로  deleteDPro에 넘기기!!!  -->
               		<%-- <input type="hidden" name="email" value="<%=diary.getEmail() %>"> --%>
               		<input type="hidden" name="diaryid" value="${diary.diaryid}">
               		<input type="hidden" name="num" value="${diary.num}">
               		<!--  -->
				</form>
					
				<form method="post" style="display: inline-block; margin-left: 10px;" class="w3-right" action="${pageContext.request.contextPath}/story/user_updateDForm">
					<input type="submit" class="w3-button w3-blue w3-small" value="수정">
					 
					 <input type="button" style="display: inline-block; margin-left: 10px;" class="w3-button w3-right w3-yellow w3-small" value="목록" onclick="history.back();" >
					
					
					<!-- hidden으로  updateDForm에 넘기기!!!  -->
               		<input type="hidden" name="diaryid" value="${diary.diaryid}">
               		<input type="hidden" name="num" value="${diary.num}">
               		<!--  -->
				</form>
				
				
			</div>
			<!-- end. 수정, 목록, 삭제 -->
			
			<!-- 이메일 출력 -->		
			<p class="w3-text-gray" style="font-size: 9pt;">${diary.email}</p>
			<hr class="w3-clear">
			<!-- 내용 출력 -->
			<p>${diary.content}</p>

			<!-- 이미지 출력 1 -->
			<c:if test="${diary.filename0!=null}">
				
			<div class="w3-third w3-container w3-margin-bottom">
				<div class="w3-container w3-white w3-padding w3-center">
					<img src="${pageContext.request.contextPath}/fileSave/${diary.filename0}" alt="${diary.filename0}" class=autoimg onclick="onClick(this)">
					<div class="w3-container w3-white">
						<p>${diary.cdate}</p>
					</div>
				</div>
			</div>		
			</c:if>
			
			<!-- 이미지 출력 2 -->
			<c:if test="${diary.filename1!=null}">
				
			<div class="w3-third w3-container w3-margin-bottom">
				<div class="w3-container w3-white w3-padding w3-center">
					<img src="${pageContext.request.contextPath}/fileSave/${diary.filename1}" alt="${diary.filename1}" class=autoimg onclick="onClick(this)">
					<div class="w3-container w3-white">
						<p>${diary.cdate}</p>
					</div>
				</div>
			</div>		
			</c:if>
			
			<!-- 이미지 출력 3 -->
			<c:if test="${diary.filename2!=null}">
				
			<div class="w3-third w3-container w3-margin-bottom">
				<div class="w3-container w3-white w3-padding w3-center">
					<img src="${pageContext.request.contextPath}/fileSave/${diary.filename2}" alt="${diary.filename2}" class=autoimg onclick="onClick(this)">
					<div class="w3-container w3-white">
						<p>${diary.cdate}</p>
					</div>
				</div>
			</div>		
			</c:if>
			
			<!-- 이미지 출력 4 -->
			<c:if test="${diary.filename3!=null}">
				
			<div class="w3-third w3-container w3-margin-bottom">
				<div class="w3-container w3-white w3-padding w3-center">
					<img src="${pageContext.request.contextPath}/fileSave/${diary.filename3}" alt="${diary.filename3}"  class=autoimg onclick="onClick(this)">
					<div class="w3-container w3-white">
						<p>${diary.cdate}</p>
					</div>
				</div>
			</div>		
			</c:if>
			
			<!-- 이미지 출력 5 -->
			<c:if test="${diary.filename4!=null}">
				
			<div class="w3-third w3-container w3-margin-bottom">
				<div class="w3-container w3-white w3-padding w3-center">
					<img src="${pageContext.request.contextPath}/fileSave/${diary.filename4}" alt="${diary.filename4}" class=autoimg onclick="onClick(this)">
					<div class="w3-container w3-white">
						<p>${diary.cdate}</p>
					</div>
				</div>
			</div>		
			</c:if>
		
		</div>
		
		<!-- 모달로 띄우기 -->
		
		<div id="modal01" class="w3-modal" style="padding-top:0" onclick="this.style.display='none'">
    		<span class="w3-button w3-black w3-xlarge w3-display-topright">×</span>
	    	<div class="w3-modal-content w3-animate-zoom w3-center w3-transparent w3-padding-64">
	      	<!-- <div class="w3-modal-content w3-animate-zoom w3-center w3-transparent w3-padding-64" style="margin-top:15%; width: auto; height: 100%;"> -->
	      		<img id="img01" class="w3-image">
	      		<p id="caption"></p>
	    	</div>
  		</div>
			
		<!-- end. 일기 (타임라인) -->	
      	
</div>
<!-- end. 메인 margin -->
</div>
<!-- end. 메인 -->

</div>
<!-- end. 전체 틀 div ★ -->



</body>
</html>