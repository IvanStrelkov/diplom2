<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link rel="stylesheet"
	href="<c:url value="/resources/css/lib/bootstrap.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/listNews.css"/>"/>
<link rel="stylesheet" href="<c:url value="/resources/css/lib/bootstrap-multiselect.css"/>"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.1.0.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="<c:url value="/resources/js/lib/bootstrap-multiselect.js" />"></script>
<script src="<c:url value="/resources/js/lib/moment.min.js" />"></script>
<script src="<c:url value="/resources/js/newsListClient.js" />"></script>
</head>
<body>
		<table class="list">
			<tr>
				<td width="40%" align="right">
					<select name="selectAuthor" id="selectAuthor" class="dropdown-toggle btn btn-default white" hidden = "hidden">
						<option disabled value="0"><spring:message code="label.chooseAuthor"/></option> 	
					</select>
				</td>
				<td align="left" width="5%">
					<c:set var="chooseTags"><spring:message code="label.chooseTags"/></c:set>
					<input id="tagsText" type="hidden" value="${chooseTags}"/>
				    <select	id="mutliSelectTags" multiple="multiple" hidden = "hidden">
				    </select>
				</td>
				<td align="left" width="45%">
					<button onclick="search()" class="btn btn-primary">
						<spring:message code="button.search"/>
					</button>
					<button onclick="reset()" class="btn btn-primary">
						<spring:message code="button.reset"/>
					</button>
				</td>
			</tr>
		</table>
	<c:set var="myDateFormat"><spring:message code="pattern.date"/></c:set>
	<input id="myDateFormat" type="hidden" value="${myDateFormat}"/>
	<div id="newsDiv">
	</div>
	<div id="endListNews"></div>
</body>
</html>