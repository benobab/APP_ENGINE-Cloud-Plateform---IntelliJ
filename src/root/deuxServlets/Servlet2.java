package root.deuxServlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Benobab on 19/01/16.
 */
public class Servlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            URL url = new URL("http://titanium-index-119507.appspot.com/getInfo");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String r = "";
            String line; while ((line = reader.readLine()) != null) {
                r += line;
                }
            reader.close();
            response.getWriter().write(r);
        } catch (MalformedURLException e) {
                // Gestion d’exceptions d’ouverture de flux
        } catch (IOException e) {
            // Gestion d’exceptions de lecture de flux
        }
    }
}
