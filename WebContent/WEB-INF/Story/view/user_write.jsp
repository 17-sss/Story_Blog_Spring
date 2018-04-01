<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Story Blog - User Write</title>
<style type="text/css">
	.w3-input {
		padding: 8px;
		display: block;
		border: none;
		border-bottom: 1px solid #ccc;
		width: 98%;
	}
</style>

<!-- SmartEditor를 사용하기 위해서 다음 js파일을 추가 (경로 확인) -->
<script type="text/javascript" src="${pageContext.request.contextPath}/SE2/js/service/HuskyEZCreator.js" charset="EUC-KR"></script>
<!-- jQuery를 사용하기위해 jQuery라이브러리 추가 -->
<script type="text/javascript" src="http://code.jquery.com/jquery-3.3.1.min.js"></script>



</head>
<body>
<!-- 전체 틀 div ★ --> 
<div>&nbsp;
	<!-- form -->
	
	<form id="frm" action="${pageContext.request.contextPath}/story/user_writePro" enctype="multipart/form-data" method="post">
	<input type="hidden" name="diaryid" value="${diaryid}">
	<input type="hidden" name="num" value="${num}">
		
		<!-- 상단 바, 사이드 바 간격 -->
		<div style="margin-top:54px; margin-left: 10%;"><br>
			
			<!-- 양 옆 간격 -->
			<div style="margin-left: 13%; margin-right: 13%;">
				<!-- 작성 폼 -->
				<div class="w3-container w3-card w3-round w3-white">
				 	
					<div class="w3-container w3-card w3-round w3-white" style="margin: 5%;">
					
						<!-- 묶음 -->
						<div class="w3-border w3-margin  w3-text-pink">
							
							<!-- 일기장 분류 (select) / 미완이라 봉인 -->
							<%-- <div class="w3-row w3-section">
								<div class="w3-content">
				 					일기장 선택
									&nbsp;
									<select name="diaryid">
			   							<!-- <option value="">Select</option> -->
									    <option value="${diaryid}">${diaryid}</option>
									</select>
								
									<input class="w3-input" name="diaryid" type="text" value="${diaryid}">
								</div>
							</div> --%>
	
							<!-- 제목 -->
							<div class="w3-row w3-section">
								<div class="w3-content">
				 					제목 
									&nbsp;<input class="w3-input" name="subject" type="text" size="60" value="${subject}" />
								</div>
							</div>
							
							<!-- 날짜   / 날짜 수정 불가..방법이.. -->
							<div class="w3-row w3-section">
								<div class="w3-content">
				 					날짜 
									&nbsp;<span class="w3-input w3-light-gray w3-text-gray" style="font-size: 9pt;">현재 시간으로 등록됩니다.</span>
								</div>
							</div>
							<!-- <div class="w3-row w3-section">
								<div class="w3-content">
				 					날짜
									&nbsp;<input class="w3-input" name="cdate" type="text" value="" readonly>
								</div>
							</div> -->
					
							<!-- 파일업로드  -->
							<!-- <div class="w3-row w3-section">
								<div class="w3-content">
				 					파일 
									&nbsp;<input class="w3-input" name="filename" type="file" style="font-size: 9pt;" size="60" maxlength="50"/>
								</div>
							</div> -->
							
							<!-- 파일업로드 - 다중테스트  --> 
							<div class="w3-row w3-section">
								<div class="w3-content" style="font-size: 11pt;">
				 					<b>파일 1 [갤러리 메인 사진]</b>
									&nbsp;<input class="w3-input w3-light-blue" name="filename0" type="file" style="font-size: 9pt;" size="60" maxlength="50"/>
								</div>
							</div>
							
							<div class="w3-row w3-section">
								<div class="w3-content" style="font-size: 9pt;">
				 					파일 2 <span class="w3-border" style="font-size: 9pt; color: red;">■■■■</span>
									&nbsp;<input class="w3-input" name="filename1" type="file" style="font-size: 7pt;" size="60" maxlength="50"/>
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-content" style="font-size: 9pt;">
				 					파일 3 <span class="w3-border" style="font-size: 9pt; color: #FFFF8F;">■■■■</span>
									&nbsp;<input class="w3-input" name="filename2" type="file" style="font-size: 7pt;" size="60" maxlength="50"/>
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-content" style="font-size: 9pt;">
				 					파일 4 <span class="w3-border" style="font-size: 9pt; color: green;">■■■■</span>
									&nbsp;<input class="w3-input" name="filename3" type="file" style="font-size: 7pt;" size="60" maxlength="50"/>
								</div>
							</div>
							<div class="w3-row w3-section">
								<div class="w3-content" style="font-size: 9pt;">
				 					파일 5 <span class="w3-border" style="font-size: 9pt; color: #F6F6F6;">■■■■</span>
									&nbsp;<input class="w3-input" name="filename4" type="file" style="font-size: 7pt;" size="60" maxlength="50"/>
								</div>
							</div>
							
							<!-- 내용 (SE2) -->
							<div class="w3-row w3-section">
								<div class="w3-content">&nbsp;내용
									<textarea  id="ir1" class="w3-input w3-border" rows="10" cols="30" 	name="content">
									</textarea>
									<!-- <textarea id="ir1" class="w3-input w3-border" rows="10" cols="30"
										style="width: 950px; height: 400px;" name="content">
									</textarea> -->
								</div>
							</div>
							
						</div>
						<!--end. 묶음 -->
						
												
						
						<!-- filename, size는 나중에..ㅠㅠㅠ -->
						
						<!-- 전송 -->
						<div class="w3-center" style="margin: 1%;">
							<input class="w3-button w3-white" type="button" onclick="submitContents(this);" value="저장" />
							&nbsp;
							<input class="w3-button w3-yellow" type="button" value="취소" onClick = "history.back();"/>
						</div>
						
					</div>
				</div>
				<!-- end. 작성폼 -->
			</div>
			<!-- end. 양 옆 간격 -->
		</div>
		<!-- end. 상단 바, 사이드 바 간격 -->
	</form>
	<!-- end. form -->
	
</div>
<script type="text/javascript">
	var oEditors = [];
	
	//추가 글꼴 목록
	//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
	
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "ir1",
		sSkinURI: "${pageContext.request.contextPath}/SE2/SmartEditor2Skin.html",	
		htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
			fOnBeforeUnload : function(){
				//alert("완료!");
			}
		}, //boolean
		fOnAppLoad : function(){
			//예제 코드
			//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
		},
		fCreator: "createSEditor2"
	});
	
	function pasteHTML() {
		var sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
		oEditors.getById["ir1"].exec("PASTE_HTML", [sHTML]);
	}
	
	function showHTML() {
		var sHTML = oEditors.getById["ir1"].getIR();
		alert(sHTML);
	}
		
	function submitContents(elClickedObj) {
		oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
		
		// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("ir1").value를 이용해서 처리하면 됩니다.
		
		try {
			elClickedObj.form.submit();
		} catch(e) {}
	}
	
	function setDefaultFont() {
		var sDefaultFont = '궁서';
		var nFontSize = 24;
		oEditors.getById["ir1"].setDefaultFont(sDefaultFont, nFontSize);
	}
</script>
<!-- end. 전체 틀 div ★ --> 
</body>
</html>

