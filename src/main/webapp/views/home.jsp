<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QDP's Bookstore</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/resources/css/home.css'/>">
<style>
	body, html {
	    height: 100%;
	    margin: 0;
	}
	
	#bg {
	    background-image:url('<c:url value="/resources/images/homeBackground.jpg"/>');
	    background-attachment:fixed;
	    /* Full height */
	    height: 100%; 
	    /* Center and scale the image nicely */
	    background-position: center;
	    background-repeat: no-repeat;
	    background-size: cover;
	}
</style>
</head>
<body id="bg">
	<jsp:include page="./navbar.jsp"/>
	<div id="searchForm" class="text-center">
		<h1 id="storeName">QUANG DAT PHAM'S Bookstore</h1><br>
		<div id="custom-search-input">
             <div class="input-group col-md-12">
                 <input id="txtSearch" type="text" class="search-query form-control" placeholder="Thousands interesting books are waiting for you!" />
             </div>
		</div>
	</div>
	
	<div class="container-fluid" id="libraryCategory">
		<div class="row" style="width:100%;">
					<c:forEach var="lib" items="${libraries}">
						<div class="col-lg-4 text-center" style="margin-bottom:100px;">
							<span class="alert alert-warning"><b>${lib.name}</b></span><br>
							<img width="300px" height="200px" class="img-thumbnail rounded" src="<c:url value='/download/${lib.avatar}'/>"><br>
						</div>
					</c:forEach>
				</div>
	</div>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
</html>