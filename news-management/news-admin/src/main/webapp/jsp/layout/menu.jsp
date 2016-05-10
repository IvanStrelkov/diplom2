<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<style>
	<%@include file='/css/menu.css' %>
</style>
</head>
<body>
	<table class="menu">
		<tr>
			<td>
				<ul>
					<li>
						<c:choose>
							<c:when test="${menu == 'listNews'}">
								<a href="${pageContext.request.contextPath}/listNews" class="strong">
									<spring:message code="menu.listNews"/>
								</a>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/listNews">
									<spring:message code="menu.listNews"/>
								</a>
							</c:otherwise>
						</c:choose> 
					</li>
					<li>
						<c:choose>
							<c:when test="${menu == 'addNews'}">
								<a href="${pageContext.request.contextPath}/addNews" class="strong">
									<spring:message code="menu.addNews"/>
								</a>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/addNews">
									<spring:message code="menu.addNews"/>
								</a>
							</c:otherwise>
						</c:choose> 
					</li>
					<li>
						<c:choose>
							<c:when test="${menu == 'authors'}">
								<a href="${pageContext.request.contextPath}/listAuthors" class="strong">
									<spring:message code="menu.authors"/>
								</a>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/listAuthors">
									<spring:message code="menu.authors"/>
								</a>
							</c:otherwise>
						</c:choose> 
					</li>
					<li>
						<c:choose>
							<c:when test="${menu == 'tags'}">
								<a href="${pageContext.request.contextPath}/listTags" class="strong"> 
									<spring:message	code="menu.tags" />
								</a>
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath}/listTags">
									<spring:message code="menu.tags"/>
								</a>
							</c:otherwise>
						</c:choose> 
					</li>
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>