<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body>
	<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="jumbotron text-center"><h1>EMPLOYEE</h1></div>
	
	<div class="container">
		<div class="row" style="width:100%;">
			<c:forEach var="lib" items="${libraries}">
				<div class="col-lg-4 text-center" style="margin-bottom:100px;">
					<span class="alert alert-warning"><b>${lib.name}</b></span><br>
					<img width="300px" height="200px" class="img-thumbnail rounded" src="<c:url value='/download/${lib.avatar}'/>"><br>
					<a class="btn btn-success" href="<c:url value='/employee/getBooks/${lib.id}'/>">Books</a>
					<a class="btn btn-warning" href="<c:url value='/employee/getShelves/${lib.id}'/>">Shelves</a>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>