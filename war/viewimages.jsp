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
 	<%
 		String userEmail = u.getEmail();
		Filter userFilter = new FilterPredicate("email", FilterOperator.EQUAL, "micky.mcguirk@gmail.com");
				Query q = new Query("User").setFilter(userFilter);
		PreparedQuery pq = datastore.prepare(q);
			
		for (Entity result : pq.asIterable())
		{
			String url = (String) result.getProperty("thumbURL");
			String email = (String) result.getProperty("email");

 	%>
 		<table>
		<tr>
		<td><%= email%></td>
		<td><img src="<%= url%>"></td>
		</tr>
		</table>
	<%		
		}
 	%>
 </body>
</html>