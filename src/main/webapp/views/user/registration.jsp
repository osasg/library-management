<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-datepicker.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/registration.css'/>">

</head>
<body style="background-image:url('<c:url value="/resources/images/registrationBackground.jpg"/>');">
<div class="container-fluid">
	<h1 class="jumbotron text-center">
		<c:choose>
			<c:when test="${mode != 'UPDATE'}">REGISTRATION</c:when>
			<c:otherwise>UPDATE</c:otherwise>
		</c:choose>
	</h1>
</div>
	<div class="containerForm">
		<form:form modelAttribute="user" class="horizontal text-center" method="POST">
			<div class="row">
				<div class="col-sm-4 label">Username</div>
				<div class="col-sm-6">
				<c:choose>
					<c:when test="${mode == 'UPDATE'}">
						<form:input path="username" id="username" type="text" disabled="true"/>
					</c:when>
					<c:otherwise>
						<form:input path="username" id="username" type="text"/>
					</c:otherwise>
				</c:choose>	
					<div class="formError"><form:errors path="username"></form:errors></div>
				</div>
			</div><br>
			<div class="row">
				<div class="col-sm-4 label">Password</div>
				<div class="col-sm-6">
					<form:input path="password" id="password" type="password"/>
					<div class="formError"><form:errors path="password"></form:errors></div>
				</div>
			</div><br>
			<div class="row">
				<div class="col-sm-4 label">Email</div>
				<div class="col-sm-6">
					<form:input path="email" id="email" type="text"/>
					<div class="formError"><form:errors path="email"></form:errors></div>
				</div>			
			</div><br>
			<div class="row">
				<div class="col-sm-4 label">Address</div>
				<div class="col-sm-6">
					<form:input path="address" id="address" type="text"/>
					<div class="formError"><form:errors path="address"></form:errors></div>
				</div>
			</div><br>
			<div class="row">
				<div class="col-sm-4 label">Birthday</div>
				<div class="col-sm-6">
	                <form:input path="birthday" type="text" class="form-control" id="datetimepicker" placeholder="${user.birthday}"/>
                    <div class="formError"><form:errors path="birthday"></form:errors></div>
                </div>
			</div><br>
			
			<sec:authorize access="hasRole('ADMIN')">
					<div class="row">
						<div class="col-sm-4 label">Roles</div>
						<div class="col-sm-6">
							<form:select path="roles" id="roles" items="${roles}" mutilple="true" itemValue="id" itemLabel="name"/>
							<div class="formError"><form:errors path="roles"></form:errors></div>		
						</div>
					</div>
			</sec:authorize>
			<br/>
			<div class="row">
				<div class="col-sm-6 formSubmit">
					<b><input type="submit" value="Submit" id="submit"/></b>
					<button type="button" class="btn-info" onclick="goBack()">Cancel</button>
				</div>
			</div>
		</form:form>
	</div>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/moment.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-datepicker.min.js'/>"></script>
<script type="text/javascript">
$(function () {
	$('#datetimepicker').datetimepicker({
	    date: new Date('${user.birthday.toGMTString()}')
	});
});

function goBack() {
    window.history.go(-1);
}
</script>
</html>