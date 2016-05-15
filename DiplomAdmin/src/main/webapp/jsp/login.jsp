<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/lib/css/bootstrap.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/lib/css/bootstrap-multiselect.css"/>" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
</head>
<body>
	<section id="loginform" class="outer-wrapper">
		<div class="inner-wrapper">
			<div class="container">
				<div class="row">
					<div class="col-sm-4 col-sm-offset-4">
						<h2 class="text-center"><spring:message code="label.hello" /></h2>
						<form method="POST"
							action="<c:url value="/j_spring_security_check" />">
							<div class="form-group">
								<label for="exampleInputEmail1"><spring:message code="label.login" /></label> <input type="text"
									name="j_username" class="form-control" id="exampleInputEmail1">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1"><spring:message code="label.password" /></label> <input
									type="password" name="j_password" class="form-control"
									id="exampleInputPassword1">
							</div>
							<div>
								<c:if test="${not empty param.error}">
									<font color="red"> <spring:message
											code="label.loginerror" /> :
										${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
									</font>
								</c:if>
							</div>
							<button type="submit" class="btn btn-default"><spring:message code="button.login"/></button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>