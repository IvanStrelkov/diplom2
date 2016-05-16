<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/layout/header.css"/>"/>
<link rel="stylesheet" href="<c:url value="/resources/css/lib/bootstrap-multiselect.css"/>"/>
<link rel="stylesheet" href="<c:url value="/resources/css/lib/bootstrap.css"/>"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
</head>
<body>
 <table class="header">
 	<tr>
 		<td align="left"  width="90%">
 			<h1 class="header"><spring:message code = "label.news"/></h1>
 		</td>
 		<td align="right" width="10%" valign="bottom">
 			<a class="header" href="?language=en"><spring:message code = "label.en"/></a> 
 			<a class="header" href="?language=ru"><spring:message code = "label.ru"/></a>
 		</td>
 	</tr>
 </table>
</body>
</html>