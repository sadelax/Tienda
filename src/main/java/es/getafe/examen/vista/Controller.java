package es.getafe.examen.vista;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;

import es.getafe.examen.negocio.Tienda;
import es.getafe.examen.negocio.TiendaImpl;
import es.getafe.examen.vista.actions.Action;
import es.getafe.examen.vista.actions.ActionsFactory;

public class Controller implements HttpRequestHandler {

	private Tienda neg;
	private String context;
	private ActionsFactory af;

	public void init() throws ServletException {
		neg = new TiendaImpl();
		af = new ActionsFactory();

		ServletContext app = getServletContext();
		context = app.getContextPath();

		app.setAttribute("context", context);
		app.setAttribute("home", context + "/home");
		app.setAttribute("css", context + "/css");
	}


	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
