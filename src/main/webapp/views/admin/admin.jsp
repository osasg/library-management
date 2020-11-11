<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<title>Admin</title>
</head>
<body>
<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="container jumbotron text-center" style="background-color:#F0F8FF;"><h2></h2><h1>ADMIN</h1></div>
	<div class="row text-center">
			<div class="col-lg-3 alert alert-info">
				<a class="btn btn-default" href="<c:url value='/admin/listUser'/>">User</a><br><hr>
				<a class="btn btn-warning" href="<c:url value='/admin/listCategory'/>">Category</a>
			</div>
			<div class="col-lg-9"></div>
		</div>
</body>
</html>