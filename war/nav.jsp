<%@ page import="com.google.appengine.api.users.*"%>

<%UserService userService = UserServiceFactory.getUserService(); %>
<%User u = userService.getCurrentUser(); %>
<%String logoutURL = userService.createLogoutURL("/viewpublicimages.jsp"); %>
<%String loginURL = userService.createLoginURL("/viewimages.jsp"); %>

<html>
 	<head>
 		<link rel="stylesheet" type="text/html" href="${pageContext.request.contextPath}/style.css"/>
 	</head>
 	<body>
 	<div id="navigation">
 	<ul>
 	<%
 	if(userService.isUserLoggedIn())
 	{
 	%>
	 	<li><a href="/viewimages.jsp">My Pictures</a></li>
	 	<li>&nbsp</li>
	 	<li><a href ="/viewalluserimages.jsp">All Pictures</a></li>
	 	<li>&nbsp</li>
	 	<%
	 	//if user is an admin, show link to admin page.
	 	if(userService.isUserAdmin())
	 	{
	 	%>
	 		<li><a href="/adminviewimages.jsp">All Pictures(Admin)</a></li>
	 		<li>&nbsp</li>
	 	<%
	 	}
	 	%>
	 	<li><a href="/upload.jsp">Upload</a></li>
	 	<li>&nbsp</li>
	 	<li><a href="<%= logoutURL%>">Logout</a></li>
	 	<li>&nbsp</li>
	 	<li><a href="/userguide.pdf">Help</a></li>
	 <%
	 }
	 else
	 {
	 //if user is not logged in, only show login link.
	 %>
	 	<li>Please login to use all features</li>
	 	<li>&nbsp</li>
	 	<li><a href="<%= loginURL%>">Login</a></li>
	 	
	 <% } %>
 	</ul>
 	</div>
 </body>
</html>