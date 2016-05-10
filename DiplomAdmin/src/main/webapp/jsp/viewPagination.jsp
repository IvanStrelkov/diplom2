<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<style><%@include file='/css/view.css' %></style>
</head>
<body>
	<table class="pagination">
		<tr>
			<td align="left">
				<c:if test="${currentNews gt 1}">
					<a href="${pageContext.request.contextPath}/view/${currentNews-1}/${previousId}"><spring:message code="button.previous"/></a>
				</c:if>			
			</td>
			<td align="right">
				<c:if test="${currentNews lt numberNews}">
					<a href="${pageContext.request.contextPath}/view/${currentNews+1}/${nextId}"><spring:message code="button.next"/></a>
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>