package ie.mydit.mcguirk.michael;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.images.*;

public class Serve extends HttpServlet
{
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	ImagesService is = ImagesServiceFactory.getImagesService();
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException 
	{
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
			 //blobstoreService.serve(blobKey, res);
			 @SuppressWarnings("deprecation")
			String foo = is.getServingUrl(blobKey, 100, false);
			 String bk = blobKey.getKeyString();
			 res.getWriter().println(bk);
			 res.getWriter().println(foo);
			 res.getWriter().println("<img src=\"" + foo + "\">");
			 
			Filter userFilter = new FilterPredicate("email", FilterOperator.EQUAL, "micky.mcguirk@gmail.com");
			
			Query q = new Query("User").setFilter(userFilter);
			PreparedQuery pq = datastore.prepare(q);
			
		for (Entity result : pq.asIterable())
		{
			String url = (String) result.getProperty("URL");
			String email = (String) result.getProperty("email");

			res.getWriter().println("Url: " + url + ". Email: " + email);
		}
	}

}
