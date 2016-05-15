<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<c:url var="style_css" value="/resources/css/style.css"/>
<link rel="stylesheet" href="${style_css}"/>
</head>
<body>
<c:if test="${not empty param.error}">
	<font color="red"> <spring:message code="label.loginerror" />
	: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} </font>
</c:if>
<form method="POST" action="<c:url value="/j_spring_security_check" />">
<table class="login">
	<tr>
		<td align="right"><spring:message code="label.login" /></td>
		<td><input type="text" name="j_username" /></td>
	</tr>
	<tr>
		<td align="right"><spring:message code="label.password" /></td>
		<td><input type="password" name="j_password" /></td>
	</tr>
	<tr>
		<td colspan="2" align="right">
			<button>
				<spring:message code="button.login"/>
			</button>
		<td>
	</tr>
</table>
</form>
</body>
</html>