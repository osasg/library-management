<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<title>Shelf</title>
</head>
<body>
<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="jumbotron text-center"><h2></h2><h1>Shelf</h1><h3>-- ${library.id} : ${library.name} --</h3></div>
	<sec:authorize access="hasRole('EMPLOYEE')">
		<div class="text-center"><a class="btn btn-success" href="<c:url value='/employee/newShelf/${library.id}'/>">New Shelf</a></div>
	</sec:authorize>
	 <div class="container">
	 	<table class="table table-hover">
	    <thead>
	      <tr>
	        <th>Id</th>
	        <th>Name</th>
	        <th>Code</th>
	        <th>Number Of Book</th>
	        <th>Number Of Drawer</th>
	        <sec:authorize access="hasRole('EMPLOYEE')">
	        	<th>Action</th>
	        </sec:authorize>
	      </tr>
	    </thead>
	    <tbody>
			<c:forEach var="s" items="${shelves}">
					<tr>
				        <td>${s.id}</td>
				        <td>${s.name}</td>
				        <td>${s.code}</td>
				        <td>${s.books.size()}</td>
				        <td>${s.numberOfDrawer}</td>
				       	<sec:authorize access="hasRole('EMPLOYEE')">
				       		 <td>
					        	<a class="btn btn-info" href="<c:url value='/employee/updateShelf/${s.id}'/>">Update</a>
					        	<a class="btn btn-danger" href="<c:url value='/employee/deleteShelf/${s.id}'/>">Delete</a>
					        </td>
				       	</sec:authorize>
			      	</tr>
			</c:forEach>	      
	    </tbody>
	  </table>
	 </div>
</body>
</html>