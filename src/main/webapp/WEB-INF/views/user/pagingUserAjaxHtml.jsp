<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<c:forEach items="${userList}" var="user">
	<tr class="user" data-userid="${user.userid}">
		<td>${user.userid}</td>
		<td>${user.usernm}</td>
		<td>${user.alias}</td>
		<td><fmt:formatDate value="${user.reg_dt}" pattern="yyyy.MM.dd" /></td>
	</tr>
</c:forEach>

####################

<li class="prev">
	<a href="javascript:pagingUserAjax(1,${pageVo.pageSize});">«</a>
</li>
	
<c:if test="${startPage!=1 && startPage!=0}">
	<li><a
		href="javascript:pagingUserAjax(1,${pageVo.pageSize});">1</a></li>
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
				<li>
					<a href="javascript:pagingUserAjax(${i},${pageVo.pageSize});">${i}</a>
					
				</li>
			</c:if>
		</c:otherwise>
	</c:choose>
</c:forEach>

<c:if test="${endPage!=pagination}">
	<li><span>...</span></li>
	<li>
		<a href="javascript:pagingUserAjax(${pagination},${pageVo.pageSize});">${pagination}</a>
	</li>
</c:if>
<li class="next">
	<a href="javascript:pagingUserAjax(${pagination},${pageVo.pageSize});">»</a>
</li>
