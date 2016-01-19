package root;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.cache.CacheException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

public class LoginServlet extends HttpServlet {
	private static final String LOGIN_LABEL = "login";
	private static final String PWD_LABEL = "pwd";
	private static final String MSG_LABEL = "msg";
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// Récupération les paramètres du formulaire
		String login = req.getParameter(LOGIN_LABEL);
		String pwd = req.getParameter(PWD_LABEL);

		// Récupérer le service Datastore
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		// Utilisation Query afin de rassembler les éléments a appeler/filter
		Query q = new Query("USER");
		Filter loginFilter = new FilterPredicate("login",
				FilterOperator.EQUAL, login);
		Filter pwdFilter = new FilterPredicate("pwd",
				FilterOperator.EQUAL, pwd);
		Filter authFilter = CompositeFilterOperator.and(loginFilter,
				pwdFilter);
		
		q.setFilter(authFilter);
		// Récupération du résultat de la requète à l’aide de PreparedQuery
		PreparedQuery pq = datastore.prepare(q);
		
		//check result
		if( pq.countEntities()==0){
			resp.getWriter().write("AUTH FAILURE");
		}else{
			String msg=getMessage(datastore);
			resp.getWriter().write("AUTH SUCCESS " + msg);
		}
	}
 
	private String getMessage(DatastoreService datastore){
		
		//récupération du service Cache
		Cache cache=null;
	    Map props = new HashMap();
	    props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
	    props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);
	    try {
	        CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	        cache = cacheFactory.createCache(props);
	     } catch ( net.sf.jsr107cache.CacheException e) {
	    	 e.printStackTrace();
	     }

		if( cache.get(MSG_LABEL)!=null){
			return (String)cache.get(MSG_LABEL);
		}else{
			String msgDatastore=null;
			Query q = new Query("WELCOMEMSG");
			PreparedQuery pq = datastore.prepare(q);
			
			for (Entity result : pq.asIterable()) {
				msgDatastore= (String) result.getProperty(MSG_LABEL);
				}
			
			//udpate cache
			cache.put(MSG_LABEL, msgDatastore);
			return msgDatastore;
		}
	}
	
	
}
