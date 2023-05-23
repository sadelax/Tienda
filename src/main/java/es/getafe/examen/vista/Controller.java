package es.getafe.examen.vista;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;

import es.getafe.examen.vista.actions.Action;
import es.getafe.examen.vista.actions.ActionsFactory;

public class Controller implements HttpRequestHandler {

	private String context;
	
	@Autowired
	private ActionsFactory af;
	private ServletContext app;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		context = request.getContextPath();
		app = request.getServletContext();
		
		System.out.println(context);
		System.out.println(app);
		
		app.setAttribute("context", context);
		app.setAttribute("home", context + "/home");
		app.setAttribute("css", context + "/recursos/css");
		
		Action actual;
		
		actual = af.getAction(request);

		String vista = actual.execute(request, response);

		if (vista.startsWith("redirect")) {
			response.sendRedirect(context + "/home/" + vista.substring(9));
		} else {
			request.getRequestDispatcher(af.VISTA_PRE + vista + af.VISTA_POST).forward(request, response);
		}
	}
}
