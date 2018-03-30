<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<title>Story Blog - 구역테스트</title>
</head>
<body>
<!-- 전체 틀 div ★ --> 
<div>&nbsp;

<!-- 상단 바, 사이드 바 간격 -->
	<div style="margin-top:54px; margin-left: 10%;"><br>
		
		<!-- 양 옆 간격 -->
		<div style="margin-left: 25%; margin-right: 25%;">	
			
			<!-- MyPage -->
			<div class="w3-card w3-round w3-white">
				<h1 class="w3-center">My Page</h1>
			</div>
			
			<!--수정  -->
			<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath() %>/story/user_updateUPro">
				<div class="w3-card w3-round w3-white w3-padding">
					
					<div class="w3-row w3-section">
					  <div class="w3-col w3-red w3-round w3-center" style="width:100px; font-size: 9pt;">이메일 <br>(수정 불가)</div>
					    <div class="w3-rest">
					      <input class="w3-input w3-light-gray"  name="email" type="text" value ="${user.email}" readonly="readonly" style="margin-left: 2%;">
					    </div>
					</div>
			
					<div class="w3-row w3-section">
					  <div class="w3-col w3-blue w3-round w3-center" style="width:100px;">이름</div>
					    <div class="w3-rest">
					      <input class="w3-input"  name="name" type="text" value="${user.name}" style="margin-left: 2%;">
					    </div>
					</div>
			
					<div class="w3-row w3-section">
					  <div class="w3-col w3-blue w3-round w3-center" style="width:100px;">비밀번호</div>
					    <div class="w3-rest">
					      <input class="w3-input"  name="pwd" type="text" value="${user.pwd}" style="margin-left: 2%;">
					    </div>
					</div>
			
					<div class="w3-row w3-section">
					  <div class="w3-col w3-blue w3-round w3-center" style="width:100px;">전화번호</div>
					    <div class="w3-rest">
					      <input class="w3-input"  name="tel" type="text" value="${user.tel}" style="margin-left: 2%;">
					    </div>
					</div>
			
					<div class="w3-row w3-section">
					  <div class="w3-col w3-blue w3-round w3-center" style="width:100px;">생일</div>
					    <div class="w3-rest">
					      <input class="w3-input"  name="birth" type="date" value="${user.birth}" style="margin-left: 2%;">
					    </div>
					</div>
					
					<div class="w3-row w3-section">
					  <div class="w3-col w3-blue w3-round w3-center" style="width:100px;">사진</div>
					    <div class="w3-rest">
					      <input class="w3-input w3-left" type="file" size="60" maxlength="50" name="filename" value="${user.filename}" style="margin-left: 2%;">
					    </div>
					    <div class="w3-small w3-gray">
					    	* 수정할 경우, 다시 로그인해주세요.
					    </div>
					</div>
					<div class="w3-row w3-section">
					    <div class="w3-rest">
					      <input type="submit" value="수정" class="w3-button w3-right w3-large w3-pink">
						  <input type="hidden" name="email" value="${user.email}">
					      <input type="hidden" name="filename" value="${user.filename}">
					    </div>
					</div>
					
					
				</div>
			</form>
			
				
			<!-- 삭제 -->
			<form method="post" style="display: inline-block; margin-left: 10px;" class="w3-right" action="<%=request.getContextPath() %>/story/user_deleteUPro">
				<span class="w3-text-red" style="font-size: 9pt;"><b> * 버튼 클릭시 바로 탈퇴됩니다. </b> </span>
				<input type="submit" class="w3-button w3-red w3-small w3-margin" value="탈퇴">
				<!-- hidden으로  deleteUro에 넘기기!!!  -->
              	<input type="hidden" name="email" value="${user.email}">
				<input type="hidden" name="pwd" value="${user.pwd}">
			</form>
		
		</div>
		<!-- end. 양 옆 간격 -->
	
	</div>		
	<!-- end. 상단 바, 사이드 바 간격 -->

</div>
<!-- end. 전체 틀 div ★ --> 
</body>
</html>