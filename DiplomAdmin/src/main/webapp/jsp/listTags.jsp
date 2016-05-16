<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<link rel="stylesheet"
	href="<c:url value="/resources/css/lib/bootstrap.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/listTags&Author.css"/>">
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
<script src="<c:url value="/resources/js/tagsList.js" />"></script>
</head>
<body>
	<c:set var="labelTag">
		<spring:message code="label.tag" />
	</c:set>
	<input id="tagLabel" type="hidden" value="${labelTag}" />
	<c:set var="addTag">
		<spring:message code="label.addTag" />
	</c:set>
	<input id="addTag" type="hidden" value="${addTag}" />
	<c:set var="buttonUpdate">
		<spring:message code="button.update" />
	</c:set>
	<input id="buttonUpdate" type="hidden" value="${buttonUpdate}" />
	<c:set var="buttonEdit">
		<spring:message code="button.edit" />
	</c:set>
	<input id="buttonEdit" type="hidden" value="${buttonEdit}" />
	<c:set var="buttonRemove">
		<spring:message code="button.remove" />
	</c:set>
	<input id="buttonRemove" type="hidden" value="${buttonRemove}" />
	<c:set var="buttonSave">
		<spring:message code="button.save" />
	</c:set>
	<input id="buttonSave" type="hidden" value="${buttonSave}" />
	<div id="listTagsDiv" class="editTable"></div>
	<div id="addTagDiv"></div>
</body>
</html>