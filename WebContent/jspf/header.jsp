<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<style type="text/css">
			.watch {
				width: 132px;
				height: 132px;
				background: black;
				border-radius: 50%;
				display: -webkit-box;
				display: -webkit-flex;
				display: -ms-flexbox;
				display: flex;
				-webkit-box-pack: center;
				-webkit-justify-content: center;	
				-ms-flex-pack: center;
				justify-content: center;
				-webkit-box-align: center;
				-webkit-align-items: center;
				-ms-flex-align: center;
				align-items: center;
				position: relative;
				overflow: hidden;
				border: 7px solid #111;
			}
			
			.watch--hour {
				width: 5px;
				height: 80px;
				border-radius: 10px;
				-webkit-transform-origin: center 65px;
				transform-origin: center 65px;
				-webkit-transform: rotate(0deg);
				transform: rotate(0deg);
				position: absolute;
				background: #111;
				left: calc(50% - 2.5px);
				top: calc(50% - 65px);
			}
			
			.watch--min {
				width: 5px;
				height: 110px;
				border-radius: 10px;
				-webkit-transform-origin: center 90px;
				transform-origin: center 90px;
				-webkit-transform: rotate(0deg);
				transform: rotate(0deg);
				position: absolute;
				background: gray;
				left: calc(50% - 2.5px);
				top: calc(50% - 90px);
			}
			
			.watch--sec {
				width: 4.5px;
				height: 117px;
				border-radius: 9px;
				-webkit-transform-origin: center 100px;
				transform-origin: center 100px;
				-webkit-transform: rotate(0deg);
				transform: rotate(0deg);
				position: absolute;
				background: #670000;
				left: calc(50% - 2.5px);
				top: calc(50% - 100px);
			}
			
			.watch--center {
				position: absolute;
				width: 10px;
				height: 10px;
				background: black;
				border-radius: 50%;
				left: calc(50% - 7px);
				top: calc(50% - 7px);
				border: 2px solid #ddd;
			}
			
			.day {
				width: 132px;
				height: 132px;
				position: absolute;
				background: #789abc;
				left: 0;
				top: 0;
			}
			
			.day--ground {
				position: absolute;
				width: 132px;
				height: 48px;
				background: green;
				bottom: 0;
			}
			
			.day--sun {
				width: 60px;
				height: 60px;
				background: yellow;
				border-radius: 50%;
				position: absolute;
				left: 60px;
				top: 35px;
			}
			
			.day--clouds {
				position: absolute;
				width: 50px;
				height: 50px;
				background: #ddd;
				border-radius: 50%;
				left: 110px;
				top: 35px;
			}
			
			.day--clouds::before {
				content: "";
				width: 25px;
				position: absolute;
				height: 25px;
				background: #ddd;
				left: -10px;
				top: 20px;
				border-radius: 50%;
			}
			.day--clouds::after {
				content: "";
				width: 40px;
				position: absolute;
				height: 40px;
				background: #ddd;
				left: 30px;
				top: 10px;
				border-radius: 50%;
			}
		</style>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script type="text/javascript">
			$(document)
				.ready(
				function() {
					var seconds = new Date().getSeconds(), minutes = new Date()
							.getMinutes(), hours = new Date()
							.getHours(), sdegree = seconds * 6, mdegree = minutes * 6, hdegree = hours * 30;

					$('.watch--sec').css('transform',
							'rotate(' + sdegree + 'deg)');
					$('.watch--min').css('transform',
							'rotate(' + mdegree + 'deg)');
					$('.watch--hour').css('transform',
							'rotate(' + hdegree + 'deg)');

					setInterval(
							function() {
								var seconds = new Date()
										.getSeconds(), minutes = new Date()
										.getMinutes(), hours = new Date()
										.getHours(), sdegree = seconds * 6, mdegree = minutes * 6, hdegree = hours * 30;
								$('.watch--sec').css(
										'transform',
										'rotate(' + sdegree
												+ 'deg)');
								$('.watch--min').css(
										'transform',
										'rotate(' + mdegree
												+ 'deg)');
								$('.watch--hour').css(
										'transform',
										'rotate(' + hdegree
												+ 'deg)');
							}, 1000)

				})
		</script>
		<style>
			body,h1 {font-family: "consolas", sans-serif}
			.bgimg {
			    background-image: url("/Story_Blog_MyBatis/Project/img/back2_babypink.jpg");
			    min-height: 100%;
			    background-position: center;
			    background-size: cover;		
			}
		</style>
		
		
		
		<script type="text/javascript">
	        // 로그아웃 담당 JSP로 이동
	        function logoutPro(){
	            location.href="/Story_Blog_MyBatis/story/LogoutPro";
	       	 }
		 </script>
		 
		 
	
		 
	<title>Story Blog - User</title>
</head>


<!-- Body - 전부: 배경, 모바일 대응 -->
<body class="bgimg">
<%-- <c:if test=" ${sessionScope eq admin}">
<jsp:forward page="/Story_Blog_m2/story/ad_main"/>
</c:if> --%>

