<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shelf Detail</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body>
<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="container jumbotron text-center"><h1>S H E L F - - D E T A I L</h1></div>
	<div class="container">
		<div class="row">
			<div class="col-lg-3"></div>
			<div class="container alert alert-warning col-lg-6">
				<c:url var="action" value="/employee/saveShelf?${_csrf.parameterName}=${_csrf.token}"/>
				<form:form modelAttribute="shelf" class="form-inline" action="${action}" method="POST">
					 <form:input path="id" type="hidden"/>
					 <form:input path="library.id" type="hidden"/>
					  <div class="form-group">
					    	<label for="name">Name : </label>
					   		<form:input path="name" type="text" class="form-control" id="name"/>
					   		<form:errors path="name" class="label label-danger"/>
					  </div><br>
					  <div class="form-group">
					    	<label for="code">Code : </label>
					   		<form:input path="code" type="text" class="form-control" id="code"/>
					   		<form:errors path="code" class="label label-danger"/>
					  </div><br>
					  <div class="form-group">
					    	<label for="name">NumberOfDrawer : </label>
					   		<form:input path="numberOfDrawer" type="text" class="form-control" id="numberOfDrawer"/>
					   		<form:errors path="numberOfDrawer" class="label label-danger"/>
					  </div><br>
					  <hr>
					  <div class="text-center">
					  	<button type="submit" class="btn btn-info">Submit</button>
					  	<button type="button" class="btn btn-default" onclick="goBack()">Cancel</button>
					  </div>
				</form:form>
			</div>
			<div class="col-lg-3"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function goBack(){
		window.history.go(-1);
	}
</script>
</html>