package root;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class AddWelcomeServlet extends HttpServlet {
	private static final String MSG_LABEL = "msg";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String msg = req.getParameter(MSG_LABEL);

		// R�cup�rer le service Datastore
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// Cr�er l'entity avec les param�tres
		Entity user = new Entity("WELCOMEMSG");
		user.setProperty(MSG_LABEL, msg);

		// enregistrer l'entit� dans le datastore
		datastore.put(user);
	}

}
