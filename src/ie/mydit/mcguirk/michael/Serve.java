package ie.mydit.mcguirk.michael;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.*;

public class Serve extends HttpServlet
{
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	ImagesService is = ImagesServiceFactory.getImagesService();
	
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
	}

}
