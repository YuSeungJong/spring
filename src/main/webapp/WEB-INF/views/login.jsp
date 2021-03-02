<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Signin Template for Bootstrap **</title>
	<%@include file="/WEB-INF/views/common/common_lib.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/js-cookie@rc/dist/js.cookie.min.js"></script>
    
    <!-- Custom styles for this template -->
    <link href="${cp}/css/signin.css" rel="stylesheet">
   
    
	<script>
	
		function getCookieValue(cookieStr, cookieName){
			cookies = cookieStr.split("; ");
			
			var result = "";
			
			for(i = 0; i<cookies.length; i++) {
				cookies1 = cookies[i].split("=");
				alert(cookies1[0]);
				alert(cookies1[1]);
				alert(cookieName);
				if(cookieName == cookies1[0]) {
					
					result = cookies1[1];
					
				}
				
			}
			return result;
		}
		//cookieName : 추가 하고자 하는 쿠키이름
		//cookieValue : 쿠키의 값
		//expires : 유효기간(일수)
		function addCookie(cookieName, cookieValue, expires){
			var dt = new Date();	// 현재 날짜 ==> expires 만큼 미래날짜로 변겅
			dt.setDate(dt.getDate()+ parseInt(expires));
			console.log(dt);
			document.cookie= cookieName + "=" + cookieValue +"; "+
								"path=/; expires="+dt.toGMTString();
		}
		
		function deleteCookie(cookieName){
			addCookie(cookieName,"",-1)
		}
		$(function() {
			
			<c:if test="${msg != null}">
				alert("${msg}"+ "ra");
		    </c:if>
			
		/* 	
			if("${msg}"!= ""){
				alert("${msg}"+ "c:remove");
				<c:remove var="msg"/>
				
			}
				 */
			
			
			//userid, rememberme 쿠키를 확인하여 존재할 경우 값설정, 체크
			if(Cookies.get('rememberme')=="Y"){
				$("#userid").val(Cookies.get('userid'))
				$("#rememberme").attr("checked", true);
			}
			
			
			//singin 아이디를 select
			$("#signin").on("click",function(){
				// rememberme 체크박스가 체크되어 있는지 확인
				// 체크되어있을 경우
				if($("#rememberme").is(":checked")){
					// userid input에 있는 값을 userid쿠키로 저장
					Cookies.set("userid",$("#userid").val());
					// rememberme 쿠키로 Y값을 저장			
					Cookies.set("rememberme","Y");
					
				}else{
					// 체크 해제되어 있는 경우 : 더이상 사용하지 않는다는 의미 이므로 userid, rememberme 쿠키 삭제
					Cookies.remove("userid");
					Cookies.remove("rememberme");
				}
				
				// form태그를 이용해서 signin 요청
				$("#frm").submit();
			});
			
		});
		
		
		
	</script>

  </head>

  <body>

    <div class="container">
      <form class="form-signin" id="frm" action="${cp}/login/process" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">userid</label>
        <input type="text" name="userid"  id="userid" class="form-control" placeholder="아이디 입력" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="pass"  name="pass" class="form-control" placeholder="패스워드 입력" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" id="rememberme" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="button" id="signin">Sign in</button>
      </form>

    </div> <!-- /container -->


  </body>
</html>
