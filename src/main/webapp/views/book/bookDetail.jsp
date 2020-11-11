<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Detail</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body onload="init();">
<jsp:include page="../navbar1.jsp"></jsp:include>
	<div class="container jumbotron text-center"><h1>B O O K - - D E T A I L</h1></div>
	<div class="container">
		<div class="row">
			<div class="col-lg-3"></div>
			<div class="container alert alert-warning col-lg-6">
				<c:url var="action" value="/employee/saveBook"/>
				<form:form modelAttribute="bookModel" class="form-inline" action="${action}" method="POST">
					 <form:input path="id" type="hidden"/>
					 <form:input path="library_id" type="hidden"/>
					  <div class="form-group">
					    	<label for="name">Name : </label>
					   		<form:input path="name" type="text" class="form-control" id="name"/>
					   		<form:errors path="name" class="label label-danger"/>
					  </div><br>
					  <div class="form-group">
					    	<label for="name">Category : </label>
					   		<form:select path="category_id" class="form-control" id="category">
					   			<form:options items="${categories}" itemLabel="name" itemValue="id"/>
					   		</form:select>
					   		<form:errors path="name" class="label label-danger"/>
					  </div><br>
					  <div class="form-group">
					    	<label for="name">Shelf : </label>
					   		<form:select path="shelf_id" type="text" class="form-control" id="shelf_id" onchange="changeDrawer();"/>
					   		<form:errors path="shelf_id" class="label label-danger"/>
					  </div><br>
					  <div class="form-group">
					    	<label for="name">Drawer : </label>
					   		<form:select path="drawer" type="text" class="form-control" id="drawer"/>
					   		<form:errors path="drawer" class="label label-danger"/>
					  </div><br>
					  <hr>
					  <div class="text-center">
					  	<button type="submit" class="btn btn-success">Submit</button>
					  	<button type="button" class="btn btn-default" onclick="goBack();">Cancel</button>	
					  </div>
				</form:form>
			</div>
			<div class="col-lg-3"></div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var shelves;
	
	function init(){
		shelves = ${shelves};
		var opts = "<option value='-1'>Select</option>";
		var selected = "";
		for(i = 0; i < shelves.length; i++){
			if(shelves[i].id == '${bookModel.shelf_id}')
				selected = 'selected';
			opts += "<option value='" + shelves[i].id + "' " + selected + ">" + shelves[i].name + "</option>";
		}
		$('#shelf_id').html(opts);
		
		var shelf_id = $('#shelf_id').val();
		for(var i = 0; i < shelves.length; i++){
			var drawer = $('#drawer');
			if(shelf_id == -1)
				drawer.empty();
			if(shelf_id == shelves[i].id){
				var options = "";
				drawer.empty();
				for(var j = 0; j < shelves[i].numberOfDrawer; j++){
					var sel = "";
					if((j + 1) == '${bookModel.drawer}')
						sel = "selected";
					options += "<option value='" + (j + 1) + "'" + sel + ">" + (j + 1) + "</option>";
				}
				drawer.html(options);
			}
		}		
	}
	
	function changeDrawer(){
		var shelf_id = $('#shelf_id').val();
		for(var i = 0; i < shelves.length; i++){
			var drawer = $('#drawer');
			if(shelf_id == -1)
				drawer.empty();
			if(shelf_id == shelves[i].id){
				var options = "";
				drawer.empty();
				for(var j = 0; j < shelves[i].numberOfDrawer; j++){
					options += "<option value='" + (j + 1) + "'>" + (j + 1) + "</option>";
				}
				drawer.html(options);
			}
		}
	}
	
	function goBack(){
		window.history.go(-1);
	}
</script>
</html>