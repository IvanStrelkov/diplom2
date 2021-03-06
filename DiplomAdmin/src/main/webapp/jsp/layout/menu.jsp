<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet"
	href="<c:url value="/resources/css/layout/menu.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/lib/bootstrap-multiselect.css"/>" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
</head>
<body>
	<table class="menu">
		<tr>
			<td>
				<ul>
					<li><a href="${pageContext.request.contextPath}/admin/portal">
							<spring:message code="menu.listNews" />
					</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/addNews">
							<spring:message code="menu.addNews" />
					</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/listAuthors"> <spring:message
								code="menu.authors" />
					</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/listTags"> <spring:message
								code="menu.tags" />
					</a></li>
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>