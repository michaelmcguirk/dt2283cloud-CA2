<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.datastore.Query.Filter"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Query.CompositeFilterOperator"%>


<%DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();%>


<html>
 	<head>
 		<title>View Public Pictures</title>
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
 		String permission = "public";
 		Filter permissionFilter = new FilterPredicate("permission", FilterOperator.EQUAL, permission);
		Query q = new Query("User").setFilter(permissionFilter);
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
				</td>
	<%	
			}
			else
			{
 	%>
		 		<td>
		 		<a href="<%= imgURL%>"><img src="<%= thumbURL%>"></a>
		 		</td>
 	<%
	 		}
 		}
 	%>
 	</table>
 </body>
</html>