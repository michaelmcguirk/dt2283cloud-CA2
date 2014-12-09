<%@ page 
import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.users.*"%>
<%@ page import="com.google.appengine.api.datastore.Query.Filter"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Query.CompositeFilterOperator"%>


<%BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
<%UserService userService = UserServiceFactory.getUserService(); %>
<%User u = userService.getCurrentUser(); %>
<%DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();%>


<html>
 	<head>
 		<title>View All Pictures</title>
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

 	<table align="center">
 	<tr>
 	<%
 		String userEmail = u.getEmail();
 		String permission = "public";
 		Filter userFilter = new FilterPredicate("email", FilterOperator.EQUAL, userEmail);
 		Filter permissionFilter = new FilterPredicate("permission", FilterOperator.EQUAL, permission);
 		
 		//composite filter for entities that match the user email or that are public.
 		Filter mainFilter = CompositeFilterOperator.or(userFilter, permissionFilter);
		Query q = new Query("User").setFilter(mainFilter);
		PreparedQuery pq = datastore.prepare(q);
		int i = 0;

		for (Entity result : pq.asIterable())
		{
			i++;
				
			String thumbURL = (String) result.getProperty("thumbURL");
			String imgURL = (String) result.getProperty("URL");
			String email = (String) result.getProperty("email");
			if(i%4 == 0)
			{
 	%>
		 		</tr>
				<tr>
				<td>
				<a href="<%= imgURL%>"><img src="<%= thumbURL%>"></a>
				<p>
	<%			
				//if image belongs to current user, show delete button.
				if(userEmail.equals(email))
				{
	%>
					<form action="/delete" method="POST">
			 			<input type="hidden" name="url" value="<%= imgURL%>">
			 			<input type="submit" value="Delete">
			 		</form>
			 		</p>
			 		<p><%= email%></p>
					</td>
	<%
				}	
			}
			else
			{
 	%>
		 		<td>
		 		<a href="<%= imgURL%>"><img src="<%= thumbURL%>"></a>
		 		<p>
 	<%
				if(userEmail.equals(email))
				{
	%>
					<form action="/delete" method="POST">
			 			<input type="hidden" name="url" value="<%= imgURL%>">
			 			<input type="submit" value="Delete">
			 		</form>
			 		</p>
			 		<p><%= email%></p>
			 		</td>
 	<%
	 			}
	 		}
 		}
 	%>
 	</table>
 </body>
</html>