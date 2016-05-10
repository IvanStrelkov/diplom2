<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@	taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<style>
	<%@include file='/css/select.css' %>
	<%@include file='/css/editNews.css' %>
</style>
<script><%@include file='/js/select.js' %></script>
</head>
<body>
	<form:form method="post" action="${pageContext.request.contextPath}/saveNews" commandName="news">  
		<input type="hidden" name="newsNumber" value="${newsNumber}"> 
		<form:hidden path="newsId"/>
     	<table class="edit">
     		<tr>
     			<td width="10%"> </td>
     			<td width="40%"> </td>
     			<td width="25%"> </td>
     			<td width="25%"> </td>
     		</tr>
			<tr>
				<td>
					<b><spring:message code="label.title" />: </b>
				</td>
				<td colspan="3">
					<form:errors path="title"/>
					<form:input class="title" path="title"/>
				</td>
			</tr>
			<tr>
				<td>
					<b><spring:message code="label.date" />: </b>
				</td>
				<td colspan="3">
					<spring:message var="dateStyle" code="pattern.date"/>
					<spring:message var="regularDate" code="regular.date"/>
					<c:choose>
						<c:when test="${news.newsId==0}">
							<form:errors path="creationDate"/>
							<fmt:formatDate value="${news.creationDate}" var="dateString" pattern="${dateStyle}" />
							<form:input path="creationDate" value="${dateString}" 
								placeholder="${dateStyle}" pattern="${regularDate}" required="required"/>
						</c:when>
						<c:otherwise>
							<form:errors path="modificationDate"/>
							<fmt:formatDate value="${news.modificationDate}" var="dateString" pattern="${dateStyle}" />
							<form:input path="modificationDate" value="${dateString}"
								placeholder="${dateStyle}" pattern="${regularDate}" required="required"/>
						</c:otherwise>
					</c:choose> 
				</td>
			</tr>
			<tr>
				<td>
					<b><spring:message code="label.brief" />: </b>
				</td>
				<td colspan="3">
					<form:errors path="shortText"/>
					<form:textarea path="shortText" rows="5"/>
				</td>
			</tr>
			<tr>
				<td>
					<b><spring:message code="label.content" />: </b>
				</td>
				<td colspan="3">
					<form:errors path="fullText"/>
					<form:textarea path="fullText" rows="10"/>
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2">
						<select name="selectAuthor" required>
							<option selected disabled value=""><spring:message code="label.chooseAuthor" /></option>
							<c:forEach var="author" items="${listAuthor}">
								<c:choose>
									<c:when test="${author.equals(newsAuthor)}">
										<option selected value="${author.authorId}">${author.authorName}</option>
									</c:when>
									<c:otherwise>
										<option value="${author.authorId}">${author.authorName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
				</td>
				<td align="left" colspan="2">		
						<div class="multiselect">
							<div class="selectBox" onclick="showCheckboxes()">
								<select>
									<option><spring:message code="label.chooseTags" /></option>
								</select>
								<div class="overSelect"></div>
							</div>
							<div id="checkboxes">
								<c:forEach var="tag" items="${listTags}">
									<label for="${tag.tagId}"> 
										<c:choose>
											<c:when	test="${newsTags.contains(tag)}">
												<input type="checkbox" checked name="selectTags" value="${tag.tagId}"/>${tag.tagName}
											</c:when> 
											<c:otherwise>
												<input type="checkbox" name="selectTags" value="${tag.tagId}"/>${tag.tagName}
											</c:otherwise> 
										</c:choose>
									</label>
								</c:forEach>
							</div>
						</div>
						<script><%@include file='/js/select.js'%></script>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="right">
					<input type="submit" value="<spring:message code="button.save"/>" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>