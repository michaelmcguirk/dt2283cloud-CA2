package ie.mydit.mcguirk.michael;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.images.*;

public class Delete extends HttpServlet
{
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	ImagesService is = ImagesServiceFactory.getImagesService();
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException 
	{
		String deleteURL = req.getParameter("url");
			 
			Filter userFilter = new FilterPredicate("URL", FilterOperator.EQUAL, deleteURL);
			
			Query q = new Query("User").setFilter(userFilter);
			PreparedQuery pq = datastore.prepare(q);
			
		for (Entity result : pq.asIterable())
		{
			Key k = result.getKey();
			datastore.delete(k);
		}
		res.getWriter().println("Image has been deleted. Bye bye image...");
		res.getWriter().println("<a href=\"/viewimages.jsp\">Back</a>");
		
	}

}
