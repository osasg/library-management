<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${library.name}</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value='/resources/css/listBook.css'/>">
<style>
	#coverImage{
		background-color:#F8F8FF;
		position: relative;
	    width: 100%;
	    border: 3px solid #73AD21;
	    z-index:-2;
	}
	
	#listBooks{
		width:100%;
		margin-left:20px;
	}
	
	#avatar{
		position: absolute;
	    top: 200px;
	    left: 44%;
	    width: 12%;
	    height: 50%;
	    border: 3px solid #73AD21;
	    background-color:	#FFFAF0;
	}
</style>
</head>
<body style="background-color:	#F0FFFF;">
	<jsp:include page="./navbar.jsp"></jsp:include>
	<div id="coverImage">
			<img height="300px" width="100%" src="<c:url value='/download/${library.coverImage}'/>">
			<div id="avatar">
				<img width="100%" height="100%" src="<c:url value='/download/${library.avatar}'/>">
			</div>		
	</div>
	<div id="listBooks">
		<span class="alert alert-warning text-center"><b>${library.name}</b></span>
		<c:choose>
			<c:when test="${! libIds.contains(library.id)}">
				<sec:authorize access="hasRole('MEMBER')">
					<strong><span id="feedback"><button class="btn btn-success" onclick="applyForEmployeeViaAjax()">Apply for employee</button></span></strong>
				</sec:authorize>
			</c:when>
			<c:otherwise>
				<span class="alert alert-success">Applied</span>
			</c:otherwise>
		</c:choose>
		
			<div style="margin-top:100px;">
				<c:forEach var="shelf" items="${library.shelves}">
				<c:forEach var="book" items="${shelf.books}">
					<div class="col-lg-3">
						<div class="offer offer-success">
							<div class="offer-content" style="height:400px;">
								<h3 class="lead">
									${book.name}
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
				</c:forEach>
			</div>
			
		</div>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" id="csrfToken" />
		<meta name="_csrf_header" content="${_csrf.headerName}" />
		<c:url var="applyForEmployee" value="/member/applyForEmployee/"/>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
<script>
	function applyForEmployeeViaAjax() {
		var token = $('#csrfToken').val();
		var header = $("meta[name='_csrf_header']").attr("content");
		var library_id = ${library.id};
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${applyForEmployee}",
			data : JSON.stringify(library_id),
			dataType : 'json',
			timeout : 100000,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				console.log("SUCCESS: ", data);
				display(data);
			},
			error : function(e) {
				$('#feedback').html(JSON.stringify(e, null, 4));
				console.log("ERROR: ", JSON.stringify(e, null, 4));
				//display(e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});

	}

	function display(data) {
		$('#feedback').html(data.msg);
		$('#feedback').prop("class", "alert alert-info");
	}
</script>
</html>