<%--- <%
        if(session.getAttribute("sessionID") == null) // 로그인이 안되었을 때
        { 
            // 로그인 화면으로 이동
            response.sendRedirect("/Story_Blog_m2/story/index");
        } else if(session.getAttribute("sessionID").equals("admin")) {
        	// 회원관리 화면으로 이동
            response.sendRedirect("/Story_Blog_m2/story/ad_main");
        } 
        else // 로그인 했을 경우
        {
%> --%>
<!-- 전체 틀 div ★ -->     
<div>


<!-- ALL 상단바 -->
<div class="w3-bar" style="position: fixed;">
<!-- 상단바 -->
	<div class="w3-bar w3-black">
	<!-- 검색 미구현 / 보류 -->
	<!-- <a href="http://www.google.com" class="w3-bar-item w3-button w3-pink w3-hover-pink w3-right">Search</a> -->
	<input class="w3-button w3-center w3-blue w3-hover-blue w3-right w3-border-right" type="button" value="Logout" onclick="logoutPro()" />
	<a href="#" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 1%;">!</a>
		<label class="w3-right w3-small" style="color: white; margin:10px 10px 0px 0px;"> <b>${name}</b> 님 환영합니다.</label>
		
		
	</div>
<!-- end. 상단바 -->

<!-- 타임라인바 -->
	<div class="w3-bar w3-white">
		<a href="#" class="w3-bar-item w3-button" style="width: 10%">&nbsp;</a>
		<div>
			<c:if test="${sessionID ne 'admin'}">
				<a href="<%=request.getContextPath() %>/story/user_main" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 13.3%; margin-left: 25%;">Main</a>
				<a href="<%=request.getContextPath() %>/story/user_timeline" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 13.3%;">Timeline</a>
				<a href="<%=request.getContextPath() %>/story/user_gallery" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 13.3%; margin-right: 25%;">Gallery</a>
			</c:if>
			<c:if test="${sessionID eq 'admin'}">
				<a href="<%=request.getContextPath() %>/story/user_main" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 10%; margin-left: 25%;">Main</a>
				<a href="<%=request.getContextPath() %>/story/user_timeline" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 10%;">Timeline</a>
				<a href="<%=request.getContextPath() %>/story/user_gallery" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 10%;">Gallery</a>
				<a href="<%=request.getContextPath() %>/story/admin/accountList" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 10%; margin-right: 25%;">Account List</a>
			</c:if>	
			<%-- <a href="<%=request.getContextPath() %>/story/user_map" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 15%;">Map</a>
			<a href="<%=request.getContextPath() %>/story/user_calender" class="w3-bar-item w3-button w3-pink w3-border-right" style="width: 15%;">Calendar</a> --%>
		
		</div>
	</div>
<!-- end. 타임라인바 -->
</div>


<!-- end. ALL 상단바 -->

<!-- 사이드바 -->
	<div class="w3-sidebar w3-bar-block" style="width: 10%; position: fixed;"> 
			
			<div class="w3-container w3-center">
			
				<p class="w3-center w3-text-blue" style="font-size: 14.5pt; font-family: fantasy;">
					<a href="#" 
					style="text-decoration: none;">Story</a>
				</p>
				
			</div>
		<div class="w3-card w3-round w3-white">
        	<h4 class="w3-center">
        		<b>${name}</b>
        		<!-- 설정이미지로 이동 (잠시 잠금) -->
        		<%-- <a href="<%=request.getContextPath() %>/story/user_set">
        		<img src="/Story_Blog_m2/Project/img/settings.png" style="height:16px;width:16px; display: inline-block; margin-right: -19px;">
        		</a> --%>
        	</h4>
        	
         	<p class="w3-center"><img src="/Story_Blog_MyBatis/userSave/${filename}" alt="${filename}" class="w3-circle" style="height:106px;width:106px"></p>
         	<br>
        
      	</div>
      	<button class="w3-button w3-bar-item w3-center w3-light-gray" onclick="location.href='<%=request.getContextPath() %>/story/user_set'">My Page</button>
      	
      	<!-- <div class="w3-bar-item w3-center w3-margin-left w3-margin-right" style="margin-top: 50%;"> -->
      	<div style="margin-left: 5%; margin-top: 50%;">
	      	<div class="watch">
				<div class="watch--inner"></div>
				<div class="watch--day day">
					<div class="day--sun"></div>
					<div class="day--clouds"></div>
					<div class="day--ground"></div>
				</div>
				<div class="watch--night night">
					<div class="night--moon"></div>
					<div class="night--clouds"></div>
					<div class="night--ground"></div>
				</div>
				<div class="watch--hour"></div>
				<div class="watch--min"></div>
				<div class="watch--sec"></div>
				<div class="watch--center"></div>
			</div>
		</div>
      	
      	<!--##### 나중에 만들기 #####-->
		<!-- <a href="#" class="w3-bar-item w3-button w3-hover-pink w3-border-bottom" onClick="alert('준비중입니다.')">Story 1</a>
		<a href="#" class="w3-bar-item w3-button w3-hover-pink w3-border-bottom" onClick="alert('준비중입니다.')">Story 2</a>
		<button class="w3-button w3-circle w3-pink w3-center w3-card-4"  onClick="alert('준비중입니다.')" style="font-size: 18px; margin:36% 0% 0% 36%">+</button> -->
	
	</div>
<!-- end. 사이드바 -->
</div>
<!-- end. 전체 틀 div ★ -->   

		<%-- <div class="footer" style="opacity: 0.4; margin-top: 3%;">
			<p>Powered by <a href="<%=request.getContextPath() %>/Project/index.jsp" target="_blank">Story Blog</a></p>
		</div> --%>
	
</body>
<!-- end. Body - 전부: 배경, 모바일 대응 -->

</html>