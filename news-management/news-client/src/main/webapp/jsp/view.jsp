<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@	taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<style><%@include file='/css/view.css' %></style>
</head>
<body>
	<table	class="back">
		<tr>
			<td align = "left">
				<a href="${pageContext.request.contextPath}/back/${currentNews}"><spring:message code="button.back"/></a>
			</td>
		</tr>
	</table>
	<table class="news">
		<tr>	
			<td width="70%" align="left">
				<b>${news.news.title}</b>
			</td>
			<td width="20%" align="left">
				(by ${news.author.authorName})
			</td>
			<td width="10%">
				<p class="date">
					<spring:message var="dateStyle" code="pattern.date"/>
					<fmt:formatDate value="${news.news.modificationDate}" pattern="${dateStyle}"/>
				</p>
			</td>
		</tr>
		<tr>
			<td colspan="2" >
				${news.news.fullText}
			</td>
			<td>
			</td>
		</tr>
	</table>	
	<table class="comments">
		<c:forEach var="comment" items="${news.listComments}" varStatus="status">
			<tr>
				<td>
					<spring:message var="dateStyle" code="pattern.date"/>
					<p class="date">
						<fmt:formatDate value="${comment.creationDate}" pattern="${dateStyle}"/>
					</p>
				</td>
			</tr>
			<tr>
				<td class="commentText">
						${comment.commentText}
				</td>
			</tr>
		</c:forEach>
	</table>
	<form:form method="post" action="${pageContext.request.contextPath}/addComment" commandName="comment">  
		<table class="comments">
			<tr>
				<td>
						<input type="hidden" name="currentNews" value="${currentNews}">
						<input type="hidden" name="newsId" value="${news.news.newsId}">
						<br>
						<form:errors path="commentText"/>
						<form:textarea path="commentText" rows="4"/>	
				</td>
			<tr>
				<td align="right">
						<button><spring:message code="button.postcomment"/></button>
				</td>
			</tr>
			
		</table>
	</form:form>
</body>
</html>