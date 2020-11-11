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
					
					
					
					<li class="[ visible-xs ]">
						<form action="http://bootsnipp.com/search" method="GET" role="search">
							<div class="[ input-group ]">
								<input type="text" class="[ form-control ]" name="q" placeholder="Search for snippets">
								<span class="[ input-group-btn ]">
									<button class="[ btn btn-primary ]" type="submit"><span class="[ glyphicon glyphicon-search ]"></span></button>
									<button class="[ btn btn-danger ]" type="reset"><span class="[ glyphicon glyphicon-remove ]"></span></button>
								</span>
							</div>
						</form>
					</li>
					
					
					
					<li><a href="<c:url value='/getAllBooks'/>" class="[ animate ]">Books</a></li>
					<li>
						<a href="#" class="[ dropdown-toggle ][ animate ]" data-toggle="dropdown">Libraries<span class="[ caret ]"></span></a>
						<ul class="[ dropdown-menu ]" role="menu">
							<c:forEach var="lib" items="${libraries}">
								<li><a href="<c:url value='/libraryPage/${lib.id}'/>" class="[ animate ]">${lib.name}</a></li>
							</c:forEach>
						</ul>
					</li>
					<li class="[ dropdown ]">
						<a href="#" class="[ dropdown-toggle ][ animate ]" data-toggle="dropdown">Categories<span class="[ caret ]"></span></a>
						<ul class="[ dropdown-menu ]" role="menu">
							<c:forEach var="c" items="${categories}">
								<li><a href="<c:url value='/getCategory/${c.id}'/>" class="[ animate ]">${c.name}</a></li>
							</c:forEach>
						</ul>
					</li>
					<sec:authorize access="isAuthenticated()">
						<li class="[ dropdown ]">
							<a href="#" class="[ dropdown-toggle ][ animate ]" data-toggle="dropdown">Roles<span class="[ caret ]"></span></a>
							<ul class="[ dropdown-menu ]" role="menu">
								<sec:authorize access="hasRole('MEMBER')">
									<li><a class="animate" href="<c:url value='/librarian/registerLibrary'/>">Create your Library</a></li>
								</sec:authorize>
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
						<li><a href="<c:url value='/member/myBook'/>" class="[ animate ]">My-Book</a></li>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<li><a class="animate" href="<c:url value='/member/createAccount'/>">Register</a></li>
					</sec:authorize>
                    <sec:authorize access="isAnonymous()">
						<li><a class="animate" href="<c:url value='/login'/>">Login</a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<li style="margin-left:50px;right:0;"><a class="animate" href="<c:url value='/logout'/>">Logout</a></li>
					</sec:authorize>
				</ul>
			</div>
		</div>
	</nav>
