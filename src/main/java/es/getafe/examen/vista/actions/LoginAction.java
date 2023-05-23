package es.getafe.examen.vista.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.getafe.examen.modelo.Usuario;
import es.getafe.examen.negocio.Tienda;
import es.getafe.examen.negocio.TiendaImpl;

import static es.getafe.examen.vista.Util.isNotEmpty;

@Component
public class LoginAction implements Action {

	@Autowired
	private Tienda neg;
	
	@Override
	public String get(String path, HttpServletRequest req, HttpServletResponse resp) {
		return path;
	}

	@Override
	public String post(String path, HttpServletRequest req, HttpServletResponse resp) {
		String usuarioP = req.getParameter("usuario");
		String passwordP = req.getParameter("password");

		HttpSession sesion = req.getSession();
		
		String vista;
		
		if (isNotEmpty(usuarioP) && isNotEmpty(passwordP)) {
			Usuario usr = neg.validaLogin(usuarioP, passwordP);
			if (usr != null) {
				sesion.setAttribute("usuario", usr);
				vista = "redirect:menu_principal";
			} else {
				sesion.setAttribute("error", "login");
				vista = "redirect:" + path;
			}
		} else {
			sesion.setAttribute("error", "login");
			vista = "redirect:" + path;
		}
		return vista;
	}
}
