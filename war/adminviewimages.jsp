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


<%BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
<%UserService userService = UserServiceFactory.getUserService(); %>
<%User u = userService.getCurrentUser(); %>
<%DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();%>


<html>
 	<head>
 		<title>View Images</title>
 	</head>
 	<body>
 	<table>
 	<tr>
 	<%
 		String userEmail = u.getEmail();
		Filter userFilter = new FilterPredicate("email", FilterOperator.EQUAL, userEmail);
		Query q = new Query("User");
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
				<form action="/delete" method="POST">
		 			<input type="hidden" name="url" value="<%= imgURL%>">
		 			<input type="submit" value="Delete">
		 		</form>
		 		</p>
		 		<p><%= email%></p>
				</td>
	<%		
			}
			else
			{
 	%>
		 		<td>
		 		<a href="<%= imgURL%>"><img src="<%= thumbURL%>"></a>
		 		<p>
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
 	%>
 	</table>
 </body>
</html>