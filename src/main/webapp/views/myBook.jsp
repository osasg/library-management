<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List Book</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/css/listBook.css'/>">

</head>
<body>
		<div>
				<c:forEach var="book" items="${books}">
					<div class="col-lg-3">
						<div class="offer offer-success">
							<div class="offer-content" style="height:400px;">
								<h3 class="lead">
									<a href="<c:url value='/viewBook/${book.id}'/>">${book.name}</a>
								</h3>
								
								  <div id="myCarousel${book.id}" class="carousel slide" data-ride="carousel">
									   <!-- Indicators -->
									    <ol class="carousel-indicators">
									    	<c:set var="count" value="-1"/>
									    	<c:forEach var="image" items="${book.bookImages}">
											    <li data-target="#myCarousel${book.id}" data-slide-to="${count = count + 1}"></li>
											</c:forEach>
									    </ol>
									    
									    <!-- Wrapper for slides -->
									    <div class="carousel-inner">
									    	 <c:if test="${! empty book.bookImages}">
									    	 	<div class="item active">
													   <img src="<c:url value='/download/${book.bookImages[0].name}'/>" alt="${book.bookImages[0].name}" style="width:80%;">
												</div>
											     <c:forEach var="image" items="${book.bookImages}" begin="1">
											     		 <div class="item">
													        <img src="<c:url value='/download/${image.name}'/>" alt="${image.name}" style="width:80%;">
													      </div>
											     </c:forEach>
									    	 </c:if>
									    </div>
									
									    <!-- Left and right controls -->
									    <a class="left carousel-control" href="#myCarousel${book.id}" data-slide="prev">
									      <span class="glyphicon glyphicon-chevron-left"></span>
									      <span class="sr-only">Previous</span>
									    </a>
									    <a class="right carousel-control" href="#myCarousel${book.id}" data-slide="next">
									      <span class="glyphicon glyphicon-chevron-right"></span>
									      <span class="sr-only">Next</span>
									    </a>
								  </div>
								  
								  <div class="alert alert-info">
								  	<strong>Category : </strong>${book.category.name}<br>
								  	<strong>Try it : </strong>
								  </div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
</html>