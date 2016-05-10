<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<style>
	<%@include file='/css/list.css' %>
</style>
</head>
<body>
	<div id="pagination">
			<c:forEach begin="1" end="${countPages}" var="i">
					<div class="page">
						<c:choose>
							<c:when test="${ i == currentPage}">
								<button disabled>${i}</button>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/list/${i}">
									<button>${i}</button>
								</a>
							</c:otherwise>
						</c:choose>
					</div>
			</c:forEach>
	</div>
</body>
</html>