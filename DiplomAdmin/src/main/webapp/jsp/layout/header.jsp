<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<style>
	<%@include file='/css/header.css' %>
</style>
</head>
<body>
 <table class="header">
 	<tr>
 		<td align="left"  width="90%">
 			<h1 class="header"><spring:message code = "label.news"/></h1>
 		</td>
 		<td align="right" width="10%" valign="bottom">
 			<security:authorize access="isAuthenticated()">
	 			<form action="${pageContext.request.contextPath}/logout">
	 				<button>
	 					<spring:message code="button.logout"/>
	 				</button>
	 			</form>
	 		</security:authorize>
 			<a href="?language=en"><spring:message code = "label.en"/></a> 
 			<a href="?language=ru"><spring:message code = "label.ru"/></a>
 		</td>
 	</tr>
 </table>
</body>
</html>