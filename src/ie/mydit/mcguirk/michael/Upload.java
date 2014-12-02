package ie.mydit.mcguirk.michael;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Upload extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	UserService userService = UserServiceFactory.getUserService();
	ImagesService is = ImagesServiceFactory.getImagesService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		User u = userService.getCurrentUser();
		String email = u.getEmail();
		
		BlobKey blobKey = blobs.get("myFile2");
		String imgURL = is.getServingUrl(blobKey);
		String thumbURL = is.getServingUrl(blobKey, 150, false);
		
		Entity user = new Entity("User");
		user.setProperty("email", email);
		user.setProperty("URL", imgURL);
		user.setProperty("thumbURL", thumbURL);
		datastore.put(user);
		
		
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (blobKey == null)
		{
			res.sendRedirect("/");
			System.out.println("Error Error");
		} else
		{
			System.out.println("Uploaded a file with blobKey: " + blobKey.getKeyString());
			//res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
			res.sendRedirect("/viewimages.jsp");
		}
	}
}
