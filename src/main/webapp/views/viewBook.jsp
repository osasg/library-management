<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style>
	.book-infomation {
		position: relative;
		margin: auto;
		margin-top: 100px;
		margin-bottom: 100px;
		width: 50%;
		border: #555 solid 2px;
		height: 200px;
	}
	
	.image {
		position: absolute;
		left: 12px;
		top: 18px;
	}
	
	.detail {
		position: absolute;
		right: 50%;
		top: 18px;
	}
	
</style>
<body>
	<jsp:include page="./navbar.jsp"></jsp:include>
	<div class="book-infomation">
		<div class="image">
			<img width="100px" src="<c:url value='/download/${book.bookImages[0].name}'/>" />
		</div>
		<div class="detail">
			<h1>${book.name}</h1>
			<a class="btn btn-success" href="<c:url value='/member/buyBook/${book.id}'/>">Buy</a>
		</div>
	</div>
	<div class="fb-comments" data-href="http://localhost:8080/libsmanager/viewBook/2" data-numposts="5"></div>
	<div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = 'https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v3.1&appId=392766977886161&autoLogAppEvents=1';
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
</html>