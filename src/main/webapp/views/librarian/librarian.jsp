<%@page import="com.quangdat.util.SecurityUtil"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Librarian</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/librarian.css'/>">
</head>
<body>
	<jsp:include page="../navbar1.jsp"></jsp:include>
		<div class="jumbotron text-center">
			<h1>LIBRARIAN</h1>
			<h2>---<%=SecurityUtil.getPricipal()%>---</h2>
		</div>
		<div class="row" style="height:100%;">
			<div class="col-lg-2">
				<div class="sidenav">
				  <a href="<c:url value='/librarian/registerLibrary'/>">New</a>
				  <a href="<c:url value='/librarian/libService'/>">LibService</a>
				  <a href="#clients">Clients</a>
				  <a href="#contact">Contact</a>
				</div>
			</div>
			<div class="col-lg-10">
				<div class="row" style="width:100%;">
					<c:forEach var="lib" items="${libraries}">
						<div class="col-lg-4 text-center" style="margin-bottom:100px;">
							<span class="alert alert-warning"><b>${lib.name}</b></span><br>
							<img width="300px" height="200px" class="img-thumbnail rounded" src="<c:url value='/download/${lib.avatar}'/>"><br>
							<a class="btn btn-success" href="<c:url value='/librarian/getBooks/${lib.id}'/>">Books</a>
							<a class="btn btn-warning" href="<c:url value='/librarian/getEmployees/${lib.id}'/>">Employee</a>
							<a class="btn btn-info" href="<c:url value='/librarian/getShelves/${lib.id}'/>">Shelf</a>
							<br>

							<!-- Button trigger modal -->
							<button type="button" class="btn btn-default" data-toggle="modal" data-target="#exampleModalCenter${lib.id}">
							  Avatar and CoverImage
							</button>
		
							<!-- Modal -->
							<div class="modal fade" id="exampleModalCenter${lib.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
							  <div class="modal-dialog modal-dialog-centered" role="document">
							    <div class="modal-content">
							    	  	 <div class="modal-header">
									        <h5 class="modal-title" id="exampleModalLongTitle${lib.id}">Avatar</h5>
									        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
									          <span aria-hidden="true">&times;</span>
									        </button>
									      </div>
									
									      <c:url var="action" value="/librarian/changeImage?${_csrf.parameterName}=${_csrf.token}"/>
									     <form:form modelAttribute="libraryImageModel" action="${action}" enctype="multipart/form-data" method="POST">
										      <div class="modal-body">
										      		<form:input path="library_id" value="${lib.id}" type="hidden"/>
											      	<label class="btn btn-default">
													    Avatar <form:input path="avatarFile" type="file" />
													</label>
													<hr>
														<label class="btn btn-default">
															CoverImage <form:input path="coverImageFile" type="file"/>
														</label>
										      </div>
										      <div class="modal-footer">
										        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
										        <button type="submit" class="btn btn-primary">Save changes</button>
										      </div>
									     </form:form>
							    </div>
							  </div>
							</div>
						</div>
	
					</c:forEach>
				</div>
			</div>
		</div>
</body>
<script>UPLOADCARE_PUBLIC_KEY = '4b0c487e2f9b4b8df946';</script>
<script src="https://ucarecdn.com/libs/widget/3.3.0/uploadcare.full.min.js" charset="utf-8"></script>
</html>