/*
 * Michael McGuirk, D13123389 - DT228/3
 * Cloud Computing. Lab 5. 15/10/2014
 * Multiplication servlet to take multiply 2 values supplied by the user or default values in XML file.
 */
package ie.mydit.mcguirk.michael;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class AssignmentServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, worldy world");
		
		//Creates instance of UserService object to require user to login/register
		UserService userService = UserServiceFactory.getUserService();  
		Principal myPrincipal = req.getUserPrincipal();	//current user
		//String myp = myPrincipal.toString();
		//resp.getWriter().println(myp);
		String emailAddress = null;
		String thisURL = req.getRequestURI();
		//fetch URL's to for login/out links 
		String loginURL = userService.createLoginURL(thisURL);
		String logoutURL = userService.createLogoutURL(thisURL);
		String uploadURL = "/upload.jsp";
		System.out.println(loginURL);
		
		resp.setContentType("text/html");
		
		if(myPrincipal == null) 
		{
			//if there is no current authenticated user, inform user and offer login/signup link.
			resp.getWriter().println("<p>You are Not Logged In time</p>");
			resp.getWriter().println("<p>You can <a href=\""+loginURL+"\">sign in here</a>.</p>");
		} // end if not logged in
		
		if(myPrincipal !=null) 
		{
			//if logged in, offer a logout link.
			emailAddress = myPrincipal.getName();
			resp.getWriter().println("<p>You are Logged in as (email): "+emailAddress+"</p>");
			resp.getWriter().println("<p>You can <a href=\"" + uploadURL +"\">Upload</a>.</p>");
			resp.getWriter().println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>");
		} // end if logged in
		
	}
}
