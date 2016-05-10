<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<style>
	<%@include file='/css/select.css' %>
	<%@include file='/css/list.css' %>
</style>
</head>
<body>
	<form action="${pageContext.request.contextPath}/filter" method="POST">
	<table class="list">
		<tr>
			<td width = "40%" align="right">
					<select name="selectAuthor">
						<option selected disabled><spring:message
								code="label.chooseAuthor" /></option>
						<c:forEach var="author" items="${listAuthor}">
								<c:choose>
									<c:when test="${author.authorId == filterAuthor}">
										<option selected value="${author.authorId}">${author.authorName}</option>
									</c:when>
									<c:otherwise>
										<option value="${author.authorId}">${author.authorName}</option>
									</c:otherwise>
								</c:choose>
						</c:forEach>
					</select>
			</td>
			<td align="left" width="5%">		
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
											<c:when	test="${filterTags.contains(tag.tagId)}">
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
			<td align ="left" width="45%">		
					<button name = "filter" value = "filter">
						<spring:message code="button.filter" />
					</button>
					<button name="filter" value = "reset">
						<spring:message code="button.reset" />
					</button>
			</td>
		</tr>
	</table>
	</form>
		<table class="list">
			<c:forEach var="news" items="${listNews}" varStatus="status">
				<tr>
					<td width="80%"><b>${news.news.title}</b></td>
					<td align="left" width="10%">(by ${news.author.authorName})</td>
					<td align="right" width="10%">
						<p class="date">
							<spring:message var="dateStyle" code="pattern.date"/>
							<fmt:formatDate value="${news.news.modificationDate}" pattern="${dateStyle}"/>
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left" width="90%">${news.news.shortText}</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">
						<p class="tags">
							<c:forEach var="tag" items="${news.listTags}">
								${tag.tagName}
							</c:forEach>
						</p>
					</td>
					<td align="left">
						<p class="comments">
							<spring:message code="label.comments">(${news.commentsCount})
							</spring:message>
						</p>
					</td>
					<td align="right">
						<a href="${pageContext.request.contextPath}/view/${startNews+status.index}/${news.news.newsId}">
							<spring:message	code="label.view" />
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
</body>
</html>