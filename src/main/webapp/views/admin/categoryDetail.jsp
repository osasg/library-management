<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Category Detail</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body>
<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="container jumbotron text-center"><h1>C A T E G O R Y - - D E T A I L</h1></div>
	<div class="container">
		<div class="row">
			<div class="col-lg-3"></div>
			<div class="container alert alert-warning col-lg-6">
				<c:url var="action" value="/admin/saveCategory"/>
				<form:form modelAttribute="category" class="form-inline" action="${action}" method="POST">
					 <form:input path="id" type="hidden"/>
					  <div class="form-group">
					    	<label for="name">Name : </label>
					   		<form:input path="name" type="text" class="form-control" id="name"/>
					   		<form:errors path="name" class="label label-danger"/>
					  </div><br><hr>
					  <div class="text-center"><button type="submit" class="btn btn-default">Submit</button></div>
				</form:form>
			</div>
			<div class="col-lg-3"></div>
		</div>
	</div>
</body>
</html>