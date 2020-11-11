<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<title>Category</title>
</head>
<body>
<jsp:include page="../navbar1.jsp"></jsp:include>
<div class="jumbotron text-center"><h2></h2><h1>Category</h1></div>
	<div class="text-center"><a class="btn btn-success" href="<c:url value='/admin/newCategory'/>">New Category</a></div>
	 <div class="container">
	 	<table class="table table-hover">
	    <thead>
	      <tr>
	        <th>Id</th>
	        <th>Name</th>
	        <th>Number Of Book</th>
	        <th>Action</th>
	      </tr>
	    </thead>
	    <tbody>
			<c:forEach var="c" items="${categories}">
					<tr>
				        <td>${c.id}</td>
				        <td>${c.name}</td>
				        <td>${c.books.size()}</td>
				        <td>
				        	<a class="btn btn-info" href="<c:url value='/admin/updateCategory/${c.id}'/>">Update</a>
				        	<a class="btn btn-danger" href="<c:url value='/admin/deleteCategory/${c.id}'/>">Delete</a>
				        </td>
			      	</tr>
			</c:forEach>	      
	    </tbody>
	  </table>
	 </div>
</body>
</html>