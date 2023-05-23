package es.getafe.examen.vista.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CerrarSesionAction implements Action {

	@Override
	public String get(String path, HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().invalidate();
		return "login";
	}

	@Override
	public String post(String path, HttpServletRequest req, HttpServletResponse resp) {
		return null;
	}

}
