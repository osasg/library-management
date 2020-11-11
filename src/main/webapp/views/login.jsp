<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/login.css'/>">

</head>
<body style="background-image:url('<c:url value="/resources/images/loginBackground.jpeg"/>')">
		<a href="<c:url value='/home'/>"></a>
		<div id="formLogin">
			<h1>LOGIN</h1>
			<c:if test="${param.error != null}">
				<div class="notification">Username or password incorrect</div>
			</c:if>
			<c:if test="${param.logout != null}">
				<div class="notification">Logout successfully</div>
			</c:if>
			<c:url var="loginUrl" value="/login"/>
			<form action="${loginUrl}" method="POST">
				<p><div class="formLabel">Username </div><input type="text" name="username" class="formInput">
				<p><div class="formLabel">Password </div><input type="password" name="password" class="formInput">
				<div><input type="checkbox" id="remember-me" name="remember-me">Remember Me</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<p><input type="submit" value="Login" id="login">
				<p><a class="btn btn-warning" style="font-size:20px;" href="<c:url value='/member/createAccount'/>"><strong>Registration</strong></a>
			</form>
		</div>
</body>
</html>