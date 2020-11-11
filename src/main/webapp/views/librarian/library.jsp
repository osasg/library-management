<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Library</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body>
	<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="jumbotron text-center" style="background-color:	#FF9;"><h1>LIBRARY</h1></div>
	<div class="container">
		<c:forEach var="lib" items="${libraries}">
			<div class="col col-lg-4 text-center" style="margin-bottom:100px;">
				<a href="<c:url value='/librarian/updateLibrary/${lib.id}'/>" style="text-decoration:none;">
					<span class="alert alert-warning"><b>${lib.name}</b></span>
					<img width="300px" height="200px" class="img-thumbnail rounded" src="<c:url value='/download/${lib.avatar}'/>"><br>
				</a><br>
				<p><a href="<c:url value='/librarian/deleteLibrary/${lib.id}'/>" class="btn btn-danger" style="width:100%;">Delete</a>
			</div>
		</c:forEach>
	</div>
</body>
</html>