package root;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class FormDelegateServlet extends HttpServlet {
	private static final String SURNAME_LABEL = "surname";
	private static final String LASTNAME_LABEL = "lastname";
	private static final String LOGIN_LABEL = "login";
	private static final String PWD_LABEL = "pwd";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Récupération les paramètres du formulaire
		String surname = req.getParameter(SURNAME_LABEL);
		String lastname = req.getParameter(LASTNAME_LABEL);
		String login = req.getParameter(LOGIN_LABEL);
		String pwd = req.getParameter(PWD_LABEL);

		Queue queue = QueueFactory.getDefaultQueue();

		// Ajout d’une tache simple
		TaskOptions task = TaskOptions.Builder.withUrl("/worker")
				.param(SURNAME_LABEL, surname)
				.param(LASTNAME_LABEL, lastname)
				.param(LOGIN_LABEL, login)
				.param(PWD_LABEL, pwd);
		queue.add(task);
	}

}
