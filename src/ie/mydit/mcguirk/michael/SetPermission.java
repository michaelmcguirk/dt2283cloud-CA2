package ie.mydit.mcguirk.michael;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class SetPermission extends HttpServlet
{
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	ImagesService is = ImagesServiceFactory.getImagesService();
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)throws IOException 
	{
		String permissionCommand = req.getParameter("permission");
		String imgURL = req.getParameter("img");
		String newPermissionValue = "";
		
		if(permissionCommand.equals("Make Private"))
		{
			newPermissionValue = "private";
		}
		else
		{
			newPermissionValue = "public";
		}
		
		Filter userFilter = new FilterPredicate("URL", FilterOperator.EQUAL, imgURL);

		Query q = new Query("User").setFilter(userFilter);
		PreparedQuery pq = datastore.prepare(q);
			
		for (Entity result : pq.asIterable())
		{
			result.setProperty("permission", newPermissionValue);
			datastore.put(result);
			res.getWriter().println("Permission changed to " + newPermissionValue);
			res.getWriter().println("<a href=\"/viewimages.jsp\">Go Back</a>");
			
		}
	}

}
