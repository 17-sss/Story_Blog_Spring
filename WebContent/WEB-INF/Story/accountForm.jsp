<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
	<title>Story Blog - Sign Up</title>
	<style type="text/css">
		body,h1 {font-family: "consolas", sans-serif}
		body, html {height: 100%}
		.bgimg {
		    background-image: url("${pageContext.request.contextPath}/img/back1.jpg");
		    min-height: 100%;
		    background-position: center;
		    background-size: cover;
		}
		.formpad {
			padding: 10% 10% 10% 10%;
			
		}
	
	</style>
	<script type="text/javascript">
	/* 이메일, 비번 체크 */
    function checkValue()
    {
		var form = document.userInfo;
		
        if(!form.email.value){
            alert("이메일을 입력하세요.");
            return false;
        }
        if(!form.name.value){
            alert("이름을 입력하세요.");
            return false;
        }
        
      
        if(!form.pwd.value){
            alert("비밀번호를 입력하세요.");
            return false;
        }
        
        if(form.pwd.value != form.passchk.value){
        	alert("비밀번호 확인란에 비밀번호를 동일하게 입력하세요.");
            return false;
        } 
        
       /*  if(form.idDuplication.value != "idCheck"){
            alert("아이디 중복체크를 해주세요.");
            return false;
        } */
        else {
        	alert("가입이 완료되었습니다.");
        }

    }
    /* end. 이메일, 비번 체크 */
	
    /* 이메일 중복확인 화면 띄우기 */
    /* function openIdChk() {
    	windows.name = "parentForm";
    	windows.open("/Story_Blog/Project/IdCheckForm.jsp", "chkForm", 
    			"width=500, height=300, resizable = no, scrollbars = no");
    }
    
    function inputIdChk() {
		document.userInfo.idDuplication.value = "idUncheck";
	} */

	function confirmEmail() {
		if (document.userInfo.email.value == "") {
			alert("이메일을 입력하세요. (중복확인)");
			return;
		}
		url = "confirmEmail?email=" + document.userInfo.email.value;
		open(url, "confirm", "toolbar=no, location=no, status=no, menubar=no, scrollbars=no, resizeble=no, width=400, height=150");
	}
</script>


</head>
<body>
<div class="bgimg formpad">
<form method="post" name="userInfo" enctype="multipart/form-data" class="w3-container w3-card-4 w3-white w3-text-pink w3-margin w3-animate-bottom w3-round" 
action="${pageContext.request.contextPath}/story/accountPro" onsubmit="return checkValue()">

<h2 class="w3-center">Sign Up</h2>
<h6 class="w3-right">*는 필수 입력입니다.</h6>

<!-- 이메일 (아이디) -->
<div class="w3-row w3-section">
  <div class="w3-col" style="width:50px"></div>
    <div class="w3-rest">
    
	   <div>
		 <input class="w3-button w3-blue w3-right" type="button" value="중복확인" onclick="confirmEmail(this.form)" style="font-size: 8pt;  display: inline-block;">	   
	     <input class="w3-input w3-border"  name="email" type="email" placeholder="E-mail (ID)* - 이메일 확인 후 가입해주세요." style="display: inline-block;">	
	     	<%-- <input type="hidden" name="email" value="<%= email %>"> --%>
	     
	   </div>
     <!-- <input class="w3-input w3-border"  name="email" type="email" placeholder="E-mail (ID)*" onkeydown="inputIdChk()">
     	  <input type="button" value="중복확인" onclick="openIdChk()">
      	  <input type="hidden" name="idDuplication" value="idUncheck"> -->
    </div>
</div>

<!-- 이름  -->
<div class="w3-row w3-section">
  <div class="w3-col" style="width:50px"></div>
    <div class="w3-rest">
      <input class="w3-input w3-border"  name="name" type="text" placeholder="Name*">
    </div>
</div>

<!-- 패스워드 -->
<div class="w3-row w3-section">
  <div class="w3-col" style="width:50px"></div>
    <div class="w3-rest">
      <input class="w3-input w3-border"  name="pwd" type="password" placeholder="Password*">
    </div>
</div>

<!-- 패스워드 확인  -->
<div class="w3-row w3-section">
  <div class="w3-col" style="width:50px"></div>
    <div class="w3-rest">
      <input class="w3-input w3-border"  name="passchk" type="password" placeholder="Password Check*">
    </div>
</div>

<!-- 연락처  -->
<div class="w3-row w3-section">
  <div class="w3-col" style="width:50px"></div>
    <div class="w3-rest">
	   <input class="w3-input w3-border"  name="tel" type="tel" placeholder="Phone">
    </div>
</div>

<!-- 생일 입력 -->
<div class="w3-row w3-section">
	<div class="w3-rest w3-input w3-border">
		 Birthday: 
			&nbsp;<input name="birth" type="date">
	</div>
</div>

<!-- 프로필 사진 입력 -->
<div class="w3-row w3-section">
	<div class="w3-rest w3-input w3-border">
		 Photo: 
			&nbsp;<input type="file" size="60" maxlength="50" name="filename">
	</div>
</div>

<p class="w3-center">
<input type="submit" value="Send" class="w3-button w3-pink">
<input type="button" value="Back" class="w3-button w3-red" OnClick="window.location='${pageContext.request.contextPath}/story/index'">  

</p>
</form>
</div>

</body>
</html>