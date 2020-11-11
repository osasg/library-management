<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/table.css'/>">
<link rel="stylesheet" href="<c:url value='/resources/css/datatables.min.css'/>">
</head>
<body>
<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="jumbotron text-center" style="background-color:	#FFF8DC;"><h1>BOOK</h1><h3>-- ${library.id} : ${library.name} --</h3></div>
	<sec:authorize access="hasRole('EMPLOYEE')">
		<div class="text-center"><a class="btn btn-info" href="<c:url value='/employee/newBook/${library.id}'/>">New Book</a></div><br>
	</sec:authorize>
	<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="pull-right">
							<strong>Category : </strong>
							<div class="btn-group">
								<button type="button" class="btn btn-success btn-filter" data-target="all">All</button>
								<c:forEach var="c" items="${categories}">
									<button type="button" class="btn btn-info btn-filter" data-target="${c.id}">${c.name}</button>
								</c:forEach>
							</div>
						</div>
						<div class="table-container alert alert-warning">
							<table class="table table-filter" id="myTable">
								<thead>
									<tr id="th">
										<th>Id</th>
										<th>Image</th>
										<th>Name</th>
										<th>Category</th>
										<th>Library</th>
										<th>Shelf</th>
										<th>Drawer</th>
										<sec:authorize access="hasRole('EMPLOYEE')">
											<th style="width:216px;">Action</th>
										</sec:authorize>
									</tr>
								</thead>
								<tbody>
										<c:forEach var="book" items="${books}">
											<tr data-status="${book.category.id}">
												<td>${book.id}</td>
												<td>
													<c:if test="${!empty book.bookImages}">
														<div class="media">
															<a href="#" class="pull-left">
																<img src="<c:url value='/download/${book.bookImages[0].name}'/>" class="media-photo">
															</a>
														</div>
													</c:if>
												</td>
												<td>${book.name}</td>
												<td>${book.category.name}</td>
												<td>${book.shelf.library.name}</td>
												<td>${book.shelf.name}</td>
												<td>${book.drawer}</td>
												<sec:authorize access="hasRole('EMPLOYEE')">
													<td>
														<a class="btn btn-info" href="<c:url value='/employee/viewBook/${book.id}'/>">VIEW</a>
														<a class="btn btn-success" href="<c:url value='/employee/updateBook/${book.id}'/>">UPDATE</a>
														<a class="btn btn-danger" href="<c:url value='/employee/deleteBook/${book.id}'/>">DELETE</a>
													</td>
												</sec:authorize>
											</tr>	
										</c:forEach>								
								</tbody>
								<tfoot>
									<tr id="tf">
										<th>Id</th>
										<th>Image</th>
										<th>Name</th>
										<th>Category</th>
										<th>Library</th>
										<th>Shelf</th>
										<th>Drawer</th>
										<sec:authorize access="hasRole('EMPLOYEE')">
											<th>Action</th>
										</sec:authorize>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
				<div class="content-footer text-center">
					<p>
						Page © - 2018 <br>
						Powered By <a href="https://www.facebook.com/Goal.Effort.Success" target="_blank">Quang Dat Pham</a>
					</p>
				</div>
			</div>
		
	</div>
</body>
<script src="<c:url value='/resources/js/datatables.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function () {
	
		$('.star').on('click', function () {
	      $(this).toggleClass('star-checked');
	    });
	
	    $('.ckbox label').on('click', function () {
	      $(this).parents('tr').toggleClass('selected');
	    });
	
	    $('.btn-filter').on('click', function () {
	      var $target = $(this).data('target');
	      if ($target != 'all') {
	        $('.table tr').css('display', 'none');
	        $('#th').show();
	        $('#tf').show();
	        $('.table tr[data-status="' + $target + '"]').fadeIn('slow');
	      } else {
	        $('.table tr').css('display', 'none').fadeIn('slow');
	      }
	    });
	
	 });
	
	// Setup - add a text input to each footer cell
    $('#myTable tfoot th').each( function () {
        var title = $(this).text();
        if(title != 'Image' && title != 'Action')
        	$(this).html( '<input type="text" placeholder="' + title + '" style="width:100%;"/>' );
    } );
 
    // DataTable
    var table = $('#myTable').DataTable();
 
    // Apply the search
    table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
</script>
</html>