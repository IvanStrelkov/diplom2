<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@	taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/news.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/select.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/css/editNews.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/lib/bootstrap-multiselect.css"/>" />
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.1.0.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="<c:url value="/resources/js/lib/bootstrap-multiselect.js" />"></script>
<script src="<c:url value="/resources/js/lib/moment.min.js" />"></script>
<script src="<c:url value="/resources/js/newsClient.js" />"></script>
</head>
<body>
	<input type="hidden" id="news_id" value="${news_id}">
	<div id="editTable" hidden="true">
		<table class="edit">
			<tr>
				<td width="10%"></td>
				<td width="40%"></td>
				<td width="25%"></td>
				<td width="25%"></td>
			</tr>
			<tr>
				<td><b><spring:message code="label.title" />: </b></td>
				<td colspan="3">
					<div id="newsTitle"></div>
				</td>
			</tr>
			<tr>
				<td><b><spring:message code="label.date" />: </b></td>
				<td colspan="3"><c:set var="myDateFormat">
						<spring:message code="pattern.date" />
					</c:set> <input id="myDateFormat" type="hidden" value="${myDateFormat}" />
					<div id="newsDate"></div></td>
			</tr>
			<tr>
				<td><b><spring:message code="label.brief" />: </b></td>
				<td colspan="3">
					<div id="newsShortText"></div>
				</td>
			</tr>
			<tr>
				<td><b><spring:message code="label.content" />: </b></td>
				<td colspan="3">
					<div id="newsFullText"></div>
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2"><select name="selectAuthor"
					id="selectAuthor" class="dropdown-toggle btn btn-default"
					hidden="hidden">
						<option disabled value="0"><spring:message
								code="label.chooseAuthor" /></option>
				</select></td>
				<td align="left" colspan="2"><c:set var="chooseTags">
						<spring:message code="label.chooseTags" />
					</c:set> <input id="tagsText" type="hidden" value="${chooseTags}" /> <select
					id="mutliSelectTags" multiple="multiple" hidden="hidden">
				</select></td>
			</tr>
			<tr>
				<td colspan="4" align="right">
					<div id="newsButtons"></div>
				</td>
			</tr>
		</table>
	</div>
	<c:set var="creationDate">
		<spring:message code="label.creationDate" />
	</c:set>
	<input id="creationDate" type="hidden" value="${creationDate}" />

	<c:set var="modificationDate">
		<spring:message code="label.modificationDate" />
	</c:set>
	<input id="modificationDate" type="hidden" value="${modificationDate}" />

	<c:set var="buttonSave">
		<spring:message code="button.save" />
	</c:set>
	<input id="buttonSave" type="hidden" value="${buttonSave}" />

	<c:set var="buttonEdit">
		<spring:message code="button.edit" />
	</c:set>
	<input id="buttonEdit" type="hidden" value="${buttonEdit}" />

	<c:set var="buttonRemove">
		<spring:message code="button.remove" />
	</c:set>
	<input id="buttonRemove" type="hidden" value="${buttonRemove}" />

	<c:set var="buttonCancel">
		<spring:message code="button.cancel" />
	</c:set>
	<input id="buttonCancel" type="hidden" value="${buttonCancel}" />

	<c:set var="buttonUpdate">
		<spring:message code="button.update" />
	</c:set>
	<input id="buttonUpdate" type="hidden" value="${buttonUpdate}" />

	<c:set var="deleteCommentButton">
		<spring:message code="button.deletecomment" />
	</c:set>
	<input id="deleteCommentButton" type="hidden"
		value="${deleteCommentButton}" />
	<c:set var="addCommentButton">
		<spring:message code="button.postcomment" />
	</c:set>
	<input id="addCommentButton" type="hidden" value="${addCommentButton}" />

	<div id="divNews"></div>
	<div id="divAddComment"></div>
	<div>
		<br>
		<br>
		<br>
	</div>
	<div id="divComments"></div>

</body>
</html>