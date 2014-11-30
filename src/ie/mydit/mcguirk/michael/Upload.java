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

public class Upload extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);

		BlobKey blobKey = blobs.get("myFile2");
		
		
		for (Entry<String, BlobKey> entry : blobs.entrySet())
		{
			String i = entry.getValue().getKeyString();
		    System.out.println(entry.getKey() + "/" + i);
		    res.getWriter().println(entry.getKey() + "/" + i);
		}
		Principal myPrincipal = req.getUserPrincipal();
		String email = myPrincipal.getName();
		
		Entity person = new Entity("Jimbob");

		person.setProperty("name", email);
		person.setProperty("age", 19);
		datastore.put(person);
		
		
		
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
			res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
		}
	}
}
