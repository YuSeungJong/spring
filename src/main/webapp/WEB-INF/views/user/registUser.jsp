<%@page import="kr.or.ddit.user.model.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Jsp</title>

<!-- Bootstrap core CSS -->
<script src="${cp}/js/jquery-1.12.4.js"></script>
<script src="${cp}/js/bootstrap.js"></script>
<!-- Custom styles for this template -->
<link href="${cp}/css/bootstrap.css" rel="stylesheet">
<link href="${cp}/css/dashboard.css" rel="stylesheet">
<link href="${cp}/css/blog.css" rel="stylesheet">

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
	$(function() {
	
		
		idcheck = true;
		// 주소검색 버튼이 클릭 되었을 때 다음주소 api 팝업을 연다
		$("#addrBtn").on("click",function(){
		    new daum.Postcode({
		        oncomplete: function(data) {
		            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
		            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
		            $("#userAddr1").val(data.roadAddress);	//도로주소
		            $("#userZip").val(data.zonecode);		//우편번호
		            
		            //사용자 편의성을 위해 상세주소 입력 input 태그로 focus 설정
		            $("#userAddr2").focus();
		        }
		    }).open();
			
		})
		
		$("#idCheckBtn").on("click",function(){
			path = "${cp}";
			userId = $("#userId").val();
			$.ajax({
				url : path+"/userCheck",
				type : "post",
				data : {"userId" : userId},
				success : function (res) {
					if(res.cnt==1){
						alert("중복된 아이디입니다.")
						idcheck = false;
					}else{
						alert("사용가능 아이디입니다.")
						idcheck = true;
					}
				
				},
				error : function(xhr) {
					alert("상태 : " + xhr.status)
				},
				dataType : "json"
			})
		})
		$("#regBtn").on("click",function(){
			if(idcheck){
				$("#regfrm").submit();
			}else{
				alert("아이디 중복검사를 확인하세요");
			}
		
		})
		
	})
</script>

</head>

<body>
	
	
	<%@ include file="/WEB-INF/views/common/header.jsp" %> 
	
	
	
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">JSP/SPRING</a>
				
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">Dashboard</a></li>
					<li><a href="#">Settings</a></li>
					<li><a href="#">Profile</a></li>
					<li><a href="#">Help</a></li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
				</form>
			</div>
		</div>
	</nav>
	<div class="container-fluid">
		<div class="row">

			<div class="col-sm-3 col-md-2 sidebar">
				<%@include file="/WEB-INF/views/common/left.jsp" %>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			
				<form method="post" class="form-horizontal" id="regfrm" role="form" action="${cp}/user/registUser" enctype="multipart/form-data">
																							

				
					
					<div class="form-group">
						<label for="userId" class="col-sm-2 control-label">사용자 아이디</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="userId" name="userid"
								placeholder="아이디입력" value="${user.userid}">
							<!-- 특정커멘드 객체에 특정 필드에 에러가 있나없나 -->
							<span style="color:red"><form:errors path="userVo.userid"/></span>
							
							<input type="file" class="form-control" name="profile"/>
						</div>
						<div class="col-sm-2">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="button" id= "idCheckBtn" class="btn btn-default">중복 확인</button>
							</div>
						</div>
					</div>
					
					
					<div class="form-group">
						spring message : <spring:message code="GREETTING" arguments="${S_USER.usernm}"/>
						<label for="userNm" class="col-sm-2 control-label">사용자 이름</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="userNm" name="usernm"
								placeholder="이름입력" value="${user.usernm}">
						</div>
					</div>
					
					<div class="form-group">
						<label for="userNm" class="col-sm-2 control-label">별명</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="userAlias" name="alias"
								 placeholder="별명입력" value="${user.alias}">
						</div>
					</div>
					
					<div class="form-group">
						<label for="userNm" class="col-sm-2 control-label">비밀번호</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="userPass" name="pass"
								placeholder="비밀번호 입력" value="${user.pass}">
						</div>
					</div>
					
				
					
					<div class="form-group">
						<label for="userNm" class="col-sm-2 control-label">도로주소</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="userAddr1" name="addr1"
								placeholder="도로주소" readonly value="${user.addr1}">
						</div>
						<div class="col-sm-2">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="button" id= "addrBtn" class="btn btn-default">주소 검색</button>
							</div>
						</div>
					</div>
					
					
					
					
					<div class="form-group">
						<label for="userNm" class="col-sm-2 control-label">상세주소</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="userAddr2" name="addr2"
								 placeholder="상세주소" value="${user.addr2}">
						</div>
					</div>
					
					<div class="form-group">
						<label for="userNm" class="col-sm-2 control-label">우편번호</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="userZip" name="zipcode"
								 placeholder="우편번호" readonly value="${user.zipcode}">
						</div>
					</div>
					
					

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" id="regBtn" class="btn btn-default">사용자 등록</button>
							
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
