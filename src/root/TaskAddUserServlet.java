package root;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class TaskAddUserServlet extends HttpServlet {
	private static final String SURNAME_LABEL="surname";
	private static final String LASTNAME_LABEL="lastname";
	private static final String LOGIN_LABEL="login";
	private static final String PWD_LABEL="pwd";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Récupération les paramètres du formulaire
		String surname=req.getParameter(SURNAME_LABEL);
		String lastname=req.getParameter(LASTNAME_LABEL);
		String login=req.getParameter(LOGIN_LABEL);
		String pwd=req.getParameter(PWD_LABEL);
		
		//Récupérer le service Datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		//Créer l'entity avec les paramètres
		Entity user = new Entity("USER");
		user.setProperty(SURNAME_LABEL, surname);
		user.setProperty(LASTNAME_LABEL, lastname);
		user.setProperty(LOGIN_LABEL, login);
		user.setProperty(PWD_LABEL, pwd);
		
		//enregistrer l'entité dans le datastore
		datastore.put(user);
		
		System.out.println("USER:"+lastname+" has been registered by TaskAddUserServlet");
	}

}
