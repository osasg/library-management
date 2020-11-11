<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<style>
.containerImage {
    position: relative;
}

.image {
  opacity: 1;
  display: block;
  width: 100%;
  height: auto;
  transition: .5s ease;
  backface-visibility: hidden;
}

.middle {
  transition: .5s ease;
  opacity: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  text-align: center;
}

.containerImage:hover .image {
  opacity: 0.3;
}

.containerImage:hover .middle {
  opacity: 1;
}
</style>
</head>
<body>
	<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="container-fluid jumbotron text-center alert alert-success"><h2></h2><br><h1>${book.name}</h1></div>
	<div class="container">
		<div class="row">
			<div class="col col-lg-4 alert alert-info" style="height:230px;">
				<strong>Id</strong> : ${book.id} <br>
				<strong>Name</strong> : ${book.name}<br>
				<strong>Category</strong> : ${book.category.name}<br>
				<strong>Library</strong> : ${book.shelf.library.name}<br>
				<strong>Shelf</strong> : ${book.shelf.name}<br>
				<strong>Drawer</strong> : ${book.drawer}<br>
				<strong>Document</strong> :
						    <!-- Button trigger modal -->
						    <c:choose>
							<c:when test="${book.trialBook.name != null}">
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#exampleModalCenter">
							  		${book.trialBook.name}
								</button><br>
							</c:when>
							<c:otherwise>
								<label class="label label-danger">EMPTY</label>
							</c:otherwise>
							</c:choose>
			</div>
			<div class="col col-lg-8 alert alert-warning text-center" style="height:230px;">
				<h3>Add image</h3>
				<c:url var="actionImage" value="/employee/addBookImage?${_csrf.parameterName}=${_csrf.token}"/>
				<form:form modelAttribute="bookImageModel" action="${actionImage}" method="POST" enctype="multipart/form-data">
					<form:input path="book_id" type="hidden"/>
					<label class="btn btn-default">
						<form:input path="files" type="file" multiple="true" id="file" style="margin:auto;"/>
					</label>
					<input type="submit" value="Submit" class="btn btn-info">
				</form:form>
				<h3>Add TrialBook</h3>
				<c:url var="actionDocument" value="/employee/alterTrialBook?${_csrf.parameterName}=${_csrf.token}"/>
				<form:form modelAttribute="trialBookModel" action="${actionDocument}" method="POST" enctype="multipart/form-data">
					<form:input path="book_id" type="hidden"/>
					<label class="btn btn-default">
						<form:input path="file" type="file" id="file" style="margin:auto;"/>
					</label>
					<input type="submit" value="Submit" class="btn btn-info">
				</form:form>
			</div>
		</div>
		<div class="row alert alert-success">
			<c:forEach var="image" items="${book.bookImages}">
				<div class="containerImage col col-lg-2">
					<img src="<c:url value='/download/${image.name}'/>" class="image img-thumbnail" style="height:150px;"/>
					<div class="middle">
					    <a href="<c:url value='/employee/deleteImage/${image.id}?book_id=${book.id}'/>" class="btn btn-danger">Delete</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
							<!-- Modal -->
							<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
							  <div class="modal-dialog modal-dialog-centered" role="document">
							    <div class="modal-content">
							    	  	 <div class="modal-header">
									        <h5 class="modal-title" id="exampleModalLongTitle">Avatar</h5>
									        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
									          <span aria-hidden="true">&times;</span>
									        </button>
									      </div>
									
									      <c:url var="action" value="/librarian/changeImage?${_csrf.parameterName}=${_csrf.token}"/>
									     <form:form modelAttribute="libraryImageModel" action="${action}" enctype="multipart/form-data" method="POST">
										      <div class="modal-body">


										      </div>
										      <div class="modal-footer">
										        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
										      </div>
									     </form:form>
							    </div>
							  </div>
							</div>
</body>
</html>