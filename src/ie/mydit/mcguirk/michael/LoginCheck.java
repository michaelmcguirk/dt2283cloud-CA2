
package ie.mydit.mcguirk.michael;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

/**
 * @author Michael McGuirk
 * D13123389
 * Cloud Computing CA2
 */
@SuppressWarnings("serial")
public class LoginCheck extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		  
		Principal myPrincipal = req.getUserPrincipal();	//current user
 
		resp.setContentType("text/html");
		
		//Check if a user is logged in. If logged in, redirect to users images. If not, redirect to public images.
		if(myPrincipal == null) 
		{
			resp.sendRedirect("/viewpublicimages.jsp");
		} // end if not logged in
		
		if(myPrincipal !=null) 
		{
			resp.sendRedirect("/viewimages.jsp");
		} // end if logged in
		
	}
}
