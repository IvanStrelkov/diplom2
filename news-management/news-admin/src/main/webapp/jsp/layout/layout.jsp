<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<style>
	<%@include file='/css/style.css' %>
</style>
</head>
<body>
    <table class="layout">
        <tr class="border_bottom" height="10%" >
        	<td colspan="2">
            	<tiles:insertAttribute  name="header"/>
        	</td>
        </tr>
        <tr height="86%" class="border_bottom">
        	<security:authorize access="isAuthenticated()">
	        	<td valign="top" width="25%">
	        		<tiles:insertAttribute  name="menu" />
	        	</td>
				<td width="75%">
					<div id="border">
						<table class="body">
							<tr height="95%" valign="top">
								<td width="100%"><tiles:insertAttribute name="body" /></td>
							</tr>
							<tr height="5%">
								<td width="100%"><tiles:insertAttribute name="pagination" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</security:authorize>
        	<security:authorize access="isAnonymous()">
	        	<td>
	        	</td>
	        	<td class="body" width="100%">
       			<div id="border">
            		<tiles:insertAttribute  name="body" />
            	</div>
        	</td>
        	</security:authorize>
        </tr>
        <tr height="4%">
        	<td colspan="2">
            	<tiles:insertAttribute  name="footer" />
        	</td>
        </tr>
    </table>
</body>
</html>