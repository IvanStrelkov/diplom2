<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<style>
	<%@include file='/css/style.css' %>
</style>
</head>
<body>
    <table class="layout">
        <tr class="border_bottom" height="10%" >
        	<td>
            	<tiles:insertAttribute  name="header"/>
        	</td>
        </tr>
        <tr height="86%" class="border_bottom">
				<td>
					<div id="border">
						<table class="body">
							<tr height="95%" valign="top">
								<td width="100%">
									<tiles:insertAttribute name="body" />
								</td>
							</tr>
							<tr height="5%">
								<td width="100%">
									<tiles:insertAttribute name="pagination" />
								</td>
							</tr>
						</table>
					</div>
				</td>
        </tr>
        <tr height="4%">
        	<td>
            	<tiles:insertAttribute  name="footer" />
        	</td>
        </tr>
    </table>
</body>
</html>