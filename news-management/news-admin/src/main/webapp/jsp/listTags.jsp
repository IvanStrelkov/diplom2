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
	<c:forEach var="tag" items="${listTags}">
		<form name="editForm${tag.tagId}" action="${pageContext.request.contextPath}/editTag/${tag.tagId}">
			<div class="editTable">
				<table class="edit">
					<tr>
						<td><b><spring:message code="label.tag" />: </b></td>
						<td>
							<input type="text" name="tagName${tag.tagId }"
							value="${tag.tagName}" size="30" disabled id="text${tag.tagId}" 
							pattern="[A-zА-я0-9 ]{1,30}" required>
						</td>
						<td>
							<button name="edit" value="update" class="link"
								id="updateButton${tag.tagId}">
								<spring:message code="button.update" />
							</button>
						</td>
						<td>
							<p onclick="deleteAuthorTags(${tag.tagId})" class="edit" 
								id="removeButton${tag.tagId}">
								<spring:message code="button.remove" />
							</p>
						</td>
						<td>
							<p onclick="showButtons(${tag.tagId})" class="edit"
								id="editButton${tag.tagId}">
								<spring:message code="button.edit" />
							</p>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</c:forEach>
	<form action="${pageContext.request.contextPath}/addTag">
		<table class="save">
			<tr>
				<td>
					<b><spring:message code="label.addTag"/>: </b>
				</td>
				<td>
					<input type="text" name="tagName" value="" size="30"
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