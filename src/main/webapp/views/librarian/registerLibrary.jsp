<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Library</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/registration.css'/>">
</head>
<body style="background-image:url('<c:url value="/resources/images/registrationBackground.jpg"/>');">
<div class="container-fluid">
	<h1 class="jumbotron text-center">REGISTRATION</h1>
</div>
	<div class="containerForm">
		<c:url var="action" value='/librarian/save?${_csrf.parameterName}=${_csrf.token}'/>
		<form:form action="${action}" modelAttribute="libraryModel" class="horizontal text-center" method="POST" enctype="multipart/form-data">
			<form:input path="id" type="hidden"/>
			<div class="row">
				<div class="col-sm-4 label">Name</div>
				<div class="col-sm-8">
					<form:input path="name" id="name" type="text"/>
					<div class="formError"><form:errors path="name"></form:errors></div>
				</div>
			</div><br>
			<div class="row">
				<div class="col-sm-8 formSubmit">
					<b><input type="submit" value="Submit" id="submit"/></b>
					<button type="button" class="btn-info" onclick="goBack()">Cancel</button>
				</div>
			</div>
		</form:form>
	</div>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script type="text/javascript">
	function goBack() {
	    window.history.go(-1);
	}
</script>
</html>