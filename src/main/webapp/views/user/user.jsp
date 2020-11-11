<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User</title>

<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/datatables.min.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/user.css'/>">

</head>
<body>
	<jsp:include page="../navbar1.jsp"></jsp:include>
	<c:choose>
		<c:when test="${mode == 'ADMIN'}">
			<h1 class="pageTitle" style="margin-top: 70px;">--------USER--------</h1>
		</c:when>
		<c:otherwise>
			<h1 class="pageTitle" style="margin-top: 70px;">--------EMPLOYEE--------</h1>
			<h2 class="pageTitle">${library.name}</h2>
		</c:otherwise>
	</c:choose>
	<c:if test="${mode == 'ADMIN'}">
		<a id="newUser" href="<c:url value='/admin/newUser'/>">New User</a>
	</c:if>
	<table id="myTable" class="display text-center">
		<thead>
			<tr>
				<th>Usename</th>
				<th>Email</th>
				<th>Birthday</th>
				<th>Regidtration Date</th>
				<th>Address</th>
				<sec:authorize access="hasRole('ADMIN')">
					<c:if test="${mode == 'ADMIN'}">
						<th>Role</th>
					</c:if>
				</sec:authorize>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>${user.birthday}</td>
					<td>${user.registrationDate}</td>
					<td>${user.address}</td>
					<sec:authorize access="hasRole('ADMIN')">
						<c:if test="${mode == 'ADMIN'}">
							<td>
								<c:forEach var="role" items="${user.roles}">
									<c:if test="${role.name == 'ADMIN'}">
										<span class="alert alert-danger">${role.name}</span>
									</c:if>
									<c:if test="${role.name == 'LIBRARIAN'}">
										<span class="alert alert-warning">${role.name}</span>
									</c:if>
									<c:if test="${role.name == 'EMPLOYEE'}">
										<span class="alert alert-info">${role.name}</span>
									</c:if>
									<c:if test="${role.name == 'MEMBER'}">
										<span style="border:solid 1px #000000;" class="alert alert-default">${role.name}</span>
									</c:if>
								</c:forEach>
							</td>
							<td>
								<a class="btn btn-info" href="<c:url value='/admin/updateUser/${user.username}'/>" class="btnUpdate">Update</a>
								<a class="btn btn-danger" href="<c:url value='/admin/deleteUser/${user.username}'/>" class="btnDelete">Delete</a>
							</td>
						</c:if>
					</sec:authorize>
					<sec:authorize access="hasRole('LIBRARIAN')">
						<c:if test="${mode == 'LIBRARIAN'}">
							<td><button class="btn btn-warning" onClick="deleteEmployee('${user.username}', '${library.id}')">Reject</button></td>
						</c:if>
					</sec:authorize>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.3.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/datatables.min.js'/>"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('#myTable').DataTable();
} );

function sendRequest(url, data, method, callback) {
	$.ajax({
		url : url,
		type : method,
		data : data,
		contentType : 'application/json',
		success : callback,
		error : function(request, msg, error) {
			// handle failure
		}
	});
};

function sendDeleteRequest(url, callback) {
	sendRequest(url, "", 'DELETE', callback);
};

function deleteEmployee(username, id) {
	var r = confirm("Do you want to delete this employee?");
	if (r == true) {
		sendDeleteRequest('./' + username + "/" + id, null);
	}	
};
</script>
</html>