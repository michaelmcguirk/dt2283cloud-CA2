<%@ page 
import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%BlobstoreService blobstoreService = 
BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
 	<head>
 		<title>Upload Picture</title>
 		<link rel="stylesheet" type="text/html" href="${pageContext.request.contextPath}/style.css"/>
 		
 		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script> 
		$(function(){
		  $("#nav").load("nav.jsp"); 
		});
		</script>
 		
 	</head>
 	<body>
 	<div id="header"><h1>Photo Box</h1></div>
 	<div id="nav"></div>
 	<p>&nbsp</p>
 		<form action="<%= blobstoreService.createUploadUrl("/upload") %>" 
			method="post" enctype="multipart/form-data">
 			<input type="text" name="foo">
 			<input type="file" name="myFile2">
 			<input type="submit" value="Submit">
 		</form>
 </body>
</html>