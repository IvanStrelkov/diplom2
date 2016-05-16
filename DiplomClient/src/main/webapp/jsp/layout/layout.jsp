<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet"
	href="<c:url value="/resources/css/layout/style.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/lib/bootstrap-multiselect.css"/>" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
</head>
<body>
	<table class="layout">
		<tr class="border_bottom" height="10%">
			<td colspan="2"><tiles:insertAttribute name="header" /></td>
		</tr>
		<tr height="86%" class="border_bottom">
			<td valign="top" width="25%"><tiles:insertAttribute name="menu" />
			</td>
			<td width="75%">
				<div id="border">
					<table class="body">
						<tr height="95%" valign="top">
							<td width="100%"><tiles:insertAttribute name="body" /></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr height="4%">
			<td colspan="2"><tiles:insertAttribute name="footer" /></td>
		</tr>
	</table>
</body>
</html>