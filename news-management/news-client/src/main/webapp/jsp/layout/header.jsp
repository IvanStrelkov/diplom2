<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
 			<a href="?language=en"><spring:message code = "label.en"/></a> 
 			<a href="?language=ru"><spring:message code = "label.ru"/></a>
 		</td>
 	</tr>
 </table>
</body>
</html>