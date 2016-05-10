<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<style>
	<%@include file='/css/listTags&Author.css' %>
	<%@include file='/css/list.css' %>
</style>
<script><%@include file='/js/listTags&Authors.js'%></script>
</head>
<body>	
	<c:forEach var="author" items="${listAuthors}">
		<form name="editForm${author.authorId}" action="${pageContext.request.contextPath}/editAuthor/${author.authorId}">
			<div class="editTable">
				<table class="edit">
					<tr>
						<td><b><spring:message code="label.author" />: </b></td>
						<td>
							<input type="text" name="authorName${author.authorId }"
							value="${author.authorName}" size="30" disabled id="text${author.authorId}" 
							pattern="[A-zА-я0-9 ]{1,30}" required>
						</td>
						<td>
							<button name="edit" value="update" class="link"
								id="updateButton${author.authorId}">
								<spring:message code="button.update" />
							</button>
						</td>
						<td>
							<p onclick="deleteAuthorTags(${author.authorId})" class="edit" 
								id="removeButton${author.authorId}">
								<spring:message code="button.remove" />
							</p>
						</td>
						<td>
							<p onclick="showButtons(${author.authorId})" class="edit"
								id="editButton${author.authorId}">
								<spring:message code="button.edit" />
							</p>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</c:forEach>
	<form action="${pageContext.request.contextPath}/addAuthor">
		<table class="save">
			<tr>
				<td>
					<b><spring:message code="label.addAuthor"/>: </b>
				</td>
				<td>
					<input type="text" name="authorName" value="" size="30"
						pattern="[A-zА-я0-9 ]{1,30}" required>
				</td>
				<td>
					<button name="save" value="save" class="link">
							<spring:message code="button.save"/>
					</button>	
				</td>
			</tr>
		</table>
	</form>
</body>
</html>