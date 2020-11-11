<%@page import="com.quangdat.util.SecurityUtil"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<link rel="stylesheet" href="<c:url value='/resources/css/navbar.css'/>">
	<nav class="[ navbar navbar-fixed-top ][ navbar-bootsnipp animate ]" role="navigation">
    	<div class="[ container ]">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="[ navbar-header ]">
				<button type="button" class="[ navbar-toggle ]" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="[ sr-only ]">Toggle navigation</span>
					<span class="[ icon-bar ]"></span>
					<span class="[ icon-bar ]"></span>
					<span class="[ icon-bar ]"></span>
				</button>
				<div class="[ animbrand ]">
					<a style="text-decoration:none;" href="<c:url value='/home'/>"><img width="100px" height="50px" src="<c:url value='/resources/images/myIcon.png'/>"><strong>Quang Dat Pham's Bookstore</strong></a>
				</div>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="[ collapse navbar-collapse ]" id="bs-example-navbar-collapse-1">
				<ul class="[ nav navbar-nav navbar-right ]">
					<li class="[ dropdown ]">
						<a href="#" class="[ dropdown-toggle ][ animate ]" data-toggle="dropdown">Roles<span class="[ caret ]"></span></a>
						<ul class="[ dropdown-menu ]" role="menu">
							<li><a class="animate" href="<c:url value='/librarian/registerLibrary'/>">Create your Library</a></li>
							<sec:authorize access="hasRole('ADMIN')">
								<li><a class="animate" href="<c:url value='/admin/'/>">ADMIN</a></li>
							</sec:authorize>
		                    <sec:authorize access="hasRole('LIBRARIAN')">
								<li><a class="animate" href="<c:url value='/librarian/'/>">LIBRARIAN</a></li>
							</sec:authorize>
							<sec:authorize access="hasRole('EMPLOYEE')">
								<li><a class="animate" href="<c:url value='/employee/'/>">EMPLOYEE</a></li>
							</sec:authorize>
						</ul>
					</li>
					<c:if test="${applicationModels.size() != 0 && applicationModels != null}">
					<sec:authorize access="hasRole('LIBRARIAN')">
							<li class="[ dropdown ]">
								<a href="#" class="[ dropdown-toggle ][ animate ]" data-toggle="dropdown">Notification (${applicationModels.size()})<span class="[ caret ]"></span></a>
								<ul class="[ dropdown-menu ]" role="menu">
									<c:forEach var="a" items="${applicationModels}">
										<li class="text-center">
											<div class="animate alert alert-warning">
												<strong>${a.username}<br>${a.library_name}</strong><br>
												<button id="btnApply${a.id}" onclick="applyEmployeeViaAjax(${a.id})" class="btn btn-warning" style="margin-left:300px;" >Apply</button>
												<div id="feedback${a.id}"></div>
											</div>
										</li>
									</c:forEach>
								</ul>
							</li>
					</sec:authorize>
					</c:if>
					<li style="margin-left:100px;right:0;"><a class="animate" href="<c:url value='/logout'/>">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="csrfToken" />
	<meta name="_csrf_header" content="${_csrf.headerName}" />
	<c:url var="applyEmployee" value="/librarian/applyEmployee"/>
<script>
	function applyEmployeeViaAjax(application_id) {
		var token = $('#csrfToken').val();
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${applyEmployee}",
			data : JSON.stringify(application_id),
			dataType : 'json',
			timeout : 100000,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				console.log("SUCCESS: ", data);
				display(data, application_id);
				$('#btnApply' + application_id).prop("disabled", true);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				display(e, 0);
			},
			done : function(e) {
				$('#feedback' + application_id).html("ERROR Result : " + e);
				console.log("DONE application_id : " + application_id);
			}
		});

	}

	function display(data, application_id) {
		$('#feedback' + application_id).html("Result : " + data.msg);
		$('#feedback' + application_id).prop("class", "alert alert-info");
	}
</script>