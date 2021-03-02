<%@page import="kr.or.ddit.common.model.PageVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script>
	//문서 로딩이 완료되고 나서 실행되는 영역
	$(function() {
		$(".user").on("click", function() {
			//this : 클릭 이벤트가 발생한 element
			// data-속성명 data-userid, 속성명은 대소문자 무시하고 소문자로 인식
			// data-userId ==> data-userid
			var userid = $(this).data("userid");
			$("#userid").val(userid);

			$("#frm").submit();
		})
	});
</script>
	
	<form id="frm" action="${cp}/user/userInfo">
		<input type="hidden" id="userid" name="userid" value="">
	</form>
	
	<div class="row">
		<div class="col-sm-8 blog-main">
			<h2 class="sub-header">사용자(tiles)</h2>
			<div class="table-responsive">
				<table class="table table-striped">
					<tr>
						<th>사용자 아이디</th>
						<th>사용자 이름</th>
						<th>사용자 별명</th>
						<th>등록일시</th>
					</tr>
					<c:if test="${userList != null }">
						<c:forEach items="${userList}" var="user">
							<tr class="user" data-userid="${user.userid}">
								<td>${user.userid}</td>
								<td>${user.usernm}</td>
								<td>${user.alias}</td>
								<td><fmt:formatDate value="${user.reg_dt}"
										pattern="yyyy.MM.dd" /></td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</div>
		
		
		
			<a class="btn btn-default pull-right" href="${cp}/user/registUser">사용자
				등록</a> <a class="btn btn-default pull-right"
				href="${cp}/user/excelDownload">사용자 엑셀다운로드</a>
	
			<div class="text-center">
	
				<ul class="pagination">
					<li class="prev"><a
						href="${cp}/user/pagingUser?page=1&pageSize=${pageVo.pageSize}">«</a>
					</li>
					<c:if test="${startPage!=1 && startPage!=0}">
						<li><a
							href="${cp}/user/pagingUser?page=1&pageSize=${pageVo.pageSize}">1</a></li>
					</c:if>
					<c:forEach begin="${startPage}" end="${endPage}" var="i">
						<c:if test="${startPage!=1 && i==startPage}">
							<li><span>...</span></li>
						</c:if>
						<c:choose>
							<c:when test="${i == pageVo.page}">
								<li class="active"><span>${i}</span></li>
							</c:when>
							<c:otherwise>
								<c:if test="${i != 0}">
									<li><a
										href="${cp}/user/pagingUser?page=${i}&pageSize=${pageVo.pageSize}">${i}</a></li>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
	
					<c:if test="${endPage!=pagination}">
						<li><span>...</span></li>
						<li><a
							href="${cp}/user/pagingUser?page=${pagination}&pageSize=${pageVo.pageSize}">${pagination}</a></li>
					</c:if>
					<li class="next"><a
						href="${cp}/user/pagingUser?page=${pagination}&pageSize=${pageVo.pageSize}">»</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	
